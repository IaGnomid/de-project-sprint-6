package model;

public class SubTask extends Task {
    private int idEpic;

    public SubTask(String name, String description, int id, int idEpic) {
        super(name, description, id);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public String toString() {
        return "model.SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                "idEpic=" + idEpic +
                '}';
    }
}
