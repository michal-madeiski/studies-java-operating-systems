import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        ArrayList<Proces> procesy = new ArrayList<>();
        ArrayList<Proces> procesyTEST = new ArrayList<>();
        ArrayList<Proces> procesyTEST2 = new ArrayList<>();

        procesyTEST.add(new Proces(0, 5, 0, 0, 0));
        procesyTEST.add(new Proces(1, 4, 1, 0, 0));
        procesyTEST.add(new Proces(2, 2, 2, 0, 0));

        procesyTEST2.add(new Proces(4, 2, 5, 0, 0));
        procesyTEST2.add(new Proces(3, 1, 4, 0, 0));
        procesyTEST2.add(new Proces(2, 5, 2, 0, 0));
        procesyTEST2.add(new Proces(1, 8, 0, 0, 0));

        for (int i = 0; i < 50; i++) {
            Random r1 = new Random();
            int x1 = r1.nextInt(0, 50);
            int x2 = r1.nextInt(0, 50);
            procesy.add(new Proces(i, x1, x2, 0,0));
        }



        System.out.println("DANE TESTOWE: ");
        for (Proces proces : procesyTEST) {
            System.out.println(proces);
        }

        System.out.println();

        System.out.println("WYNIKI DLA DANYCH TESTOWYCH: ");
        Algorytmy alg1 = new Algorytmy(procesyTEST);
        System.out.println("FCFS: " + alg1.FCFS());
        System.out.println("SJF: " + alg1.SJF());
        System.out.println("SRTF: " + alg1.SRTF());
        System.out.println("RR: " + alg1.RR(1));

        System.out.println("--------------------------------------------------------------");
        System.out.println("WYNIKI DLA 50 LOSOWYCH PROCESÓW: ");
        Algorytmy alg2 = new Algorytmy(procesy);
        System.out.println("FCFS: " + alg2.FCFS());
        System.out.println("SJF: " + alg2.SJF());
        System.out.println("SRTF: " + alg2.SRTF());
        System.out.println("RR: " + alg2.RR(2));

        System.out.println("--------------------------------------------------------------");
        System.out.println("WYNIKI ŚREDNIE DLA 10 ZESTAWÓW 50 LOSOWYCH PROCESÓW: ");
        double fcfs1 = 0;
        double sjf1 = 0;
        double srtf1 = 0;
        double rr1 = 0;


        ArrayList<Proces> procesyPom1 = new ArrayList<>();

        for (int j = 0; j < 10; j++) {
            procesyPom1.clear();
            Random r = new Random();
            //int i0 = r.nextInt(20,30);
            for (int i = 0; i < 50; i++) {
                Random r1 = new Random();
                int x1 = r1.nextInt(0, 50);
                int x2 = r1.nextInt(0, 50);
                procesyPom1.add(new Proces(i, x1, x2, 0,0));
            }

            Algorytmy alg3 = new Algorytmy(procesyPom1);
            fcfs1 += alg3.FCFS();
            sjf1 += alg3.SJF();
            srtf1 += alg3.SRTF();
            rr1 += alg3.RR(40);
        }
        System.out.println("FCFS: " + fcfs1/10);
        System.out.println("SJF: " + sjf1/10);
        System.out.println("SRTF: " + srtf1/10);
        System.out.println("RR: " + rr1/10);

        System.out.println("--------------------------------------------------------------");
        System.out.println("WYNIKI ŚREDNIE DLA 10 ZESTAWÓW 20-30 LOSOWYCH PROCESÓW: ");
        double fcfs = 0;
        double sjf = 0;
        double srtf = 0;
        double rr = 0;


        ArrayList<Proces> procesyPom = new ArrayList<>();

        for (int j = 0; j < 10; j++) {
            procesyPom.clear();
            Random r = new Random();
            int i0 = r.nextInt(20,30);
            for (int i = 0; i < i0; i++) {
                Random r1 = new Random();
                int x1 = r1.nextInt(0, 50);
                int x2 = r1.nextInt(0, 50);
                procesyPom.add(new Proces(i, x1, x2, 0,0));
            }

            Algorytmy alg3 = new Algorytmy(procesyPom);
            fcfs += alg3.FCFS();
            sjf += alg3.SJF();
            srtf += alg3.SRTF();
            rr += alg3.RR(50);
        }


        System.out.println("FCFS: " + fcfs/10);
        System.out.println("SJF: " + sjf/10);
        System.out.println("SRTF: " + srtf/10);
        System.out.println("RR: " + rr/10);


    }
}