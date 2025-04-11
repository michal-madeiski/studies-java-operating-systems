import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("TEST: size=" + 160 + ", start=" + 63 + ", requests=" + 8 + ", RTrequests=" + 2);
        List<SimpleRequest> simpleTestList = new ArrayList<>();
        int[] simpleTestTab = {23, 67, 55, 14, 31, 7, 84, 10};
        for (int i = 0; i < simpleTestTab.length; i++) {
            simpleTestList.add(new SimpleRequest(i + 1, simpleTestTab[i]));
        }

        SimpleAlgorithms testSimpAlgo1 = new SimpleAlgorithms(simpleTestList, 63, 1, 160);
        System.out.print("SIMPLE ALGO: "); printList(simpleTestList); System.out.println();
        System.out.println("FCFS: " + testSimpAlgo1.FIFO());
        System.out.println("SSTF: " + testSimpAlgo1.SSTF());
        System.out.println("SCAN: " + testSimpAlgo1.SCAN());
        System.out.println("C-SCAN: " + testSimpAlgo1.C_SCAN());
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");


        List<PriorityRequest> realTimeTestList = new ArrayList<>();
        int[] realTimeTestTab1 = {23, 67, 55, 14, 31, 7, 84, 10};
        int[] realTimeTestTab2 = {102, 41};
        for (int i = 0; i < realTimeTestTab1.length; i++) {
            realTimeTestList.add(new PriorityRequest(i + 1, realTimeTestTab1[i], 0));
        }
        for (int i = 0; i < realTimeTestTab2.length; i++) {
            realTimeTestList.add(new PriorityRequest(realTimeTestTab1.length + i + 1, realTimeTestTab2[i], i + 1));
        }

        RealTimeAlgorithms testRealAlgo1 = new RealTimeAlgorithms(realTimeTestList, 63, 1, 160);
        System.out.print("REAL TIME ALGO: "); printList2(realTimeTestList); System.out.println();
        System.out.println("EDF_FCFS: " + testRealAlgo1.EDF(1));
        System.out.println("EDF_SSTF: " + testRealAlgo1.EDF(2));
//        System.out.println("EDF_SCAN: " + testRealAlgo1.EDF(3));
//        System.out.println("EDF_C-SCAN: " + testRealAlgo1.EDF(4));
//        System.out.println("FD-SCAN_FIFO: " + testRealAlgo1.FD_SCAN(1));
//        System.out.println("FD-SCAN_SSTF: " + testRealAlgo1.FD_SCAN(2));
        System.out.println("FD-SCAN_SCAN: " + testRealAlgo1.FD_SCAN(3));
        System.out.println("FD-SCAN_C-SCAN: " + testRealAlgo1.FD_SCAN(4));
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        int size1 = 200;
        int requests1 = 20; int RTrequests1 = requests1/10 + 1;
        ArrayList<SimpleRequest> simpleList1 = simpleRequestListGenerator(size1, requests1);
        ArrayList<PriorityRequest> prioList1 = priorityRequestListGenerator(size1, simpleList1);
        int start1 = 100;
        SimpleAlgorithms simpleAlgo1 = new SimpleAlgorithms(simpleList1, start1, 1, size1);
        RealTimeAlgorithms realTimeAlgo1 = new RealTimeAlgorithms(prioList1, start1, 1, size1);
        System.out.println("SIMULATION 1: size=" + size1 + ", start=" + start1 + ", requests=" + requests1 + ", RTrequests=" + RTrequests1);
        System.out.print("SIMPLE ALGO: "); printList(simpleList1); System.out.println();
        System.out.println("FCFS: " + simpleAlgo1.FIFO());
        System.out.println("SSTF: " + simpleAlgo1.SSTF());
        System.out.println("SCAN: " + simpleAlgo1.SCAN());
        System.out.println("C-SCAN: " + simpleAlgo1.C_SCAN());
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("REAL TIME ALGO: "); printList2(prioList1); System.out.println();
        System.out.println("EDF_FCFS: " + realTimeAlgo1.EDF(1));
        System.out.println("EDF_SSTF: " + realTimeAlgo1.EDF(2));
