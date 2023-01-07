package service;

import model.Task;

import java.util.HashMap;
import java.util.Map;

public class MapsConstructor <T extends Task> {

    public Map<Integer, T> createMap (Task task){
        return new HashMap<>();
    }

    Map<Integer, T> mapTasks = new HashMap<>();

}
