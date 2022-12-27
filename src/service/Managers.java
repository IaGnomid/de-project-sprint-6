package service;

import service.HistoryManager;
import service.TaskManager;

import java.nio.file.Paths;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static FileBackedTaskManager getDefaultHistory(){
        return new FileBackedTaskManager(Paths.get("file.csv"));
    }
}
