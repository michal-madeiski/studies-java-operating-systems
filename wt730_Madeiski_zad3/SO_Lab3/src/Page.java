public class Page implements Comparable<Page> {
    @Override
    public int compareTo(Page p) {
        return p.getNextUse() - nextUse;
    }

    private int num;
    private boolean refBit;
    private int nextUse;

    public Page(int num) {
        this.num = num;
        this.refBit = false;
        this.nextUse = 0;
    }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public boolean isRefBit() {
        return refBit;
    }
    public void setRefBit(boolean refBit) {
        this.refBit = refBit;
    }
    public int getNextUse() {
        return nextUse;
    }
    public void setNextUse(int nextUse) {
        this.nextUse = nextUse;
    }
    public String toString() {
        return "" + num;
    }

}
