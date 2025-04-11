import java.util.Comparator;

public class PriorityRequestComparator implements Comparator<PriorityRequest> {
    @Override
    public int compare(PriorityRequest r1, PriorityRequest r2) {
        return r2.getPriority() - r1.getPriority();
    }
}
