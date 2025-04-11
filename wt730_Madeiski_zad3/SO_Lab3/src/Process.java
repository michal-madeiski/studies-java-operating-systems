import java.util.ArrayList;
import java.util.Random;

public class Process {
    private int id;
    private int pageMinNum;
    private int pageMaxNum;
    private int refSize;
    private Page[] pagesTab;

    public Process(int id, int pageMinNum, int pageMaxNum, int refSize) {
        this.id = id;
        this.pageMinNum = pageMinNum;
        this.pageMaxNum = pageMaxNum;
        this.refSize = refSize;
        this.pagesTab = new Page[pageMaxNum - pageMinNum + 1];
        Random r = new Random();
        int index = 0;
        for (int i = pageMinNum; i < pageMaxNum + 1; i++) {
            pagesTab[index] = new Page(i);
            index++;
        }
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
    public Page[] getPagesTab() {
        return pagesTab;
    }
    public void setPagesTab(Page[] pagesTab) {
        this.pagesTab = pagesTab;
    }
    public String toString() {
        String returnString = "( id=" + id + " [ ";
        for (Page page : pagesTab) {
           returnString += page + ", ";
        }
        returnString += "] )";
        return returnString;
    }

    public ArrayList<Page> generate() {
        ArrayList<Page> ref = new ArrayList<>();
        Random r = new Random();
        int refCount = 0;
        while (refCount < refSize) {
            int number = r.nextInt(pagesTab.length);
            Page pageToAdd = pagesTab[number];
            if (!ref.contains(pageToAdd)) {
                ref.add(pageToAdd);
                refCount++;
            }
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
