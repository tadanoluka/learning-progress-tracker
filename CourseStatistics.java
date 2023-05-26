package tracker;

public class CourseStatistics {
    private String mostPopular;
    private String leastPopular;
    private String highestActivity;
    private String lowestActivity;
    private String easiest;
    private String hardest;

    public CourseStatistics() {
    }

    public void setMostPopular(String mostPopular) {
        this.mostPopular = mostPopular;
    }

    public void setLeastPopular(String leastPopular) {
        this.leastPopular = leastPopular;
    }

    public void setHighestActivity(String highestActivity) {
        this.highestActivity = highestActivity;
    }

    public void setLowestActivity(String lowestActivity) {
        this.lowestActivity = lowestActivity;
    }

    public void setEasiest(String easiest) {
        this.easiest = easiest;
    }

    public void setHardest(String hardest) {
        this.hardest = hardest;
    }

    public String getMostPopular() {
        return mostPopular;
    }

    public String getLeastPopular() {
        return leastPopular;
    }

    public String getHighestActivity() {
        return highestActivity;
    }

    public String getLowestActivity() {
        return lowestActivity;
    }

    public String getEasiest() {
        return easiest;
    }

    public String getHardest() {
        return hardest;
    }
}