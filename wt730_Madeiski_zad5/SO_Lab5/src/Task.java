import java.util.Random;

public class Task {
    private int timeToBeDone; //w milisekundach
    private int partOfProcessor; //procentowo

    public Task(int maxPartOfProcessor, int minLength, int maxLength) {
        Random r = new Random();
        this.partOfProcessor = r.nextInt(maxPartOfProcessor) + 1;
        this.timeToBeDone = r.nextInt(minLength, maxLength) + 1;
    }

    public Task(Task t) {
        this.timeToBeDone = t.getTimeToBeDone();
        this.partOfProcessor = t.getPartOfProcessor();
    }

    public Task() {
        this.timeToBeDone = 0;
        this.partOfProcessor = 0;
    }

    public int getTimeToBeDone() {
        return timeToBeDone;
    }
    public void setTimeToBeDone(int timeToBeDone) {
        this.timeToBeDone = timeToBeDone;
    }
    public int getPartOfProcessor() {
        return partOfProcessor;
    }
    public void setPartOfProcessor(int partOfProcessor) {
        this.partOfProcessor = partOfProcessor;
    }

    public String toString() {
        return "Task [timeToBeDone=" + timeToBeDone + ", partOfProcessor=" + partOfProcessor + "]";
    }

    public void doTask() {
        setTimeToBeDone(getTimeToBeDone() - 1);
    }

    public boolean isDone() {
        return getTimeToBeDone() <= 0;
    }

    public void decreaseToFinishTask(int n) {
        setTimeToBeDone(getTimeToBeDone() * (n/getPartOfProcessor()));
        setPartOfProcessor(n);
    }

}
