import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Algorithms {
    private ArrayList<Page> refList;
    private int vrMemorySize;
    private int frameSize;
    int pagesAmount;

    public Algorithms(ArrayList<Page> refList, int vrMemorySize, int frameSize) {
        this.refList = refList;
        this.vrMemorySize = vrMemorySize;
        this.frameSize = frameSize;
        this.pagesAmount = refList.size();
    }

    public ArrayList<Page> getRefList() {
        return refList;
    }
    public void setRefList(ArrayList<Page> refList) {
        this.refList = refList;
    }
    public int getVrMemorySize() {
        return vrMemorySize;
    }
    public void setVrMemorySize(int vrMemorySize) {
        this.vrMemorySize = vrMemorySize;
    }
    public int getFrameSize() {
        return frameSize;
    }
    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }
    public int getPagesAmount() {
        return pagesAmount;
    }
    public void setPagesAmount(int pagesAmount) {
        this.pagesAmount = pagesAmount;
    }

    public ArrayList<Page> deepCopyPages(ArrayList<Page> list) {
        ArrayList<Page> returnList = new ArrayList<>();
        for (Page page : list) {
            returnList.add(page);
        }
        return returnList;
    }

    public int FIFO() {
        int pageErrors = 0;

        ArrayList<Page> pagesList = deepCopyPages(refList);
        ArrayList<Page> frames = new ArrayList<>();

        for (Page page : pagesList) {
            if (frames.size() < frameSize) {
                if (!frames.contains(page)) {
                    pageErrors++;
                    frames.add(page);
                }
            } else {
                if (!frames.contains(page)) {
                    pageErrors++;
                    frames.removeFirst();
                    frames.add(page);
                }
            }
        }
        return pageErrors;
    }

    public int RAND() {
        int pageErrors = 0;

        ArrayList<Page> pagesList = deepCopyPages(refList);
        ArrayList<Page> frames = new ArrayList<>();

        for (Page page : pagesList) {
            if (frames.size() < frameSize) {
                if (!frames.contains(page)) {
                    pageErrors++;
                    frames.add(page);
                }
            } else {
                if (!frames.contains(page)) {
                    pageErrors++;
                    Random r = new Random();
                    int idx = r.nextInt(frameSize);
                    frames.add(idx, page);
                    frames.remove(idx + 1);
                }
            }
        }
        return pageErrors;
    }

    public int LRU() {
        int pageErrors = 0;

        ArrayList<Page> pagesList = deepCopyPages(refList);
        ArrayList<Page> frames = new ArrayList<>();

        for (Page page : pagesList) {
            if (frames.size() < frameSize) {
                if (!frames.contains(page)) {
                    pageErrors++;
                    frames.add(page);
                } else {
                    frames.remove(page);
                    frames.add(page);
                }
            } else {
                if (!frames.contains(page)) {
                    pageErrors++;
                    frames.removeFirst();
                    frames.add(page);
                } else {
                    frames.remove(page);
                    frames.add(page);
                }
            }
        }
        return pageErrors;
    }

    public int LRUaprx() {
        int pageErrors = 0;

        ArrayList<Page> pagesList = deepCopyPages(refList);
        ArrayList<Page> frames = new ArrayList<>();
        ArrayList<Page> fifoQueue = new ArrayList<>();

        for (Page page : pagesList) {
            if (frames.size() < frameSize) {
                if (!frames.contains(page)) {
                    pageErrors++;
                    page.setRefBit(true);
                    frames.add(page);
                    fifoQueue.add(page);
                }
            } else {
                if (!frames.contains(page)) {
                    int temp = 0;
                    for (Page p : frames) {
                        temp++;
                        if (p.isRefBit()) {
                            p.setRefBit(false);
                        } else {
                            break;
                        }
                    }
                    if (temp - 1 == frames.size() - 1) {
                        frames.removeFirst();
                        fifoQueue.removeFirst();
                        page.setRefBit(true);
                        frames.add(0, page);
                        fifoQueue.add(page);
                    } else {
                        fifoQueue.removeFirst();
                        frames.remove(temp - 1);
                        page.setRefBit(true);
                        frames.add(temp, page);
                        fifoQueue.add(page);
                    }
                    pageErrors++;
                }
            }
        }

        return pageErrors;
    }

    public int OPT() {
        int pageErrors = 0;

        ArrayList<Page> pagesList = deepCopyPages(refList);
        ArrayList<Page> frames = new ArrayList<>();

        int idx = 0;
        for (Page page : pagesList) {
            if (frames.size() < frameSize) {
                if (!frames.contains(page)) {
                    pageErrors++;
                    frames.add(page);
                }
            } else {
                if (!frames.contains(page)) {
                    pageErrors++;
                    sortFramesByNextUse(frames, idx + 1);
                    frames.removeFirst();
                    frames.add(page);
                    resetNextUse(frames);
                }
            }
            idx++;
        }

        return pageErrors;
    }
    public void sortFramesByNextUse(ArrayList<Page> frames, int start) {
        for (Page page : frames) {
            page.setNextUse(refList.size() + 1);
            for (int i = start; i < refList.size(); i++) {
                if (refList.get(i).getNum() == page.getNum()) {
                    page.setNextUse(i);
                    break;
                }
            }
        }
        Collections.sort(frames);
    }
    public void resetNextUse(ArrayList<Page> frames) {
        for (Page page : frames) {
            page.setNextUse(0);
        }
    }

}
