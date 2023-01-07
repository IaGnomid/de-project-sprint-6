package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static service.TaskName.valueOf;

public class CSVTaskConverter {


    public Task fromString(String value){
        String[] splitTask = value.split(",");
        int idTask = Integer.parseInt(splitTask[0]);
        String typeTask = splitTask[1];
        String nameTask = splitTask[2];
        String statusTask = splitTask[3];
        String description = splitTask[4];
        int idEpic = Integer.parseInt(splitTask[5]);

//        Task task = new Task(nameTask, description, idTask);
//        task.setStatus(TaskStatus.valueOf(statusTask));
//        return task;

        switch (valueOf(typeTask)) {
            case TASK:
                Task task = new Task(nameTask, description, idTask);
                task.setStatus(TaskStatus.valueOf(statusTask));
                return task;
            case SUBTASK:
                SubTask subTask = new SubTask(nameTask, description, idTask, idEpic);
                subTask.setStatus(TaskStatus.valueOf(statusTask));
                return subTask;
            case EPIC:
                Epic epic = new Epic(nameTask, description, idTask);
                epic.setStatus(TaskStatus.valueOf(statusTask));
                return epic;
        }
        //Напишите метод создания задачи из строки
        return null;
    }

    public String toString(Task task){
        //Напишите метод сохранения задачи в строку
        String str = String.format("%s,%S,%s,%s,%s,%s", task.getId(), task.getClass().getName(), task.getName(), task. getStatus(), task.getDescription(), task.getIdEpic());
        return str;
    }

    static String historyToString(HistoryManager manager){
        List<Task> listHistoryTask = manager.getHistory();
        int[] idTasks = new int[listHistoryTask.size()];
        for (int i = 0; i < listHistoryTask.size(); i++) {
            idTasks[i] = listHistoryTask.get(i).getId();
        }
        return Arrays.toString(idTasks);
    }

    static List<Integer> historyFromString(String value){
        String[] splitHistory = value.split(",");
        List<Integer> listId = new ArrayList<>();
        for (String s : splitHistory) {
            int id = Integer.parseInt(s);
            listId.add(id);
        }
        return listId;
    }
}
