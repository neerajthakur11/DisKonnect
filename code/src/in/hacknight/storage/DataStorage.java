package in.hacknight.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

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

    private static final String PROFILE_DATABASE_CREATE = "CREATE TABLE " + PROFILE_TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PROFILE_NAME + " TEXT NOT NULL ," +
            PROFILE_DURATION + " INTEGER ," +
            PROFILE_PROPERTY + " INTEGER );";

    private static final String EVENT_DATABASE_CREATE = "CREATE TABLE " + PROFILE_TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EVENT_DURATION + " TEXT NOT NULL ," +
                EVENT_START_TIME + " INTEGER ," +
                EVENT_END_TIME + " INTEGER ," +
                EVENT_PROFILE_ID + " INTEGER );";


    public void storeProfile(String name, int property, int duration) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("insert into " + PROFILE_TABLE_NAME + " (" + PROFILE_NAME + "," + PROFILE_PROPERTY + "," + PROFILE_DURATION + ") values ( ?, ?, ?)");
        statement.bindString(1, name);
        statement.bindLong(2, property);
        statement.bindLong(3, duration);
        statement.executeInsert();
    }

    public void storeEvent(int duration, int startTime, int endTime, int profileId) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("insert into " + EVENT_TABLE_NAME + " (" + EVENT_DURATION + "," + EVENT_PROFILE_ID + "," + EVENT_START_TIME + "," + EVENT_END_TIME + ") values ( ?, ?, ?, ?)");
        statement.bindLong(1, duration);
        statement.bindLong(2, profileId);
        statement.bindLong(3, startTime);
        statement.bindLong(3, endTime);
        statement.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROFILE_DATABASE_CREATE);
        db.execSQL(EVENT_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
