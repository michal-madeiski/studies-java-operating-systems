public class Test {
    public static void main(String[] args) {
        //każde zadanie przychodzi w jednej jednostce czasu (1ms), czyli np.: dla 1000 zadań, syumlacja trwa jakby 1000ms
        //im dłuższe zadania, tym bardziej zostaną obciążone procesory
        //jeśli w algo1 migrations = amountOfTasks tzn. że nie było sytuacji, w której obciążenie procesu było >= p

        System.out.println("----------------------------------------------------------------------------------------");
        Algorithms sim1 = new Algorithms(60, 40, 20, 30, 100000, 10, 1000,10000, 5);
        sim1.runSim();
    }
}