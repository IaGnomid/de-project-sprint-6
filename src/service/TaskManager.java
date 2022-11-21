package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {

    Integer generatingId();

    public Map<Integer, Task> getAllTasks();

    public Map<Integer, Epic> getAllEpics();

    public Map<Integer, SubTask> getAllSubTasks();

    public void delAllSubTasks();

    public void delAllEpics();

    public void delAllTasks();

    public void createTask(Task task);

    public void createSubTask(SubTask subTask);

    public void createEpic(Epic epic);

    public void updateTask(Task newTask);

    public void updateSubTask(SubTask newTask);

    public void updateEpic(Epic newEpic);

    public boolean dellEpicById(int id);

    public boolean dellSubTaskById(int id);

    public boolean dellTaskById(int id);

    public List<SubTask> getSubtasksByEpicId(int epicId);

    public Epic getEpic(int id);

    public SubTask getSubTask(int id);

    public Task getTask(int id);

    public List<Task> getHistory();

}
