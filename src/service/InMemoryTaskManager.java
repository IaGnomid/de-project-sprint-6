package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, SubTask> subTasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected HistoryManager historyManager = (HistoryManager) Managers.getDefaultHistory();

    private int id = 1;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer generatingId() {
        return id++;
    }

    @Override
    public Map<Integer, Task> getAllTasks() {
        return new HashMap<>(tasks);
    }

    @Override
    public Map<Integer, Epic> getAllEpics() {
        return new HashMap<>(epics);
    }

    @Override
    public Map<Integer, SubTask> getAllSubTasks() {
        return new HashMap<>(subTasks);
    }

    @Override
    public void delAllSubTasks() {
        if (!subTasks.isEmpty()) {
            for (Map.Entry<Integer, Epic> pair : epics.entrySet()) {
                pair.getValue().clearList();
                pair.getValue().setStatus(TaskStatus.NEW);
            }
            for(Map.Entry<Integer, SubTask> pair : subTasks.entrySet()){
                historyManager.remove(pair.getKey());
            }
            subTasks.clear();
        }
    }

    @Override
    public void delAllEpics() {
        for(Map.Entry<Integer, SubTask> pair : subTasks.entrySet()){
            historyManager.remove(pair.getKey());
        }
        for (Map.Entry<Integer, Epic> pair : epics.entrySet()) {
            historyManager.remove(pair.getKey());
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void delAllTasks() {
        for (Map.Entry<Integer, Task> pair : tasks.entrySet()) {
            historyManager.remove(pair.getKey());
        }
        tasks.clear();
    }

    @Override
    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void createSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        Epic tempEpic = epics.get(subTask.getIdEpic());
        tempEpic.addList(subTask.getId());
        checkStatus(epics.get(subTask.getIdEpic()));
    }

    @Override
    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateTask(Task newTask) {
        if (tasks.containsKey(newTask.getId())) {
            tasks.put(newTask.getId(), newTask);
        }
    }

    @Override
    public void updateSubTask(SubTask newTask) {
        if (subTasks.containsKey(newTask.getId())) {
            subTasks.put(newTask.getId(), newTask);
            checkStatus(epics.get(newTask.getIdEpic()));
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())) {
            epics.put(newEpic.getId(), newEpic);
        }
    }

    @Override
    public boolean dellEpicById(int id) {
        if (epics.containsKey(id)) {
            List<Integer> listId = epics.get(id).getSubTasks();
            for (Integer subTaskId : listId) {
                subTasks.remove(subTaskId);
                historyManager.remove(subTaskId);
            }
            epics.remove(id);
            historyManager.remove(id);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean dellSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            int idEpic = subTasks.get(id).getIdEpic();
            Epic epic = epics.get(idEpic);
            List<Integer> subTasksId = epic.getSubTasks();
            subTasksId.remove((Integer)id);
            subTasks.remove(id);
            historyManager.remove(id);
            checkStatus(epic);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean dellTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public List<SubTask> getSubtasksByEpicId(int epicID) {
        List<Integer> list = epics.get(epicID).getSubTasks();
        List<SubTask> subTasksList = new ArrayList<>();
        for (Integer subTuskId : list) {
            subTasksList.add(subTasks.get(subTuskId));
        }
        return subTasksList;
    }

    public void checkStatus(Epic epic) {
        List<Integer> subTasksList = epic.getSubTasks();
        if (!subTasksList.isEmpty()) {
            int countNew = 0;
            int countDone = 0;
            Task task;
            for (Integer subtaskId : subTasksList) {
                task = subTasks.get(subtaskId);
                switch (task.getStatus()) {
                    case NEW:
                        countNew++;
                        break;
                    case DONE:
                        countDone++;
                        break;
                    case IN_PROGRESS:
                        epic.setStatus(TaskStatus.IN_PROGRESS);
                        return;
                }
            }
            if(countNew == subTasksList.size()){
                epic.setStatus(TaskStatus.NEW);
            } else if (countDone == subTasksList.size()) {
                epic.setStatus(TaskStatus.DONE);
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            }
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
