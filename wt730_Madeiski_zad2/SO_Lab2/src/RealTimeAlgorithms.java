import java.util.ArrayList;
import java.util.List;

public class RealTimeAlgorithms {
    private List<PriorityRequest> list;
    private int startHeadNum;
    private int minPos;
    private int maxPos;

    public RealTimeAlgorithms(List<PriorityRequest> list, int startHeadNum, int minPos, int maxPos) {
        this.list = list;
        this.startHeadNum = startHeadNum;
        this.minPos = minPos;
        this.maxPos = maxPos;
    }

    public List<PriorityRequest> getList() {
        return list;
    }
    public void setList(List<PriorityRequest> list) {
        this.list = list;
    }
    public int getStartHeadNum() {
        return startHeadNum;
    }
    public void setStartHeadNum(int startHeadNum) {
        this.startHeadNum = startHeadNum;
    }
    public int getMinPos() {
        return minPos;
    }
    public void setMinPos(int minPos) {
        this.minPos = minPos;
    }
    public int getMaxPos() {
        return maxPos;
    }
    public void setMaxPos(int maxPos) {
        this.maxPos = maxPos;
    }


    public int EDF(int n) {
        int shift = 0;

        int actPos = startHeadNum;
        ArrayList<PriorityRequest> tempPrioList = new ArrayList<>();
        for (PriorityRequest priorityRequest : list) {
            if (priorityRequest.getPriority() > 0) {
                tempPrioList.add(priorityRequest);
            }
        }
        tempPrioList.sort(new PriorityRequestComparator());

        for (PriorityRequest priorityRequest : tempPrioList) {
            shift += Math.abs(actPos - priorityRequest.getRequestNumber());
            actPos = priorityRequest.getRequestNumber();
        }

        ArrayList<SimpleRequest> tempList = new ArrayList<>();
        for (PriorityRequest priorityRequest : list) {
            if (priorityRequest.getPriority() == 0) {
                tempList.add(new SimpleRequest(priorityRequest.getId(), priorityRequest.getRequestNumber()));
            }
        }

        SimpleAlgorithms simpleAlgorithms = new SimpleAlgorithms(tempList, actPos, minPos, maxPos);
        switch (n) {
            case 2 -> shift += simpleAlgorithms.SSTF();
            case 3 -> shift += simpleAlgorithms.SCAN();
            case 4 -> shift += simpleAlgorithms.C_SCAN();
            default -> shift += simpleAlgorithms.FIFO();
        }

        return shift;
    }

    public int FD_SCAN(int n) {
        int shift = 0;

        int actPos = startHeadNum;
        ArrayList<PriorityRequest> tempPrioList = new ArrayList<>();
        ArrayList<PriorityRequest> tempListALL = new ArrayList<>();
        for (PriorityRequest priorityRequest : list) {
            if (priorityRequest.getPriority() > 0) {
                tempPrioList.add(priorityRequest);
            }
            tempListALL.add(priorityRequest);
        }
        tempPrioList.sort(new PriorityRequestComparator());

        for (PriorityRequest priorityRequest : tempPrioList) {
            if (priorityRequest.getId() != -1) {
                int newPos = priorityRequest.getRequestNumber();
                int oldPos = actPos;
                shift += Math.abs(actPos - priorityRequest.getRequestNumber());
                actPos = priorityRequest.getRequestNumber();
                for (PriorityRequest request : tempListALL) {
                    if (oldPos >= newPos) {
                        if (request.getRequestNumber() < oldPos && request.getRequestNumber() > newPos) {
                            request.setId(-1);
                        }
                    } else if (oldPos <= newPos) {
                        if (request.getRequestNumber() > oldPos && request.getRequestNumber() < newPos) {
                            request.setId(-1);
                        }
                    }
                }
                for (PriorityRequest request : tempPrioList) {
                    if (oldPos >= newPos) {
                        if (request.getRequestNumber() < oldPos && request.getRequestNumber() > newPos) {
                            request.setId(-1);
                        }
                    } else if (oldPos <= newPos) {
                        if (request.getRequestNumber() > oldPos && request.getRequestNumber() < newPos) {
                            request.setId(-1);
                        }
                    }
                }
            }
        }

        ArrayList<SimpleRequest> tempList = new ArrayList<>();
        for (PriorityRequest priorityRequest : tempListALL) {
            if (priorityRequest.getPriority() == 0 && priorityRequest.getId() != -1) {
                tempList.add(new SimpleRequest(priorityRequest.getId(), priorityRequest.getRequestNumber()));
            }
        }

        SimpleAlgorithms simpleAlgorithms = new SimpleAlgorithms(tempList, actPos, minPos, maxPos);
        switch (n) {
            case 2 -> shift += simpleAlgorithms.SSTF();
            case 3 -> shift += simpleAlgorithms.SCAN();
            case 4 -> shift += simpleAlgorithms.C_SCAN();
            default -> shift += simpleAlgorithms.FIFO();
        }

        return shift;
    }
}
