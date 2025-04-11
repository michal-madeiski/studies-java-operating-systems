import java.util.ArrayList;
import java.util.Collections;

public class Algorithms {
    private final ArrayList<Process> availableProcesses;
    private ArrayList<Integer[]> referenceList;
    private int vrMemorySize;
    private int frameSize;
    int processesAmount;

    public Algorithms(ArrayList<Process> availableProcesses, ArrayList<Integer[]> refList, int vrMemorySize, int frameSize) {
        this.availableProcesses = availableProcesses;
        this.referenceList = refList;
        this.vrMemorySize = vrMemorySize;
        this.frameSize = frameSize;
        this.processesAmount = refList.size();
    }

    public ArrayList<Integer[]> getReferenceList() {
        return referenceList;
    }
    public void setReferenceList(ArrayList<Integer[]> referenceList) {
        this.referenceList = referenceList;
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
    public int getProcessesAmount() {
        return processesAmount;
    }
    public void setProcessesAmount(int pagesAmount) {
        this.processesAmount = pagesAmount;
    }

    public ArrayList<Process> deepCopyProcesses(ArrayList<Process> list) {
        ArrayList<Process> returnList = new ArrayList<>();
        for (Process p : list) {
            returnList.add(new Process(p.getId(), p.getPageMinNum(), p.getPageMaxNum(), p.getRefSize(), p.getPagesTab(), p.getAllocatedFrames(), p.getCurrentFrames()));
        }
        return returnList;
    }

    private int LRU(int x) {
        int pageErrors = 0;

        for (Process p : availableProcesses) {
            p.setProcessErrors(0);
        }

        ArrayList<Process> allProcesses = deepCopyProcesses(availableProcesses);
        allProcesses.sort(new ProcessIDComp());

        ArrayList<Integer> frames = new ArrayList<>();

        for (Integer[] p : referenceList) {
            for (Integer i : p) {
                if (hasProcessAvailableFrame(p[0], allProcesses)) {
                    if (!frames.contains(i)) {
                        pageErrors++;
                        increaseProcessErrors(p[0], allProcesses);
                        frames.add(i);
                        increaseProcessCurrentFrames(p[0], allProcesses);
                    } else {
                        frames.remove(i);
                        frames.add(i);
                    }
                } else {
                    if (!frames.contains(i)) {
                        pageErrors++;
                        increaseProcessErrors(p[0], allProcesses);
                        removeFromFramesLRUForThisProcess(p[1], p[2], frames);
                        frames.add(i);
                    } else {
                        frames.remove(i);
                        frames.add(i);
                    }
                }
            }
        }

        if (x == 0) {
            System.out.printf("%-5s %-5s %-5s \n", "id", "pages", "errors");
            for (Process p : allProcesses) {
                System.out.printf("%-5d %-5d %-5d \n", p.getId(), p.getPagesTab().length, p.getProcessErrors());
            }
        }

        return pageErrors;
    }
    private int LRU_zm(int x, int t) {
        int pageErrors = 0;

        for (Process p : availableProcesses) {
            p.setProcessErrors(0);
        }

        ArrayList<Process> allProcesses = deepCopyProcesses(availableProcesses);
        allProcesses.sort(new ProcessIDComp());

        ArrayList<Integer> frames = new ArrayList<>();
        ArrayList<Integer[]> usedProcesses = new ArrayList<>();
        int actualTime = 1;

        for (Integer[] p : referenceList) {
            if (!isProcessSuspended(p[0], allProcesses)) {
                usedProcesses.add(p);
                for (Integer i : p) {
                    if (hasProcessAvailableFrame(p[0], allProcesses)) {
                        if (!frames.contains(i)) {
                            pageErrors++;
                            increaseProcessErrors(p[0], allProcesses);
                            frames.add(i);
                            increaseProcessCurrentFrames(p[0], allProcesses);
                        } else {
                            frames.remove(i);
                            frames.add(i);
                        }
                    } else {
                        if (!frames.contains(i)) {
                            pageErrors++;
                            increaseProcessErrors(p[0], allProcesses);
                            removeFromFramesLRUForThisProcess(p[1], p[2], frames);
                            frames.add(i);
                        } else {
                            frames.remove(i);
                            frames.add(i);
                        }
                    }
                }
            }
            if (actualTime % t ==  0) {
                int allWSSsizes = 0;
                ArrayList<Integer> WSSsizes = new ArrayList<>();
                for (Process proc : allProcesses) {
                    if (!proc.isSuspended()) {
                        int s = WSS(proc.getId(), usedProcesses, t).size();
                        WSSsizes.add(s);
                        allWSSsizes += s;
                    }
                }
                if (allWSSsizes > frameSize) {
                    int framesForOtherProcesses = suspendAndReturnItsAlocFramesZM(WSSsizes, allProcesses);
                    while (framesForOtherProcesses > 0) {
                        for (Process proc : allProcesses) {
                            if (!proc.isSuspended()) {
                                proc.setAllocatedFrames(proc.getAllocatedFrames() + 1);
                                framesForOtherProcesses--;
                            }
                        }
                    }
                }
                if (allWSSsizes < frameSize - (allWSSsizes/WSSsizes.size())) {
                    int unSuspendProc = 0;
                    for (Process proc : allProcesses) {
                        if (proc.isSuspended()) {
                            proc.setSuspended(false);
                            break;
                        }
                    }
                    for (Process proc : allProcesses) {
                        if (!proc.isSuspended()) {
                            unSuspendProc++;
                        }
                    }
                    int f = frameSize / unSuspendProc;
                    if (f == 0) f++;
                    for (Process proc : allProcesses) {
                        if (!proc.isSuspended()) {
                            proc.setAllocatedFrames(f);
                        }
                    }
                }
            }
            actualTime++;
        }
        if (x == 0) {
            System.out.printf("%-5s %-5s %-5s \n", "id", "pages", "errors");
            for (Process proc : allProcesses) {
                System.out.printf("%-5d %-5d %-5d \n", proc.getId(), proc.getPagesTab().length, proc.getProcessErrors());
            }
        }
        return pageErrors;
    }
    private int LRU_pff(int x, int t, int min, int max) {
        int minErrors = (min*frameSize)/10;
        int maxErrors = (max*frameSize)/10;

        int pageErrors = 0;

        for (Process p : availableProcesses) {
            p.setProcessErrors(0);
        }

        ArrayList<Process> allProcesses = deepCopyProcesses(availableProcesses);
        allProcesses.sort(new ProcessIDComp());

        ArrayList<Integer> frames = new ArrayList<>();
        ArrayList<Integer[]> usedProcesses = new ArrayList<>();
        int actualTime = 1;

        for (Integer[] p : referenceList) {
            if (!isProcessSuspended(p[0], allProcesses)) {
                usedProcesses.add(p);
                for (Integer i : p) {
                    if (hasProcessAvailableFrame(p[0], allProcesses)) {
                        if (!frames.contains(i)) {
                            pageErrors++;
                            increaseProcessErrors(p[0], allProcesses);
                            frames.add(i);
                            increaseProcessCurrentFrames(p[0], allProcesses);
                        } else {
                            frames.remove(i);
                            frames.add(i);
                        }
                    } else {
                        if (!frames.contains(i)) {
                            pageErrors++;
                            increaseProcessErrors(p[0], allProcesses);
                            removeFromFramesLRUForThisProcess(p[1], p[2], frames);
                            frames.add(i);
                        } else {
                            frames.remove(i);
                            frames.add(i);
                        }
                    }
                }
            }
            if (actualTime % t ==  0) {
                int framesFromMostErrors = suspendAndReturnItsAlocFramesPFF(allProcesses, maxErrors);
                int framesFromSmallest = giveToOtherIfSmallerThanMin(allProcesses, minErrors);
                int sum = framesFromMostErrors + framesFromSmallest;

                while (sum > 0) {
                    for (Process proc : allProcesses) {
                        if (!proc.isSuspended()) {
                            proc.setAllocatedFrames(proc.getAllocatedFrames() + 1);
                            sum--;
                        }
                    }
                }

                if (framesFromMostErrors == 0) {
                    for (Process proc : allProcesses) {
                        if (!proc.isSuspended()) {
                            proc.setSuspended(false);
                            break;
                        }
                    }
                    int unSuspendProc = 0;
                    for (Process proc : allProcesses) {
                        if (!proc.isSuspended()) {
                            unSuspendProc++;
                        }
                    }
                    int f = frameSize / unSuspendProc;
                    if (f == 0) f++;
                    for (Process proc : allProcesses) {
                        if (!proc.isSuspended()) {
                            proc.setAllocatedFrames(f);
                        }
                    }
                }
            }
            actualTime++;
        }
        if (x == 0) {
            System.out.printf("%-5s %-5s %-5s \n", "id", "pages", "errors");
            for (Process proc : allProcesses) {
                System.out.printf("%-5d %-5d %-5d \n", proc.getId(), proc.getPagesTab().length, proc.getProcessErrors());
            }
        }
        return pageErrors;
    }

    public int EqualAllocation(int x) {
        int alocFrames = frameSize / availableProcesses.size();
        if (alocFrames == 0) alocFrames++;
        for (Process availableProcess : availableProcesses) {
            availableProcess.setAllocatedFrames(alocFrames);
        }
        return LRU(x);
    }
    public int ProportionalAllocation(int x) {
        int allPages = 0;
        for (Process p : availableProcesses) {
            allPages += p.getPagesTab().length;
        }
        int tempAllFrames = frameSize;
        for (Process p : availableProcesses) {
            int alocFrames = (p.getPagesTab().length * frameSize) / allPages;
            if (alocFrames == 0) alocFrames++;
            p.setAllocatedFrames(alocFrames);
            tempAllFrames -= alocFrames;
        }

        ArrayList<Process> tempProcList = availableProcesses;
        Collections.sort(tempProcList);
        while (tempAllFrames > 0) {
            for (Process p : tempProcList) {
                if (tempAllFrames > 0) {
                    p.setAllocatedFrames(p.getAllocatedFrames() + 1);
                    tempAllFrames--;
                }
            }
        }
        return LRU(x);
    }
    public int ZoneModel(int x, int t) {
        int allPages = 0;
        for (Process p : availableProcesses) {
            allPages += p.getPagesTab().length;
        }
        int tempAllFrames = frameSize;
        for (Process p : availableProcesses) {
            int alocFrames = (p.getPagesTab().length * frameSize) / allPages;
            if (alocFrames == 0) alocFrames++;
            p.setAllocatedFrames(alocFrames);
            tempAllFrames -= alocFrames;
        }

        ArrayList<Process> tempProcList = availableProcesses;
        Collections.sort(tempProcList);
        while (tempAllFrames > 0) {
            for (Process p : tempProcList) {
                if (tempAllFrames > 0) {
                    p.setAllocatedFrames(p.getAllocatedFrames() + 1);
                    tempAllFrames--;
                }
            }
        }
        return LRU_zm(x, t);
    }
    public int PFF(int x, int t, int min, int max) {
        int allPages = 0;
        for (Process p : availableProcesses) {
            allPages += p.getPagesTab().length;
        }
        int tempAllFrames = frameSize;
        for (Process p : availableProcesses) {
            int alocFrames = (p.getPagesTab().length * frameSize) / allPages;
            if (alocFrames == 0) alocFrames++;
            p.setAllocatedFrames(alocFrames);
            tempAllFrames -= alocFrames;
        }

        ArrayList<Process> tempProcList = availableProcesses;
        Collections.sort(tempProcList);
        while (tempAllFrames > 0) {
            for (Process p : tempProcList) {
                if (tempAllFrames > 0) {
                    p.setAllocatedFrames(p.getAllocatedFrames() + 1);
                    tempAllFrames--;
                }
            }
        }
        return LRU_pff(x, t, min, max);
    }

    private boolean hasProcessAvailableFrame(int x, ArrayList<Process> availableProcesses) {
        for (Process p : availableProcesses) {
            if (p.getId() == x) {
                if (p.getCurrentFrames() < p.getAllocatedFrames()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    private void increaseProcessCurrentFrames(int x, ArrayList<Process> availableProcesses) {
        for (Process p : availableProcesses) {
            if (p.getId() == x) {
                p.setCurrentFrames(p.getCurrentFrames() + 1);
            }
        }
    }
    private void removeFromFramesLRUForThisProcess(int min, int max, ArrayList<Integer> frames) {
        for (Integer frame : frames) {
            if (frame >= min && frame <= max) {
                frames.remove(frame);
                break;
            }
        }
    }
    private void increaseProcessErrors(int x, ArrayList<Process> availableProcesses) {
        for (Process p : availableProcesses) {
            if (p.getId() == x) {
                p.setProcessErrors(p.getProcessErrors() + 1);
            }
        }
    }

    private boolean isProcessSuspended(int p, ArrayList<Process> procList) {
        for (Process proc : procList) {
            if (proc.getId() == p) {
                return proc.isSuspended();
            }
        }
        return false;
    }
    private ArrayList<Integer> WSS(int p, ArrayList<Integer[]> usedProcesses, int t) {
        ArrayList<Integer> WSS = new ArrayList<>();
        int size = usedProcesses.size();
        for (int i = size - 1; i > size - t - 1; i--) {
            if (usedProcesses.get(i)[0] == p) {
                for (int j = 3; j < usedProcesses.get(i).length ; j++) {
                    if (!WSS.contains(usedProcesses.get(i)[j])) {
                        WSS.add(usedProcesses.get(i)[j]);
                    }
                }
            }
        }
        return WSS;
    }
    private int suspendAndReturnItsAlocFramesZM(ArrayList<Integer> wss, ArrayList<Process> procList) {
        int max = 0;
        for (int i = 0; i < wss.size(); i++) {
            if (wss.get(i) > max) {
                max = i;
            }
        }
        procList.get(max).setSuspended(true);
        return procList.get(max).getAllocatedFrames();
    }
    private int suspendAndReturnItsAlocFramesPFF(ArrayList<Process> procList, int m) {
        int max = 0;
        for (int i = 0; i < procList.size(); i++) {
            if (procList.get(i).getProcessErrors() > procList.get(max).getProcessErrors()) {
                max = i;
            }
        }
        if (procList.get(max).getProcessErrors() > m) {
            procList.get(max).setSuspended(true);
            return procList.get(max).getAllocatedFrames();
        } else {
            return 0;
        }
    }
    private int giveToOtherIfSmallerThanMin(ArrayList<Process> procList, int m) {
        int min = 0;
        for (int i = 0; i < procList.size(); i++) {
            if (procList.get(i).getProcessErrors() < procList.get(min).getProcessErrors()) {
                min = i;
            }
        }
        int ret = procList.get(min).getAllocatedFrames() - m;
        procList.get(min).setAllocatedFrames(m);
        return ret;
    }
}
