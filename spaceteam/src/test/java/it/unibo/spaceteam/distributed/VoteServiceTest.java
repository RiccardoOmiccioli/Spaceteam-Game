package it.unibo.spaceteam.distributed;

import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.unibo.spaceteam.distributed.VoteService.MAX_VOTERS;
import static org.junit.jupiter.api.Assertions.*;

public class VoteServiceTest {

    private Lobby lobby;

    @BeforeEach
    public void setup() {
        Player player1 = new Player("1");
        lobby = new Lobby(player1, null);

        Player player2 = new Player("2");
        Player player3 = new Player("3");
        Player player4 = new Player("4");
        Player player5 = new Player("5");

        lobby.addPlayer(player2);
        lobby.addPlayer(player3);
        lobby.addPlayer(player4);
        lobby.addPlayer(player5);
    }

    @Test
    public void testCanVote() {
        List<Boolean> canVoteResults = new ArrayList<>();

        lobby.getAllPlayers().forEach(player -> {
            canVoteResults.add(VoteService.canVote(player, lobby, 0));
        });

        assertEquals(canVoteResults.stream().filter(canVote -> canVote).count(), MAX_VOTERS);
    }

    @Test
    public void testOneVotePolicy() {
        VoteService voteService = new VoteService(VotePolicy.ONE, lobby);

        lobby.getAllPlayers().forEach(player -> {
            if (VoteService.canVote(player, lobby, 0)) {
                voteService.castVote(new Vote(player.getId(), true));
                assertTrue(voteService.isVoteComplete());
            }
        });
    }

    @Test
    public void testMajorityVotePolicy() {
        VoteService voteService = new VoteService(VotePolicy.MAJORITY, lobby);
        List<Vote> castedVotes = new ArrayList<>();
        int votersNumber = Math.min(lobby.getAllPlayers().size(), MAX_VOTERS);

        lobby.getAllPlayers().forEach(player -> {
            if (VoteService.canVote(player, lobby, 0)) {
                Vote vote = new Vote(player.getId(), true);
                voteService.castVote(vote);
                castedVotes.add(vote);
                if (castedVotes.size() >= Math.ceil(votersNumber / 2.0)) {
                    assertTrue(voteService.isVoteComplete());
                } else {
                    assertFalse(voteService.isVoteComplete());
                }
            }
        });
    }

    @Test
    public void testAllVotePolicy() {
        VoteService voteService = new VoteService(VotePolicy.ALL, lobby);
        List<Vote> castedVotes = new ArrayList<>();
        int votersNumber = Math.min(lobby.getAllPlayers().size(), MAX_VOTERS);

        lobby.getAllPlayers().forEach(player -> {
            if (VoteService.canVote(player, lobby, 0)) {
                Vote vote = new Vote(player.getId(), true);
                voteService.castVote(vote);
                castedVotes.add(vote);
                if (castedVotes.size() >= votersNumber) {
                    assertTrue(voteService.isVoteComplete());
                } else {
                    assertFalse(voteService.isVoteComplete());
                }
            }
        });
    }

    @Test
    public void testVoteChange() {
        VoteService voteService = new VoteService(VotePolicy.ONE, lobby);

        voteService.castVote(new Vote("1", false));
        assertFalse(voteService.isVoteComplete());
        voteService.castVote(new Vote("1", true));
        assertTrue(voteService.isVoteComplete());
    }

    @Test
    public void testNegativeOneVotePolicy() {
        VoteService voteService = new VoteService(VotePolicy.ONE, lobby);

        voteService.castVote(new Vote("1", false));
        voteService.castVote(new Vote("2", false));
        assertFalse(voteService.isVoteComplete());
        voteService.castVote(new Vote("3", true));
        assertTrue(voteService.isVoteComplete());
    }

    @Test
    public void testNegativeMajorityVotePolicy() {
        VoteService voteService = new VoteService(VotePolicy.MAJORITY, lobby);

        voteService.castVote(new Vote("1", false));
        voteService.castVote(new Vote("2", true));
        assertFalse(voteService.isVoteComplete());
        voteService.castVote(new Vote("3", false));
        voteService.castVote(new Vote("4", true));
        voteService.castVote(new Vote("5", true));
        assertTrue(voteService.isVoteComplete());
    }

    @Test
    public void testNegativeAllVotePolicy() {
        VoteService voteService = new VoteService(VotePolicy.ALL, lobby);

        voteService.castVote(new Vote("1", false));
        voteService.castVote(new Vote("2", true));
        assertFalse(voteService.isVoteComplete());
        voteService.castVote(new Vote("3", true));
        assertFalse(voteService.isVoteComplete());
        voteService.castVote(new Vote("1", true));
        assertTrue(voteService.isVoteComplete());
    }

}
