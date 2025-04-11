import java.util.ArrayList;
import java.util.Random;

public class Algorithms {
    private ArrayList<Task> tasks;
    private ArrayList<Processor> processors;
    int N; //liczba identycznych procesorów w systemie
    int p; //górny próg obciążenia procesora
    int r; //dolny próg obciążenia procesora
    int z; //maksymalna ilość zapytań w algo1
    int part; //część przejmowanych procesów w algo3

    public Algorithms(int N, int p, int r, int z, int amountOfTasks, int maxLoadOfTask, int minLengthOfTask, int maxLengthOfTask, int part) {
        this.processors = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.N = N;
        this.p = p;
        this.r = r;
        this.z = z;
        this.part = part;

        for (int i = 0; i < N; i++) {
            processors.add(new Processor());
        }
        for (int i = 0; i < amountOfTasks; i++) {
            tasks.add(new Task(maxLoadOfTask, minLengthOfTask, maxLengthOfTask));
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public ArrayList<Processor> getProcessors() {
        return processors;
    }
    public void setProcessors(ArrayList<Processor> processors) {
        this.processors = processors;
    }
    public int getN() {
        return N;
    }
    public void setN(int n) {
        N = n;
    }
    public int getP() {
        return p;
    }
    public void setP(int p) {
        this.p = p;
    }
    public int getR() {
        return r;
    }
    public void setR(int r) {
        this.r = r;
    }
    public int getZ() {
        return z;
    }
    public void setZ(int z) {
        this.z = z;
    }

    public void algo1(ArrayList<Task> tasks, ArrayList<Processor> processors) {
        int migrations = 0, requests = 0;
        ArrayList<Integer> averagesList = new ArrayList<>();
        ArrayList<Integer> sDVList = new ArrayList<>();

         while (!tasks.isEmpty()) {
             Random rand = new Random();
             int procIdx = rand.nextInt(N);
             Processor actProcessor = processors.get(procIdx);
             Task actTask = tasks.getFirst();
             boolean foundOtherProcessor = false;
             int timeSpendForFinding = 0;

             for (int i = 0; (!foundOtherProcessor && i < z); i++) {
                 int otherProcIdx = rand.nextInt(N);
                 while (otherProcIdx == procIdx) {
                     otherProcIdx = rand.nextInt(N);
                 }
                 Processor otherProcessor = processors.get(otherProcIdx);
                 if (otherProcessor.getCurrentLoad() <= p) {
                     if (otherProcessor.getCurrentLoad() + actTask.getPartOfProcessor() > 100) {
                         actTask.decreaseToFinishTask(100 - otherProcessor.getCurrentLoad());
                     }
                     otherProcessor.getTasks().add(actTask);
                     foundOtherProcessor = true;
                     tasks.removeFirst();
                     otherProcessor.setCurrentLoad(otherProcessor.getCurrentLoad() + actTask.getPartOfProcessor());
                     migrations++;
                 }
                 requests++;
                 timeSpendForFinding++;
             }

             if (!foundOtherProcessor) {
                 if (actProcessor.getCurrentLoad() < 100) {
                     if (actProcessor.getCurrentLoad() + actTask.getPartOfProcessor() > 100) {
                         actTask.decreaseToFinishTask(100 - actProcessor.getCurrentLoad());
                     }
                     actProcessor.getTasks().add(actTask);
                     actProcessor.setCurrentLoad(actProcessor.getCurrentLoad() + actTask.getPartOfProcessor());
                     tasks.removeFirst();
                 }
             }

             for (int i = 0; i < N; i++) {
                 Processor proc = processors.get(i);
                 if (!proc.getTasks().isEmpty()) {
                     for (Task task : proc.getTasks()) {
                         task.setTimeToBeDone(task.getTimeToBeDone() - timeSpendForFinding);
                         task.doTask();
                         if (task.isDone()) {
                             proc.setCurrentLoad(proc.getCurrentLoad() - task.getPartOfProcessor());
                         }
                     }
                     proc.getTasks().removeIf(task -> task.getTimeToBeDone() <= 0);
                 }
             }

             for (Processor processor : processors) {
                 averagesList.add(processor.getCurrentLoad());
                 sDVList.add(processor.getCurrentLoad());
             }
         }

         double average = arithmeticAverage(averagesList);
         double sDV = standardDeviation(sDVList);

        System.out.println("STRATEGY 1: ");
        System.out.println("Average load with standard deviation: " + average + "% +/- " + sDV + "%");
        System.out.println("Requests: " + requests);
        System.out.println("Migrations: " + migrations);
        System.out.println("----------------------------------------------------------------------------------------");
    }
    public void algo2(ArrayList<Task> tasks, ArrayList<Processor> processors) {
        int migrations = 0, requests = 0;
        ArrayList<Integer> averagesList = new ArrayList<>();
        ArrayList<Integer> sDVList = new ArrayList<>();

        while (!tasks.isEmpty()) {
            Random rand = new Random();
            int procIdx = rand.nextInt(N);
            Processor actProcessor = processors.get(procIdx);
            Task actTask = tasks.getFirst();
            int timeSpendForFinding = 0;

            if (actProcessor.getCurrentLoad() <= p) {
                    if (actProcessor.getCurrentLoad() + actTask.getPartOfProcessor() > 100) {
                        actTask.decreaseToFinishTask(100 - actProcessor.getCurrentLoad());
                    }
                    actProcessor.getTasks().add(actTask);
                    actProcessor.setCurrentLoad(actProcessor.getCurrentLoad() + actTask.getPartOfProcessor());
                    tasks.removeFirst();
            } else {
                int otherProcIdx = rand.nextInt(N);
                Processor otherProcessor = processors.get(otherProcIdx);
                requests++;
                int counter = 1;
                while (otherProcessor.getCurrentLoad() >= p && counter < N) {
                    otherProcIdx = rand.nextInt(N);
                    if (otherProcIdx != procIdx) {
                        otherProcessor = processors.get(otherProcIdx);
                        requests++;
                        counter++;
                    }
                    timeSpendForFinding++;
                }
                migrations++;
                if (otherProcessor.getCurrentLoad() + actTask.getPartOfProcessor() > 100) {
                    actTask.decreaseToFinishTask(100 - otherProcessor.getCurrentLoad());
                }
                otherProcessor.getTasks().add(actTask);
                tasks.removeFirst();
                otherProcessor.setCurrentLoad(otherProcessor.getCurrentLoad() + actTask.getPartOfProcessor());
            }

            for (int i = 0; i < N; i++) {
                Processor proc = processors.get(i);
                if (!proc.getTasks().isEmpty()) {
                    for (Task task : proc.getTasks()) {
                        task.setTimeToBeDone(task.getTimeToBeDone() - timeSpendForFinding);
                        task.doTask();
                        if (task.isDone()) {
                            proc.setCurrentLoad(proc.getCurrentLoad() - task.getPartOfProcessor());
                        }
                    }
                    proc.getTasks().removeIf(task -> task.getTimeToBeDone() <= 0);
                }
            }

            for (Processor processor : processors) {
                averagesList.add(processor.getCurrentLoad());
                sDVList.add(processor.getCurrentLoad());
            }
        }

        double average = arithmeticAverage(averagesList);
        double sDV = standardDeviation(sDVList);

        System.out.println("STRATEGY 2: ");
        System.out.println("Average load with standard deviation: " + average + "% +/- " + sDV + "%");
        System.out.println("Requests: " + requests);
        System.out.println("Migrations: " + migrations);
        System.out.println("----------------------------------------------------------------------------------------");
    }
    public void algo3(ArrayList<Task> tasks, ArrayList<Processor> processors) {
        int migrations = 0, requests = 0;
        ArrayList<Integer> averagesList = new ArrayList<>();
        ArrayList<Integer> sDVList = new ArrayList<>();

        while (!tasks.isEmpty()) {
            Random rand = new Random();
            int procIdx = rand.nextInt(N);
            Processor actProcessor = processors.get(procIdx);
            Task actTask = tasks.getFirst();
            int timeSpendForFinding = 0;

            if (actProcessor.getCurrentLoad() <= p) {
                if (actProcessor.getCurrentLoad() + actTask.getPartOfProcessor() > 100) {
                    actTask.decreaseToFinishTask(100 - actProcessor.getCurrentLoad());
                }
                actProcessor.getTasks().add(actTask);
                actProcessor.setCurrentLoad(actProcessor.getCurrentLoad() + actTask.getPartOfProcessor());
                tasks.removeFirst();
            } else {
                int otherProcIdx = rand.nextInt(N);
                Processor otherProcessor = processors.get(otherProcIdx);
                requests++;
                int counter = 1;
                while (otherProcessor.getCurrentLoad() >= p && counter < N) {
                    otherProcIdx = rand.nextInt(N);
                    if (otherProcIdx != procIdx) {
                        otherProcessor = processors.get(otherProcIdx);
                        requests++;
                        counter++;
                    }
                    timeSpendForFinding++;
                }
                migrations++;
                if (otherProcessor.getCurrentLoad() + actTask.getPartOfProcessor() > 100) {
                    actTask.decreaseToFinishTask(100 - otherProcessor.getCurrentLoad());
                }
                otherProcessor.getTasks().add(actTask);
                tasks.removeFirst();
                otherProcessor.setCurrentLoad(otherProcessor.getCurrentLoad() + actTask.getPartOfProcessor());
            }

            boolean foundOtherProcessor = false;
            if (actProcessor.getCurrentLoad() < r) {
                for (int i = 0; (!foundOtherProcessor && i < N); i++) {
                    int otherProcIdx = rand.nextInt(N);
                    Processor otherProcessor = processors.get(otherProcIdx);
                    if (otherProcessor.getCurrentLoad() > p) {
                        foundOtherProcessor = true;
                        int movedLoad = 0;
                        while (movedLoad < part) {
                            int amountOfOtherProcessorTasks = otherProcessor.getTasks().size();
                            int taskIdx = rand.nextInt(amountOfOtherProcessorTasks);
                            Task taskToMove = otherProcessor.getTasks().get(taskIdx);
                            otherProcessor.getTasks().remove(taskIdx);
                            otherProcessor.setCurrentLoad(otherProcessor.getCurrentLoad() - taskToMove.getPartOfProcessor());
                            movedLoad += taskToMove.getPartOfProcessor();
                            actProcessor.getTasks().add(taskToMove);
                            actProcessor.setCurrentLoad(actProcessor.getCurrentLoad() + taskToMove.getPartOfProcessor());
                            migrations++;
                        }
                    }
                }
            }

            for (int i = 0; i < N; i++) {
                Processor proc = processors.get(i);
                if (!proc.getTasks().isEmpty()) {
                    for (Task task : proc.getTasks()) {
                        task.setTimeToBeDone(task.getTimeToBeDone() - timeSpendForFinding);
                        task.doTask();
                        if (task.isDone()) {
                            proc.setCurrentLoad(proc.getCurrentLoad() - task.getPartOfProcessor());
                        }
                    }
                    proc.getTasks().removeIf(task -> task.getTimeToBeDone() <= 0);
                }
            }

            for (Processor processor : processors) {
                averagesList.add(processor.getCurrentLoad());
                sDVList.add(processor.getCurrentLoad());
            }
        }

        double average = arithmeticAverage(averagesList);
        double sDV = standardDeviation(sDVList);

        System.out.println("STRATEGY 3: ");
        System.out.println("Average load with standard deviation: " + average + "% +/- " + sDV + "%");
        System.out.println("Requests: " + requests);
        System.out.println("Migrations: " + migrations);
        System.out.println("----------------------------------------------------------------------------------------");
    }

    private double standardDeviation(ArrayList<Integer> list) {
        double sdSum = 0;
        int amount = list.size();
        double avrg = arithmeticAverage(list);
        for (Integer i : list) {
            sdSum += Math.pow(i - avrg , 2);
        }
        return Math.sqrt(sdSum / amount);
    }
    private double arithmeticAverage(ArrayList<Integer> list) {
        int amount = list.size();
        if (amount == 0) {
            return 0;
        }
        int sum = 0;
        for (Integer i : list) {
            sum += i;
        }
        return sum / amount;
    }

    public void runSim() {
        ArrayList<Task> tasks1 = new ArrayList<>();
        ArrayList<Task> tasks2 = new ArrayList<>();
        ArrayList<Task> tasks3 = new ArrayList<>();

        ArrayList<Processor> processors1 = new ArrayList<>();
        ArrayList<Processor> processors2 = new ArrayList<>();
        ArrayList<Processor> processors3 = new ArrayList<>();

        for (Task task : tasks) {
            tasks1.add(new Task(task));
            tasks2.add(new Task(task));
            tasks3.add(new Task(task));
        }

        for (Processor processor : processors) {
            processors1.add(new Processor(processor));
            processors2.add(new Processor(processor));
            processors3.add(new Processor(processor));
        }

        algo1(tasks1, processors1);
        algo2(tasks2, processors2);
        algo3(tasks3, processors3);
    }
}
