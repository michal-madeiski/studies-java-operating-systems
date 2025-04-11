import java.util.Comparator;

public class SimpleRequestComparator implements Comparator<SimpleRequest> {
    @Override
    public int compare(SimpleRequest r1, SimpleRequest r2) {
        return r1.getRequestNumber() - r2.getRequestNumber();
    }
}
