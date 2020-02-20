import java.util.ArrayList;
import java.util.Collections;

public class Main {

    //    private static final Problem pr = new Problem("a_example.txt");
//    private static final Problem pr = new Problem("b_read_on.txt");
//    private static final Problem pr = new Problem("c_incunabula.txt");
//    private static final Problem pr = new Problem("d_tough_choices.txt");
//    private static final Problem pr = new Problem("e_so_many_books.txt");
//    private static final Problem pr = new Problem("f_libraries_of_the_world.txt");

    Problem prA, prB, prC, prD, prE, prF;
    boolean doA, doB, doC, doD, doE, doF;

    public void init() {
        doA = true;
        doB = true;
        doC = true;
        doD = true;
        doE = true;
        doF = true;
    }

    public Solution solve(Problem problem) {
        Solution solution;
        solution = random(problem);
//        solution = sortLibsOnStartTime(problem);

        return solution;
    }

    // TODO Set best book order when library order is known

    public Solution sortLibsOnStartTime(Problem problem) {
        Solution solution = new Solution(problem);

        solution.addAllLibraries();
        solution.addAllBooks();

//        solution.sortBooksOnScoreDumb();
//        solution.sortOnBooksLeft();
        solution.sortLibrariesOnScoreLeft();

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
        for (int i = 0; i < 1000; i++) {
            for (Library library : solution.libraries) {
                Collections.shuffle(library.booksOrder);
            }
            Collections.shuffle(solution.libraries);
            long score = solution.getScore();
            if (score > bestScore) {
                bestScore = score;
                bestSolution = solution.clone();
                System.out.println("Improved to " + bestScore);
                solution.toFile();

                solution.sortOnBooksLeft();
                score = solution.getScore();
                if (score > bestScore) {
                    bestScore = score;
                    bestSolution = solution.clone();
                    System.out.println("Improved with sortOnBooksLeft to " + bestScore);
                    solution.toFile();
                }
            }
            if (score * 1.0005 > bestScore && false) {
                if (solution.getScore() > bestScore) {
                    bestScore = solution.getScore();
                    bestSolution = solution.clone();
                    System.out.println("Improved with sortOnBooksLeft to " + bestScore);
                    solution.toFile();
                }
            }
        }

        return bestSolution;
    }

    public void run() {
        if (doA) {
            prA = new Problem("a_example.txt");
        }
        if (doB) {
            prB = new Problem("b_read_on.txt");
        }
        if (doC) {
            prC = new Problem("c_incunabula.txt");
        }
        if (doD) {
            prD = new Problem("d_tough_choices.txt");
        }
        if (doE) {
            prE = new Problem("e_so_many_books.txt");
        }
        if (doF) {
            prF = new Problem("f_libraries_of_the_world.txt");
        }

        if (doA) {
            Solution a = solve(prA);
            System.out.println("A: " + a.getScore());
            a.toFile();
        }

        if (doB) {
            Solution b = solve(prB);
            System.out.println("B: " + b.getScore());
            b.toFile();
        }

        if (doC) {
            Solution c = solve(prC);
            System.out.println("C: " + c.getScore());
            c.toFile();
        }

        if (doD) {
            Solution d = solve(prD);
            System.out.println("D: " + d.getScore());
            d.toFile();
        }

        if (doE) {
            Solution e = solve(prE);
            System.out.println("E: " + e.getScore());
            e.toFile();
        }

        if (doF) {
            Solution f = solve(prF);
            System.out.println("F: " + f.getScore());
            f.toFile();
        }

    }


    public static void main(String[] args) {
        Main main = new Main();
        main.init();
        main.run();
    }

}