//        System.out.println("EDF_SCAN: " + realTimeAlgo1.EDF(3));
//        System.out.println("EDF_C-SCAN: " + realTimeAlgo1.EDF(4));
//        System.out.println("FD-SCAN_FIFO: " + realTimeAlgo1.FD_SCAN(1));
//        System.out.println("FD-SCAN_SSTF: " + realTimeAlgo1.FD_SCAN(2));
        System.out.println("FD-SCAN_SCAN: " + realTimeAlgo1.FD_SCAN(3));
        System.out.println("FD-SCAN_C-SCAN: " + realTimeAlgo1.FD_SCAN(4));
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        int size2 = 500;
        int requests2 = 100; int RTrequests2 = requests2 /10 + 1;
        ArrayList<SimpleRequest> simpleList2 = simpleRequestListGenerator(size2, requests2);
        ArrayList<PriorityRequest> prioList2 = priorityRequestListGenerator(size2, simpleList2);
        int start2 = 250;
        SimpleAlgorithms simpleAlgo2 = new SimpleAlgorithms(simpleList2, start2, 1, size2);
        RealTimeAlgorithms realTimeAlgo2 = new RealTimeAlgorithms(prioList2, start2, 1, size2);
        System.out.println("SIMULATION 1: size=" + size2 + ", start=" + start2 + ", requests=" + requests2 + ", RTrequests=" + RTrequests2);
        System.out.print("SIMPLE ALGO: "); printList(simpleList2); System.out.println();
        System.out.println("FCFS: " + simpleAlgo2.FIFO());
        System.out.println("SSTF: " + simpleAlgo2.SSTF());
        System.out.println("SCAN: " + simpleAlgo2.SCAN());
        System.out.println("C-SCAN: " + simpleAlgo2.C_SCAN());
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("REAL TIME ALGO: "); printList2(prioList2); System.out.println();
        System.out.println("EDF_FCFS: " + realTimeAlgo2.EDF(1));
        System.out.println("EDF_SSTF: " + realTimeAlgo2.EDF(2));
//        System.out.println("EDF_SCAN: " + realTimeAlgo2.EDF(3));
//        System.out.println("EDF_C-SCAN: " + realTimeAlgo2.EDF(4));
//        System.out.println("FD-SCAN_FIFO: " + realTimeAlgo2.FD_SCAN(1));
//        System.out.println("FD-SCAN_SSTF: " + realTimeAlgo2.FD_SCAN(2));
        System.out.println("FD-SCAN_SCAN: " + realTimeAlgo2.FD_SCAN(3));
        System.out.println("FD-SCAN_C-SCAN: " + realTimeAlgo2.FD_SCAN(4));
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void printList(List<SimpleRequest> lista) {
        for (SimpleRequest x : lista) {
            System.out.print(x + "; ");
        }
    }
    public static void printList2(List<PriorityRequest> lista) {
        for (SimpleRequest x : lista) {
            System.out.print(x + "; ");
        }
    }
    public static ArrayList<SimpleRequest> simpleRequestListGenerator(int size, int requests) {
        ArrayList<SimpleRequest> simpleRequestList = new ArrayList<>();
        ArrayList<Integer> tempList = new ArrayList<>();
        Random r = new Random();
        int tempCounter = 0;
        while (tempCounter < requests) {
            int x = r.nextInt(1, size + 1);
            if (!tempList.contains(x)) {
                tempList.add(x);
                tempCounter++;
            }
        }

        int id = 1;
        for (Integer i : tempList) {
            simpleRequestList.add(new SimpleRequest(id, i));
            id++;
        }

        return simpleRequestList;
    }
    public static ArrayList<PriorityRequest> priorityRequestListGenerator(int size, ArrayList<SimpleRequest> simpleRequestList) {
        ArrayList<PriorityRequest> priorityRequestList = new ArrayList<>();
        ArrayList<Integer> tempList2 = new ArrayList<>();
        for (SimpleRequest simpleRequest : simpleRequestList) {
            priorityRequestList.add(new PriorityRequest(simpleRequest.getId(), simpleRequest.getRequestNumber(), 0));
            tempList2.add(simpleRequest.getRequestNumber());
        }

        int howManyPrio = (simpleRequestList.size())/10 + 1;
        ArrayList<Integer> tempList = new ArrayList<>();
        Random r = new Random();
        int tempCounter = 0;
        while (tempCounter < howManyPrio) {
            int x = r.nextInt(1, size + 1);
            if (!tempList.contains(x) && !tempList2.contains(x)) {
                tempList.add(x);
                tempCounter++;
            }
        }

        int id = simpleRequestList.size() + 1;
        int prio = 1;
        for (Integer i : tempList) {
            priorityRequestList.add(new PriorityRequest(id, i, prio));
            id++;
            prio++;
        }
        return priorityRequestList;
    }
}