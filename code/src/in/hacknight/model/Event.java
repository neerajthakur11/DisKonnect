package in.hacknight.model;

public class Event {

    public int requiredDuration;
    public int startTime;
    public int endTime;
    public int profileId;

    public Event(int requiredDuration, int startTime, int endTime, int profileId ) {
        this.endTime = endTime;
        this.startTime = startTime;
        this.requiredDuration = requiredDuration;
        this.profileId = profileId;
    }
}
