package it.unibo.spaceteam.distributed;

import java.util.Objects;

public class Vote {

    private final String voterId;
    private final Boolean voteValue;

    public Vote(String voterId, Boolean voteValue) {
        this.voterId = voterId;
        this.voteValue = voteValue;
    }

    public String getVoterId() {
        return voterId;
    }

    public Boolean getVoteValue() {
        return voteValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(voterId, vote.voterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voterId);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voterId='" + voterId + '\'' +
                ", voteValue=" + voteValue +
                '}';
    }

}
