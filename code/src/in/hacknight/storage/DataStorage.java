package in.hacknight.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import in.hacknight.model.Event;
import in.hacknight.model.Profile;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;

public class DataStorage extends SQLiteOpenHelper {
    private static final String DB_NAME = "disKonnect.db";

    private static final String PROFILE_TABLE_NAME = "profile";
    private static final String PROFILE_NAME = "name";
    private static final String PROFILE_PROPERTY = "property";
    private static final String PROFILE_DURATION = "duration";

    private static final String EVENT_TABLE_NAME = "event";
    private static final String EVENT_DURATION = "duration";
    private static final String EVENT_START_TIME = "startTime";
    private static final String EVENT_END_TIME = "endTime";
    private static final String EVENT_PROFILE_ID = "profileID";

    public DataStorage(Context context) {
        super(context, DB_NAME, null, 1);
    }

    private static final String PROFILE_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + PROFILE_TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PROFILE_NAME + " TEXT NOT NULL ," +
            PROFILE_DURATION + " INTEGER ," +
            PROFILE_PROPERTY + " INTEGER );";

    private static final String EVENT_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + EVENT_TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EVENT_DURATION + " INTEGER ," +
                EVENT_START_TIME + " INTEGER ," +
                EVENT_END_TIME + " INTEGER ," +
                EVENT_PROFILE_ID + " INTEGER );";


    public void storeProfile(Profile profile) {
        SQLiteDatabase db = getWritableDatabase();
        store(profile, db);
    }

    private void store(Profile profile, SQLiteDatabase db) {
        SQLiteStatement statement = db.compileStatement("insert into " + PROFILE_TABLE_NAME + " (" + PROFILE_NAME + "," + PROFILE_PROPERTY + "," + PROFILE_DURATION + ") values ( ?, ?, ?)");
        statement.bindString(1, profile.name);
        statement.bindLong(2, profile.property);
        statement.bindLong(3, profile.duration);
        statement.executeInsert();
    }
    public List<Profile> getAllProfiles() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PROFILE_TABLE_NAME,null);
        ArrayList<Profile> list = new ArrayList<Profile>();

        if (cursor .moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                String name = cursor.getString(cursor.getColumnIndex(PROFILE_NAME));
                int duration = cursor.getInt(cursor.getColumnIndex(PROFILE_DURATION));
                int property = cursor.getInt(cursor.getColumnIndex(PROFILE_PROPERTY));
                int id = cursor.getInt(cursor.getColumnIndex(_ID));
                list.add(new Profile(name, property, duration, false, id));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
    
    public Profile getProfileById(int _id_) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PROFILE_TABLE_NAME + " where id = " + Integer.toString(_id_) ,null);

        if (cursor .moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                String name = cursor.getString(cursor.getColumnIndex(PROFILE_NAME));
                int duration = cursor.getInt(cursor.getColumnIndex(PROFILE_DURATION));
                int property = cursor.getInt(cursor.getColumnIndex(PROFILE_PROPERTY));
                int id = cursor.getInt(cursor.getColumnIndex(_ID));
                cursor.close();
                return new Profile(name, property, duration, false, id);
            }
        }
        
        return null;
        
    }
    
     public List<Event> getAllEvents() {
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from " + EVENT_TABLE_NAME,null);
            ArrayList<Event> list = new ArrayList<Event>();

            if (cursor .moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    int eventDuration = cursor.getInt(cursor.getColumnIndex(EVENT_DURATION));
                    int eventStartTime = cursor.getInt(cursor.getColumnIndex(EVENT_START_TIME));
                    int eventEndTime = cursor.getInt(cursor.getColumnIndex(EVENT_END_TIME));
                    int eventProfileId = cursor.getInt(cursor.getColumnIndex(EVENT_PROFILE_ID));
                    list.add(new Event(eventDuration, eventStartTime, eventEndTime, eventProfileId));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            return list;
        }

    public void storeEvent(Event event) {
        SQLiteDatabase db = getWritableDatabase();
        store(event, db);
    }
    private void store(Event event, SQLiteDatabase db) {
        SQLiteStatement statement = db.compileStatement("insert into " + EVENT_TABLE_NAME + " ( " + EVENT_DURATION + " , " + EVENT_PROFILE_ID + " , " + EVENT_START_TIME + " , " + EVENT_END_TIME + " ) values (?, ?, ?, ?)");
        statement.bindLong(1, event.requiredDuration);
        statement.bindLong(2, event.profileId);
        statement.bindLong(3, event.startTime);
        statement.bindLong(3, event.endTime);
        statement.executeInsert();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROFILE_DATABASE_CREATE);
        db.execSQL(EVENT_DATABASE_CREATE);
        store(new Profile("default", 600000, 1111, false), db);
        store(new Event(600000, 1385762598, 1385762598, 1), db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void updateProfile(Profile profile) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + PROFILE_TABLE_NAME + " SET " + PROFILE_NAME + " = '" + profile.name + "'," + PROFILE_PROPERTY + " = " + profile.property  + "," + PROFILE_DURATION + " = " + profile.duration  + " WHERE  "+ _ID + " = " + profile.id + ";");
    }
}
