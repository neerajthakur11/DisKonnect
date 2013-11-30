package in.hacknight.model;

import android.content.Context;
import in.hacknight.storage.DataStorage;

import java.util.List;

public class ScoreCalculator {
    public long getScore(Context context){
        DataStorage dataStorage = new DataStorage(context);
        List<Event> allEvents = dataStorage.getAllEvents();
        long totalRequiredTime = 0;
        long totalConsumedTime = 0;
        for(Event event : allEvents) {
            totalRequiredTime = totalRequiredTime + event.requiredDuration;
            totalConsumedTime = totalConsumedTime + (event.endTime - event.startTime);

        }

        long score = (totalConsumedTime / totalRequiredTime / 1000) ;
        return score;
    }
}
