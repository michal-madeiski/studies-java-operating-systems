import java.util.Comparator;

public class ComparatoryProcesow {
    public static Comparator<Proces> ComparatorMomentZgloszenia = new Comparator<Proces>() {
        @Override
        public int compare(Proces o1, Proces o2) {
            return o1.getMomentZgloszenia() - o2.getMomentZgloszenia();
        }
    };

    public static Comparator<Proces> ComparatorDlugoscFazy = new Comparator<Proces>() {
        @Override
        public int compare(Proces o1, Proces o2) {
            return o1.getDlugoscFazy() - o2.getDlugoscFazy();
        }
    };

    public static Comparator<Proces> ComparatorNrProcesu = new Comparator<Proces>() {
        @Override
        public int compare(Proces o1, Proces o2) {
            return o1.getNrProcesu() - o2.getNrProcesu();
        }
    };

    public static Comparator<Proces> ComparatorNrProcesuMomentZgloszenia = new Comparator<Proces>() {
        @Override
        public int compare(Proces o1, Proces o2) {
            int roznica1 = o1.getNrProcesu() - o2.getNrProcesu();
            if (roznica1 != 0) return o1.getNrProcesu() - o2.getNrProcesu();
            else return o1.getMomentZgloszenia() - o2.getMomentZgloszenia();
        }
    };

    public static Comparator<Proces> ComparatorCzasPozostaly = new Comparator<Proces>() {
        @Override
        public int compare(Proces o1, Proces o2) {
            return o1.getCzasPozostaly() - o2.getCzasPozostaly();
        }
    };

}
