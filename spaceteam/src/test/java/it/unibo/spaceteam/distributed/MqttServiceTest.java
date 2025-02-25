package it.unibo.spaceteam.distributed;

import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.Player;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MqttServiceTest {

    MqttService mqttService;
    Lobby lobby;

    @BeforeEach
    public void setUp() {
        Player player = new Player();
        lobby = new Lobby(player, "lobby1");
        mqttService = new MqttService("tcp://test.mosquitto.org:1883", lobby);
    }

    @Test
    public void testPayload() {
        Player player = mqttService.retrievePayload(new MqttMessage(mqttService.createPayload(lobby.getCurrentPlayer())), Player.class);

        assertEquals(lobby.getCurrentPlayer(), player);
    }

    @Test
    public void testPublishSubscribe() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        String testTopic = "spaceteam/test";
        String expectedMessage = "Hello!";

        mqttService.subscribe(testTopic, (topic, message) -> {
            String receivedMessage = mqttService.retrievePayload(message, String.class);
            assertEquals(expectedMessage, receivedMessage);
            latch.countDown();
        });

        mqttService.publish(testTopic, mqttService.createPayload(expectedMessage), 1, false);

        boolean messageReceived = latch.await(3, TimeUnit.SECONDS);
        assertTrue(messageReceived);
    }

    @Test
    public void testUnsubscribe() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        String testTopic = "spaceteam/test";
        String expectedMessage = "Hello!";

        mqttService.subscribe(testTopic, (topic, message) -> {
            String receivedMessage = mqttService.retrievePayload(message, String.class);
            assertEquals(expectedMessage, receivedMessage);
            latch.countDown();
        });

        mqttService.unsubscribe(testTopic);

        mqttService.publish(testTopic, mqttService.createPayload(expectedMessage), 1, false);

        boolean messageReceived = latch.await(3, TimeUnit.SECONDS);
        assertFalse(messageReceived);
    }

}
