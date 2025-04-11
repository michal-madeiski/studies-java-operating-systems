import java.util.ArrayList;
import java.util.Random;

public class Test {
    public static ArrayList<Process> generateProcesses (int vrMemSize, int minCapOfProcess, int maxCapOfProcess, int amountOfProcesses){
        ArrayList<Process> listOfAllProcesses = new ArrayList<>();
        int pageMin = 1;
        int vrMemorySize = vrMemSize;
        int id = 1;
        while (vrMemorySize > maxCapOfProcess) {
            Random r = new Random();
            int processSize = r.nextInt(minCapOfProcess, maxCapOfProcess + 1);
            int refSize;
            if (processSize > 4) {
                int div = r.nextInt(2, 5);
                refSize = processSize / div;
            } else {
                refSize = 1;
            }
            int pageMax = pageMin + (processSize - 1);
            listOfAllProcesses.add(new Process(id, pageMin, pageMax, refSize));
            id++;
            vrMemorySize -= processSize;
            pageMin = pageMax + 1;
        }
        Random r = new Random();
        int processSize = vrMemorySize;
        int div = r.nextInt(2, 4);
        int refSize = processSize / div;
        listOfAllProcesses.add(new Process(id, pageMin, vrMemSize, refSize));

        ArrayList<Integer> chosenIndexes = new ArrayList<>();
        ArrayList<Process> chosenProcesses = new ArrayList<>();
        int s = listOfAllProcesses.size();
        while (chosenIndexes.size() < amountOfProcesses) {
            int chosenIndex = r.nextInt(0, s);
            if (!chosenIndexes.contains(chosenIndex)) {
                chosenIndexes.add(chosenIndex);
                chosenProcesses.add(listOfAllProcesses.get(chosenIndex));
            }
        }
        return chosenProcesses;
    }
    public static ArrayList<Integer[]> referenceSim(ArrayList<Process> processesList, int simLength) {
        ArrayList<Integer[]> pages = new ArrayList<>();
        int sizeProc = processesList.size();
        for (int i = 0; i < simLength; i++) {
            Random r = new Random();
            int idx = r.nextInt(sizeProc);
            Process process = processesList.get(idx);
            Integer[] tab = new Integer[process.getRefSize() + 3];
            tab[0] = process.getId();
            tab[1] = process.getPageMinNum();
            tab[2] = process.getPageMaxNum();
            idx++;
            ArrayList<Integer> tempProc = process.generate();
            for (int j = 3; j < tab.length ; j++) {
                tab[j] = tempProc.get(j - 3);
            }
            pages.add(tab);
        }
        return pages;
    }
    public static void printReferences(ArrayList<Integer[]> pages) {
        for (Integer[] ref : pages) {
            System.out.print("[");
            for (Integer i : ref) {
                System.out.print(i + ", ");
            }
            System.out.print("]");

        }
        System.out.println();
    }
    public static void runSim(int n, int vrMemSize, int minCapOfProcess, int maxCapOfProcess, int amountOfProcesses, int frameSize, int simLength, int t, int min, int max, int reps) {
        System.out.println("SIMULATION " + n +  ": vrMemorySize = " + vrMemSize +  ", minCapOfProcess = " + minCapOfProcess + ", maxCapOfProcess = " + maxCapOfProcess + ", amountOfProcess = " + amountOfProcesses +", frameSize = " + frameSize + ", t = " + t + ", simLength = " + simLength);
        double eq = 0; double prop = 0; double zm = 0; double pff = 0;
        for (int i = 0; i < reps; i++) {
            ArrayList<Process> processesList = generateProcesses(vrMemSize, minCapOfProcess, maxCapOfProcess, amountOfProcesses);
            ArrayList<Integer[]> referenceSim = referenceSim(processesList, simLength);
            Algorithms algo = new Algorithms(processesList, referenceSim, vrMemSize, frameSize);
            eq += algo.EqualAllocation(1);
            prop += algo.ProportionalAllocation(1);
            zm += algo.ZoneModel(1, t);
            pff += algo.PFF(1, t, min, max);
        }
        System.out.println("equal: " + eq/reps);
        System.out.println("proportional: " + prop/reps);
        System.out.println("zone model: " + zm/reps);
        System.out.println("pff: " + pff/reps);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("TEST:");
        int vrMemSize = 200; int simLength = 100; int frameSize = 40;
        int minCapOfProcess = 5; int maxCapOfProcess = 20; int amountOfProcesses = 5;
        int t = 20; int min = 2; int max = 9;

        ArrayList<Process> processesList = generateProcesses(vrMemSize, minCapOfProcess, maxCapOfProcess, amountOfProcesses);
        System.out.println(processesList);
        ArrayList<Integer[]> referenceSim = referenceSim(processesList, simLength);
        //printReferences(referenceSim);
        Algorithms algo1 = new Algorithms(processesList, referenceSim, vrMemSize, frameSize);

        System.out.println("equal: "); System.out.println(algo1.EqualAllocation(0));
        System.out.println("proportional: "); System.out.println(algo1.ProportionalAllocation(0));
        System.out.println("zone model: "); System.out.println(algo1.ZoneModel(0, t));
        System.out.println("pff: "); System.out.println(algo1.PFF(0, t, min, max));
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");

        runSim(1, 1000, 5,50,10,100,100,20,2,8,10);
        runSim(2, 1000, 50,100,5,200, 500, 50, 2, 8, 10);
    }
}