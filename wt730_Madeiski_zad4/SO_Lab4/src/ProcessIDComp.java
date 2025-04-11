import java.util.Comparator;

public class ProcessIDComp implements Comparator<Process> {
    @Override
    public int compare(Process p1, Process p2) {
        return p1.getId() - p2.getId();
    }
}
