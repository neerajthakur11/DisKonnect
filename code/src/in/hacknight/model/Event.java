package in.hacknight.model;

public class Event {

    public long requiredDuration;
    public long startTime;
    public long endTime;
    public long profileId;

    public Event(long requiredDuration, long startTime, long endTime, long profileId ) {
        this.endTime = endTime;
        this.startTime = startTime;
        this.requiredDuration = requiredDuration;
        this.profileId = profileId;
    }
}
