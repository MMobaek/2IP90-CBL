package snackademy;

/**
 * Represents a player's score entry for the leaderboard.
 * Implements Comparable to allow sorting in descending order of scores.
 */
public class ScoreEntry implements Comparable<ScoreEntry> {

    /** Name of the player. */
    private final String name;

    /** Score achieved by the player. */
    private final int score;

    /**
     * Constructs a ScoreEntry with a player's name and score.
     *
     * @param name  the player's name
     * @param score the score achieved
     */
    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Returns the name of the player.
     *
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the score achieved by the player.
     *
     * @return player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Compares this ScoreEntry to another for sorting.
     * Entries are sorted in descending order of score.
     *
     * @param other the other ScoreEntry to compare to
     * @return negative if this > other, positive if this < other, 0 if equal
     */
    @Override
    public int compareTo(ScoreEntry other) {
        return Integer.compare(other.score, this.score);
    }
}
