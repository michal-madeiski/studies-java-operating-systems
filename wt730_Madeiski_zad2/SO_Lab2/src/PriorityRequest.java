public class PriorityRequest extends SimpleRequest{
    private int priority;
    public PriorityRequest(int id, int requestNumber, int priority) {
        super(id, requestNumber);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public String toString(){
        return super.toString() + "(" + priority + ")";
    }
}
