import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

//    private static final Problem prA = new Problem("a_example.txt");
//    private static final Problem prB = new Problem("b_read_on.txt");
//    private static final Problem prC = new Problem("c_incunabula.txt");
//    private static final Problem prD = new Problem("d_tough_choices.txt");
//    private static final Problem prE = new Problem("e_so_many_books.txt");
//    private static final Problem prF = new Problem("f_libraries_of_the_world.txt");

//    private static final Problem pr = new Problem("a_example.txt");
    private static final Problem pr = new Problem("b_read_on.txt");
//    private static final Problem pr = new Problem("c_incunabula.txt");
//    private static final Problem pr = new Problem("d_tough_choices.txt");
//    private static final Problem pr = new Problem("e_so_many_books.txt");
//    private static final Problem pr = new Problem("f_libraries_of_the_world.txt");


    public void run() {
        Solution solution;
        solution = sortLibsOnStartTime(pr);
//        solution = random(pr);
        System.out.println("Score: " + solution.getScore());
        solution.toFile();
    }

    // TODO Set best book order when library order is known

    public Solution sortLibsOnStartTime(Problem problem) {
        Solution solution = new Solution(problem);

        solution.addAllLibraries();
        solution.addAllBooks();

        solution.libraries.sort(Comparator.comparingInt(o -> o.signupTime));

        return solution;
    }

    public Solution random(Problem problem) {
        for (Library library : problem.libraries) {
            library.booksOrder = new ArrayList<>(library.books);
        }
        Solution solution = new Solution(problem);
        solution.libraries = new ArrayList<>(problem.libraries);

        // Shuffle
        Solution bestSolution = solution.clone();
        long bestScore = solution.getScore();
        for (int i = 0; i < 1000; i ++) {
            for (Library library : solution.libraries) {
                Collections.shuffle(library.booksOrder);
            }
            Collections.shuffle(solution.libraries);
            if (solution.getScore() > bestScore) {
                bestScore = solution.getScore();
                bestSolution = solution.clone();
            }
        }

        return bestSolution;
    }


    public static void main(String[] args) throws FileNotFoundException {
        new Main().run();
    }

}
