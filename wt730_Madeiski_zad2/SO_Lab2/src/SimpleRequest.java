public class SimpleRequest {
    private int arrivalTime;
    private int id;
    private int requestNumber;

    public SimpleRequest(int id, int requestNumber) {
        this.id = id;
        this.requestNumber = requestNumber;
        this.arrivalTime = 0;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRequestNumber() {
        return requestNumber;
    }
    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }
    public String toString() {
        return "" + requestNumber;
    }
}
