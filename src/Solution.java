import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    List<Library> libraries = new ArrayList<>();

    private final Problem problem;
    private long score = -1;

    public Solution(Problem problem) {
        this.problem = problem;
    }

    public Solution(Problem problem, List<Library> libraries) {
        this.problem = problem;
        this.libraries = libraries;
    }

    public Solution clone() {
        List<Library> libraries = new ArrayList<>();
        for (Library library : this.libraries) {
            libraries.add(library.cloneSolution());
        }
        return new Solution(problem, libraries);
    }

    public long calcScore() {
        int day = 0;
        Set<Book> booksScanned = new HashSet<>();
        for (Library library : libraries) {
            day += library.signupTime;
            int canScan = (problem.numDays - day) * library.booksPerDay;
            for (int i = 0; i < Math.min(library.booksOrder.size(), canScan); i ++) {
                booksScanned.add(library.booksOrder.get(i));
            }
        }
        long score = 0;
        for (Book book : booksScanned) {
            score += book.value;
        }
        return score;
    }

    public long getScore() {
        if (score == -1) {
            score = calcScore();
        }
        return score;
    }
}
