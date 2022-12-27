package model;

public class Task {
    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private int idEpic;

    public Task(String name, String description, int id, int idEpic) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = TaskStatus.NEW;
        this.idEpic = idEpic;
    }
    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = TaskStatus.NEW;
    }

    public Integer getIdEpic() {
        return null;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

}
