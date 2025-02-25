package it.unibo.spaceteam.distributed;

import com.google.gson.Gson;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.utils.json.GsonUtils;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MqttService {

    private final Gson gson;
    private final Topic topic;

    private final MqttClient client;
    private final Map<String, BiConsumer<String, MqttMessage>> callbacks = new HashMap<>();

    public MqttService(String broker, Lobby lobby) {
        gson = GsonUtils.createGson();
        topic = new Topic(lobby.getId());
        String clientId = UUID.randomUUID().toString();

        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setCleanStart(true);

        MqttMessage willMessage = new MqttMessage();
        willMessage.setPayload(new byte[0]);
        willMessage.setRetained(true);
        willMessage.setQos(1);
        options.setWill(topic.getPlayerTopic(lobby.getCurrentPlayer().getId()), willMessage);

        try {
            client = new MqttClient(broker, clientId);
            client.connect(options);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void disconnected(MqttDisconnectResponse disconnectResponse) {}

            @Override
            public void mqttErrorOccurred(MqttException exception) {}

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                for (Map.Entry<String, BiConsumer<String, MqttMessage>> entry : callbacks.entrySet()) {
                    if (topicMatch(entry.getKey(), topic)) {
                        entry.getValue().accept(topic, message);
                        return;
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttToken token) {}

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {}

            @Override
            public void authPacketArrived(int reasonCode, MqttProperties properties) {}
        });
    }

    public Topic getTopic() {
        return topic;
    }

    public byte[] createPayload(Object object) {
        return gson.toJson(object).getBytes(StandardCharsets.UTF_8);
    }

    public <T> T retrievePayload(MqttMessage message, Class<T> type) {
        String jsonString = new String(message.getPayload(), StandardCharsets.UTF_8);
        return gson.fromJson(jsonString, type);
    }

    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void publish(String topic, byte[] payload, int qos, boolean retained) {
        CompletableFuture.runAsync(() -> {
            try {
                client.publish(topic, payload, qos, retained);
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void subscribe(String topic, BiConsumer<String, MqttMessage> callback)  {
        try {
            callbacks.put(topic, callback);
            client.subscribe(topic, 1);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void unsubscribe(String topic) {
        try {
            client.unsubscribe(topic);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        callbacks.remove(topic);
    }

    public boolean topicMatch(String subscriptionTopic, String actualTopic) {
        String[] subscriptionLevels = subscriptionTopic.split("/");
        String[] topicLevels = actualTopic.split("/");
        for (int i = 0; i < subscriptionLevels.length; i++) {
            String subscriptionLevel = subscriptionLevels[i];
            String topicLevel = topicLevels[i];
            if (subscriptionLevel.equals("#")) {
                return true;
            }
            if (!subscriptionLevel.equals("+") && !subscriptionLevel.equals(topicLevel)) {
                return false;
            }
        }
        return subscriptionLevels.length == topicLevels.length;
    }

}
