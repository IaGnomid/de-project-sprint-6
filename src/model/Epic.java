package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subTasks = new ArrayList<>();
    private int idEpic;

    public Epic(String name, String description, int id) {
        super(name, description, id);
    }

    public Epic(String name, String description, int id, int idEpic) {
        super(name, description, id);
        this.idEpic = idEpic;
    }

    public Integer getIdEpic() {
        return null;
    }

    public void addList(int id) {
        subTasks.add(id);
    }

    public void clearList() {
        subTasks.clear();
    }

    public List<Integer> getSubTasks() {
        return subTasks;
    }

    @Override
    public String toString() {
        return "model.SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
