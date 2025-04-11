import java.util.ArrayList;
import java.util.List;

public class SimpleAlgorithms {
    private List<SimpleRequest> list;
    private int startHeadNum;
    private int minPos;
    private int maxPos;

    public SimpleAlgorithms(List<SimpleRequest> list, int startHeadNum, int minPos, int maxPos) {
        this.list = list;
        this.startHeadNum = startHeadNum;
        this.minPos = minPos;
        this.maxPos = maxPos;
    }

    public List<SimpleRequest> getList() {
        return list;
    }
    public void setList(List<SimpleRequest> list) {
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

    public int FIFO() {
        int shift = 0;

        int actPos = startHeadNum;
        for (SimpleRequest simpleRequest : list) {
            shift += Math.abs(actPos - simpleRequest.getRequestNumber());
            actPos = simpleRequest.getRequestNumber();
        }

        return shift;
    }

    public int SSTF() {
        int shift = 0;

        int actPos = startHeadNum;
        List<SimpleRequest> tempList = new ArrayList<>();
        for (SimpleRequest simpleRequest : list) {
            tempList.add(simpleRequest);
        }

        while (tempList.size() > 0) {
            SimpleRequest closest = findClosest(tempList, actPos, maxPos);
            shift += Math.abs(actPos - closest.getRequestNumber());
            actPos = tempList.get(tempList.indexOf(closest)).getRequestNumber();
            tempList.remove(closest);
        }

        return shift;
    }
    private static SimpleRequest findClosest(List<SimpleRequest> list, int pos, int maxPos) {
        int minVal = maxPos;
        SimpleRequest returnReq = null;
        for (SimpleRequest simpleRequest : list) {
            if (Math.abs(simpleRequest.getRequestNumber() - pos) < minVal) {
                minVal = Math.abs(simpleRequest.getRequestNumber() - pos);
                returnReq = simpleRequest;
            }
        }
        return returnReq;
    }

    public int SCAN() {
        int shift = 0;

        int actPos = startHeadNum;
        List<SimpleRequest> tempList = list;
        tempList.sort(new SimpleRequestComparator());
        ArrayList<SimpleRequest> leftRequestList = new ArrayList<>();
        ArrayList<SimpleRequest> rightRequestList = new ArrayList<>();

        int i = 0;
        while (i < tempList.size() && tempList.get(i).getRequestNumber() < actPos) {
            leftRequestList.add(tempList.get(i));
            i++;
        }

        int j = i;
        while (j < tempList.size() && tempList.get(j).getRequestNumber() > actPos) {
            rightRequestList.add(tempList.get(j));
            j++;
        }

        for (int k = leftRequestList.size() - 1; k >= 0 ; k--) {
            shift += Math.abs(actPos - leftRequestList.get(k).getRequestNumber());
            actPos = leftRequestList.get(k).getRequestNumber();
        }

        shift += actPos;
        actPos = minPos;
        for (int m = 0; m < rightRequestList.size() ; m++) {
            shift += Math.abs(actPos - rightRequestList.get(m).getRequestNumber());
            actPos = rightRequestList.get(m).getRequestNumber();
        }

        return shift;
    }

    public int C_SCAN() {
        int shift = 0;

        int actPos = startHeadNum;
        List<SimpleRequest> tempList = list;
        tempList.sort(new SimpleRequestComparator());
        ArrayList<SimpleRequest> leftRequestList = new ArrayList<>();
        ArrayList<SimpleRequest> rightRequestList = new ArrayList<>();

        int i = 0;
        while (i < tempList.size() && tempList.get(i).getRequestNumber() < actPos) {
            leftRequestList.add(tempList.get(i));
            i++;
        }

        int j = i;
        while (j < tempList.size() && tempList.get(j).getRequestNumber() > actPos) {
            rightRequestList.add(tempList.get(j));
            j++;
        }

        for (int k = leftRequestList.size() - 1; k >= 0 ; k--) {
            shift += Math.abs(actPos - leftRequestList.get(k).getRequestNumber());
            actPos = leftRequestList.get(k).getRequestNumber();
        }

        shift += actPos;
        actPos = maxPos;
        //shift += maxPos;
        //shift += 1;
        rightRequestList.sort(new SimpleRequestComparator().reversed());
        for (int m = 0; m < rightRequestList.size() ; m++) {
            shift += Math.abs(actPos - rightRequestList.get(m).getRequestNumber());
            actPos = rightRequestList.get(m).getRequestNumber();
        }

        return shift;
    }
}
