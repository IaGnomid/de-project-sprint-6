package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager{

    private Path file;
    public static CSVTaskConverter converter = new CSVTaskConverter();

    public FileBackedTaskManager(Path file) {
        this.file = file;
    }
//Восстанавливаем FileBackedTaskManager из файла
    //Если нет файла, создпем новый экземпляр
public static FileBackedTaskManager loadFromFile(Path file){
    FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
    int generatorId = 0;
    try {
        List<String> list = Files.readAllLines(file);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) != null) {
                Task task = converter.fromString(list.get(i));
                if (task.getId() > generatorId) {
                    generatorId = task.getId();
                }
                fileBackedTaskManager.tasks.put();
            }else {
                converter.historyFromString(list.get(i+1));
                break;
            }
        }
        fileBackedTaskManager.setId(generatorId);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

//Привязать сабтаски и эпики
//Проходимся по сабтаскам и связоваем сабтаски и эпики
    return fileBackedTaskManager;
}
    public <T extends Task> void save(Map<Integer, T> tasks) {
        //
        // Записываем в файл - BufferedReader writer
        //writer.write(CSVTaskConverter.getHeader())
        //перевод строки

        //Сериализация и запись тасков
        //По очереди проходим каждую мапу с тасками
        //Внутрикаждого for мы сериализуем таску CSVTaskFormater.toString(task)

        //Записать новую строку
        //
        //Сериализуем историю
        //CSVTaskConverter.historyToString()
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("file.csv"))) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();
            for (Map.Entry idAndTask : tasks.entrySet()){
                writer.write(converter.toString((Task) idAndTask.getValue()));
            }
            writer.newLine();
            writer.newLine();
            writer.write(converter.historyToString(historyManager));
        }catch (IOException e) {
            try {
                throw new ManagerSaveException("ошибка");
            } catch (ManagerSaveException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save(tasks);
    }

    @Override
    public void createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save(subTasks);
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save(epics);
    }

    @Override
    public void updateTask(Task newTask) {
        if (tasks.containsKey(newTask.getId())) {
            tasks.put(newTask.getId(), newTask);
            save(tasks);
        }
    }

    @Override
    public void updateSubTask(SubTask newTask) {
        if (subTasks.containsKey(newTask.getId())) {
            subTasks.put(newTask.getId(), newTask);
            checkStatus(epics.get(newTask.getIdEpic()));
            save(subTasks);
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())) {
            epics.put(newEpic.getId(), newEpic);
            save(epics);
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
            save(epics);
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
            save(subTasks);
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
            save(tasks);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        save(subTasks);
        return subTask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        save(subTasks);
        return epic;
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        save(subTasks);
        return task;
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
        save(epics);
    }
}
