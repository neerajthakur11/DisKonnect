package in.hacknight.model;

import android.content.Context;
import in.hacknight.storage.DataStorage;

import java.util.List;

public class ScoreCalculator {
    public int getScore(Context context){
        DataStorage dataStorage = new DataStorage(context);
        List<Event> allEvents = dataStorage.getAllEvents();
        int totalRequiredTime = 0;
        int totalConsumedTime = 0;
        for(Event event : allEvents) {
            totalRequiredTime = totalRequiredTime + event.requiredDuration;
            System.out.println(totalRequiredTime + " &&&&&&&&&&&&&&");
            totalConsumedTime = totalConsumedTime + (event.endTime - event.startTime);
            System.out.println(totalConsumedTime + " &&&&&&&&&&&&&&!!!!!!!!!!!!!");

        }

        int score = (totalConsumedTime / totalRequiredTime) * 100;
        System.out.println(score + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return score > 100 ? 100 : score;
    }
}
