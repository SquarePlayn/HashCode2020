import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    static final String NL = "\n";
    static final String SEP = " ";

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
        Set<Library> librariesSeen = new HashSet<>();
        for (Library library : libraries) {
            if (librariesSeen.contains(library)) {
                throw new IllegalStateException("Library exists multiple times in solution.");
            }
            librariesSeen.add(library);

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

    public void toFile() {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("solution/"+problem.name));
            writer.write(libraries.size() + NL);
            for (Library library : libraries) {
                writer.write(library.id + SEP + library.booksOrder.size() + NL);
                for (Book book : library.booksOrder) {
                    writer.write(book.id + SEP);
                }
                writer.write(NL);
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
