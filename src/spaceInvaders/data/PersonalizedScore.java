package spaceInvaders.data;

public class PersonalizedScore implements Comparable<PersonalizedScore> {
    public PersonalizedScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(PersonalizedScore personalizedScore) {
        return Integer.compare(score, personalizedScore.score);
    }

    private String name;
    private int score;

}
