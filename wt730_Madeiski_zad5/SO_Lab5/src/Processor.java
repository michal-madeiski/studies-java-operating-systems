import java.util.ArrayList;

public class Processor {
    private ArrayList<Task> tasks;
    private int currentLoad; //procentowo

    public Processor(ArrayList<Task> tasks, int currentLoad) {
        this.tasks = tasks;
        this.currentLoad = currentLoad;
    }

    public Processor(Processor processor) {
        this.tasks = processor.getTasks();
        this.currentLoad = processor.getCurrentLoad();
    }

    public Processor() {
        this.tasks = new ArrayList<>();
        this.currentLoad = 0;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public int getCurrentLoad() {
        return currentLoad;
    }
    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

}
