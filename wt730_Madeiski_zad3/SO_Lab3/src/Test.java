import java.util.ArrayList;
import java.util.Random;

public class Test {
    public static ArrayList<Process> generateProcesses(int vrMemSize, int maxCapOfProcess) {
        ArrayList<Process> list = new ArrayList<>();
        int pageMin = 1;
        int vrMemorySize = vrMemSize;
        int id = 1;
        while (vrMemorySize > maxCapOfProcess) {
            Random r = new Random();
            int processSize = r.nextInt(1, maxCapOfProcess + 1);
            int refSize;
            if (processSize > 4) {
                int div = r.nextInt(2, 5);
                refSize = processSize / div;
            } else {
                refSize = 1;
            }
            int pageMax = pageMin + (processSize - 1);
            list.add(new Process(id, pageMin, pageMax, refSize));
            id++;
            vrMemorySize -= processSize;
            pageMin = pageMax + 1;
        }
        Random r = new Random();
        int processSize = vrMemorySize;
        int div = r.nextInt(2, 4);
        int refSize = processSize / div;
        list.add(new Process(id, pageMin, vrMemSize, refSize));

        return list;
    }
    public static ArrayList<Page> refSim(ArrayList<Process> processesList, int simLength) {
        ArrayList<Page> pages = new ArrayList<>();
        int sizeProc = processesList.size();
        for (int i = 0; i < simLength; i++) {
            Random r = new Random();
            int idx = r.nextInt(sizeProc);
            Process process = processesList.get(idx);
            for (Page page : process.generate()) {
                pages.add(page);
            }
        }
        return pages;
    }
    public static void runSim(int n, int vrMemSize, int maxCapOfProcess, int frameSize, int simLength, int reps) {
        System.out.println("SIMULATION " + n +  ": vrMemorySize = " + vrMemSize +  ", maxCapOfProcess = " + maxCapOfProcess + ", frameSize = " + frameSize + ", simLength = " + simLength);
        double fifo = 0; double rand = 0; double lru = 0; double opt = 0; double lruaprx = 0;

        for (int i = 0; i < reps; i++) {
            ArrayList<Process> procesy = generateProcesses(vrMemSize, maxCapOfProcess);
            ArrayList<Page> pages = refSim(procesy, simLength);
            Algorithms algo = new Algorithms(pages, vrMemSize, frameSize);
            fifo += algo.FIFO();
            rand += algo.RAND();
            lru += algo.LRU();
            opt += algo.OPT();
            lruaprx += algo.LRUaprx();
        }

        System.out.println("FIFO: " + fifo/reps);
        System.out.println("RAND: " + rand/reps);
        System.out.println("LRU: " + lru/reps);
        System.out.println("OPT: " + opt/reps);
        System.out.println("LRUaprx: " + lruaprx/reps);
        System.out.println("----------------------------------------------------------------------------------------------");

    }

    public static void main(String[] args) {
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("TEST: testReferencesNums -> 1, 2, 3, 4, 1, 2, 5, 1, 2");
        ArrayList<Page> testPages = new ArrayList<>();
        ArrayList<Page> temp = new ArrayList<>();
        Page page1 = new Page(1);Page page2 = new Page(2);Page page3 = new Page(3);Page page4 = new Page(4); Page page5 = new Page(5);
        temp.add(page1);temp.add(page2);temp.add(page3);temp.add(page4);temp.add(page5);

        int[] testTab = {1, 2, 3, 4, 1, 2, 5, 1, 2};
        for (int i = 0; i < testTab.length; i++) {
            testPages.add(temp.get(testTab[i] - 1));
        }

        Algorithms testAlgo = new Algorithms(testPages, 5, 3);
        System.out.println("FIFO: " + testAlgo.FIFO());
        System.out.println("RAND: " + testAlgo.RAND());
        System.out.println("LRU: " + testAlgo.LRU());
        System.out.println("OPT: " + testAlgo.OPT());
        System.out.println("LRUaprx: " + testAlgo.LRUaprx());
        System.out.println("----------------------------------------------------------------------------------------------");

        runSim(1,500,10,5,3000,10);
        runSim(2,1000,15,100,2000,10);
        runSim(3,5000,30,10,5000,10);
    }
}