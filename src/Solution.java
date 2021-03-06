import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Solution {

    static final String NL = "\n";
    static final String SEP = " ";

    List<Library> libraries = new ArrayList<>();

    private final Problem problem;
//    private long score = -1;

    // ===== Algorithm methods with logic =====

    // == Sort libraries

    public void sortLibrariesOnTime() {
        libraries.sort(Comparator.comparingInt(o -> o.signupTime));
    }

    public void sortLibrariesOnScoreLeft() {
        addAllBooks();
        libraries = new ArrayList<>();

        Set<Library> libsLeft = new HashSet<>(problem.libraries);
        Set<Book> justAdded = new HashSet<>();
        int day = 0;
        libsLeft.forEach(l -> l.booksOrder.sort(Comparator.comparingInt(o -> -o.value)));

        while (!libsLeft.isEmpty() && day < problem.numDays) {
            Library bestLibrary = null;
            int bestScore = -1;

            for (Library library : libsLeft) {
                library.booksOrder.removeAll(justAdded);
                int score = 0;
                Set<Book> booksAdd = new HashSet<>();
                for (int i = 0; i < library.canStillScan(day, problem.numDays); i++) {
                    score += library.booksOrder.get(i).value;
                    booksAdd.add(library.booksOrder.get(i));
                }
                if (score > bestScore) {
                    bestLibrary = library;
                    bestScore = score;
                    justAdded = booksAdd;
                }
            }

            libsLeft.remove(bestLibrary);
            libraries.add(bestLibrary);
            day += bestLibrary.signupTime;
        }
    }

    // == Sort books

    public void sortBooksOnScoreDumb() {
        for (Library library : libraries) {
            library.booksOrder.sort(Comparator.comparingInt(o -> -o.value));
        }
    }

    public void sortOnBooksLeft() {
        Set<Book> booksScanned = new HashSet<>();
        int day = 0;
        for (Library library : libraries) {
            day += library.signupTime;

            if (day >= problem.numDays) {
                break;
            }

            library.booksOrder.sort((o1, o2) -> {
                if (booksScanned.contains(o1) == booksScanned.contains(o2)) {
                    return o2.value - o1.value;
                } else {
                    if (booksScanned.contains(o1)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });

            for (int i = 0; i < library.canStillScan(day, problem.numDays); i++) {
                booksScanned.add(library.booksOrder.get(i));
            }
        }
    }


    // ===== Other functionality =====

    public void addAllLibraries() {
        libraries = new ArrayList<>(problem.libraries);
    }

    public void addAllBooks() {
        for (Library library : libraries) {
            library.booksOrder = new ArrayList<>(library.books);
        }
    }

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

    private long calcScore() {
        int day = 0;
        Set<Book> booksScanned = new HashSet<>();
        Set<Library> librariesSeen = new HashSet<>();
        for (Library library : libraries) {
            if (librariesSeen.contains(library)) {
                throw new IllegalStateException("Library exists multiple times in solution.");
            }
            librariesSeen.add(library);

            day += library.signupTime;
            if (day >= problem.numDays) {
                break;
            }
            for (int i = 0; i < library.canStillScan(day, problem.numDays); i++) {
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
        return calcScore();
    }

    public void toFile() {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("solution/" + problem.name));
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
