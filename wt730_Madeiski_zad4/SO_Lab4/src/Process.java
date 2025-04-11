import java.util.ArrayList;
import java.util.Random;

public class Process implements Comparable<Process> {
    private int id;
    private int pageMinNum;
    private int pageMaxNum;
    private int refSize;
    private int[] pagesTab;
    private int allocatedFrames;
    private int currentFrames;
    private int processErrors;
    private boolean suspended = false;

    public Process(int id, int pageMinNum, int pageMaxNum, int refSize) {
        this.id = id;
        this.pageMinNum = pageMinNum;
        this.pageMaxNum = pageMaxNum;
        this.refSize = refSize;
        this.allocatedFrames = 0;
        this.currentFrames = 0;
        this.pagesTab = new int[pageMaxNum - pageMinNum + 1];
        this.processErrors = 0;
        Random r = new Random();
        int index = 0;
        for (int i = pageMinNum; i < pageMaxNum + 1; i++) {
            pagesTab[index] = i;
            index++;
        }
    }
    public Process(int id, int pageMinNum, int pageMaxNum, int refSize, int allocatedFrames) {
        this.id = id;
        this.pageMinNum = pageMinNum;
        this.pageMaxNum = pageMaxNum;
        this.refSize = refSize;
        this.allocatedFrames = allocatedFrames;
        this.pagesTab = new int[pageMaxNum - pageMinNum + 1];
        Random r = new Random();
        int index = 0;
        for (int i = pageMinNum; i < pageMaxNum + 1; i++) {
            pagesTab[index] = i;
            index++;
        }
    }
    public Process(int id, int pageMinNum, int pageMaxNum, int refSize, int[] pagesTab, int allocatedFrames, int currentFrames) {
        this.id = id;
        this.pageMinNum = pageMinNum;
        this.pageMaxNum = pageMaxNum;
        this.refSize = refSize;
        this.pagesTab = pagesTab;
        this.allocatedFrames = allocatedFrames;
        this.currentFrames = currentFrames;
    }

    public int getPageMinNum() {
        return pageMinNum;
    }
    public void setPageMinNum(int pageMinNum) {
        this.pageMinNum = pageMinNum;
    }
    public int getPageMaxNum() {
        return pageMaxNum;
    }
    public void setPageMaxNum(int pageMaxNum) {
        this.pageMaxNum = pageMaxNum;
    }
    public int getRefSize() {
        return refSize;
    }
    public void setRefSize(int refSize) {
        this.refSize = refSize;
    }
    public int[] getPagesTab() {
        return pagesTab;
    }
    public void setPagesTab(int[] pagesTab) {
        this.pagesTab = pagesTab;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAllocatedFrames() {
        return allocatedFrames;
    }
    public void setAllocatedFrames(int allocatedFrames) {
        this.allocatedFrames = allocatedFrames;
    }
    public int getCurrentFrames() {
        return currentFrames;
    }
    public void setCurrentFrames(int currentFrames) {
        this.currentFrames = currentFrames;
    }
    public int getProcessErrors() {
        return processErrors;
    }
    public void setProcessErrors(int processErrors) {
        this.processErrors = processErrors;
    }
    public boolean isSuspended() {
        return suspended;
    }
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    @Override
    public String toString() {
        String returnString = "( id=" + id + " [ ";
        for (int page : pagesTab) {
           returnString += page + ", ";
        }
        returnString += "] )";
        return returnString;
    }
    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Process)) {
            return false;
        }
        Process p = (Process) obj;
        if (p.getId() == id) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public int compareTo(Process p) {
        return p.getPagesTab().length - pagesTab.length;
    }

    public ArrayList<Integer> generate() {
        ArrayList<Integer> ref = new ArrayList<>();
        Random r = new Random();
        int refCount = 0;
        while (refCount < refSize) {
            int number = r.nextInt(pagesTab.length);
            int pageToAdd = pagesTab[number];
            ref.add(pageToAdd);
            refCount++;
        }
        return ref;
    }

    public static void main(String[] args) {
        Process p1 = new Process(1, 3,10,4);
        System.out.println(p1);
        System.out.println(p1.generate());
        System.out.println(p1.generate());
        System.out.println(p1.generate());
        System.out.println(p1.generate());
    }
}
