package src;

import model.Epic;
import model.SubTask;
import model.Task;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        manager.createEpic(new Epic("model.Epic name1", "Description epic1", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name2", "Description epic2", manager.generatingId()));
        manager.createSubTask(new SubTask("Subtask1", "Сабтаск в Эпике id 2", manager.generatingId(), 2));
        manager.createSubTask(new SubTask("Subtask2", "Сабтаск в Эпике id 2", manager.generatingId(), 2));
        manager.createSubTask(new SubTask("Subtask3", "Сабтаск в Эпике id 2", manager.generatingId(), 2));
        manager.createSubTask(new SubTask("Subtask4", "Сабтаск в Эпике id 2", manager.generatingId(), 2));
        manager.createTask(new Task("Task1", "DescriptionTask1", manager.generatingId()));

        System.out.println("Обращаемся к таскам");
        //System.out.println(manager.getSubtasksByEpicId(2));
        System.out.println(manager.getEpic(1));
        System.out.println(manager.getEpic(2));
        System.out.println(manager.getEpic(1));
        System.out.println(manager.getEpic(2));
        System.out.println(manager.getEpic(2));
        System.out.println(manager.getEpic(2));
        System.out.println(manager.getEpic(2));
        System.out.println(manager.getEpic(1));



        //manager.dellHistoryTask(2);

        System.out.println("Печатаем историю обращений");
        List<Task> list = manager.getHistory();
        for (Task task : list) {
            System.out.println(task);
        }

//        System.out.println("Печатаем историю обращений");
//        list = manager.getHistory();
//        for (Task task : list) {
//            System.out.println(task);
//        }
    }
}
