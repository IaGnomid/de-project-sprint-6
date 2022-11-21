package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subTasksList = new ArrayList<>();

    public void addList(int id) {
        subTasksList.add(id);
    }

    public void clearList() {
        subTasksList.clear();
    }

    public List<Integer> getSubTasksList() {
        return subTasksList;
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
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
