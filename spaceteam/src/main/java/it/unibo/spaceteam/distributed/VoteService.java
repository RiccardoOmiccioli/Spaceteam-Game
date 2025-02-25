package it.unibo.spaceteam.distributed;

import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.Player;

import java.util.*;
import java.util.stream.IntStream;

public class VoteService {

    public static final int MAX_VOTERS = 3;

    private final VotePolicy votePolicy;
    private final Set<Vote> votes;
    private final Lobby lobby;

    public VoteService(VotePolicy votePolicy, Lobby lobby) {
        this.votePolicy = votePolicy;
        this.votes = new HashSet<>();
        this.lobby = lobby;
    }

    // Returns if the specified player can vote based on the provided seed
    public static boolean canVote(Player player, Lobby lobby, int seed) {
        List<Player> players = new ArrayList<>(lobby.getAllPlayers());
        players.sort(Comparator.comparing(Player::getId));

        /* Create a stream of indices from 0 (inclusive) to players.size (exclusive)
         * shift the index based on lobby status and take until maxVoters
         * take players based on found indices
         * check if provided player is in selected players
         */
        return IntStream.range(0, players.size())
                .filter(i -> (i + seed) % players.size() < MAX_VOTERS)
                .mapToObj(players::get).toList()
                .contains(player);
    }

    public boolean isVoteComplete() {
        List<Vote> votesList = votes.stream().filter(Vote::getVoteValue).toList();
        return switch (votePolicy) {
            case ONE -> !votesList.isEmpty();
            case MAJORITY -> votesList.size() >= Math.ceil(getVotersNumber() / 2.0);
            case ALL -> votesList.size() >= getVotersNumber();
        };
    }

    public void castVote(Vote vote) {
        votes.remove(vote);
        votes.add(vote);
    }

    private int getVotersNumber() {
        return Math.min(lobby.getAllPlayers().size(), MAX_VOTERS);
    }

}
