package co.edu.eci.ieti.android.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.eci.ieti.android.persistence.dao.TaskDao;
import co.edu.eci.ieti.android.persistence.dao.UserDao;
import co.edu.eci.ieti.android.persistence.entities.Task;
import co.edu.eci.ieti.android.persistence.entities.User;

@Database(entities = {User.class, Task.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDB extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract TaskDao taskDao();

    private static volatile AppDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), AppDB.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
