import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Problem {

    final String name;
    final int numBooks;
    final int numLibs;
    final int numDays;
    final ArrayList<Library> libraries;
    final ArrayList<Book> books;

    long scoreUpperBound;
    int minBookValue;
    int maxBookValue;
    double avgBookValue;
    int minBooksPerLib;
    int maxBooksPerLib;
    double avgBooksPerLib;
    long totalBooksInLibs;
    int minConstructTime;
    int maxConstructTime;
    long totalConstructTime;
    double avgConstructTime;
    int minScanPerDay;
    int maxScanPerDay;
    long totalScanPerDay;
    double avgScanPerDay;

    public Problem(String name, int numBooks, int numLibs, int numDays, ArrayList<Library> libraries, ArrayList<Book> books) {
        this.name = name;
        this.numBooks = numBooks;
        this.numLibs = numLibs;
        this.numDays = numDays;
        this.libraries = libraries;
        this.books = books;

        calcStats();
    }

    public Problem(String name) {
        this.name = name;

        Scanner sc = null;
        try {
            sc = new Scanner(new File("data/" + name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        this.numBooks = sc.nextInt();
        this.numLibs = sc.nextInt();
        this.numDays = sc.nextInt();

        books = new ArrayList<>();
        for (int i = 0; i < numBooks; i ++) {
            books.add(new Book(i, sc.nextInt()));
        }

        libraries = new ArrayList<>();
        for (int i = 0; i < numLibs; i ++) {
            Library library = new Library(i, sc.nextInt(), sc.nextInt(), sc.nextInt());
            for (int j = 0; j < library.numBooks; j ++) {
                Book book = books.get(sc.nextInt());
                library.books.add(book);
                book.libraries.add(library);
            }
            libraries.add(library);
        }

        calcStats();
        printStats();
    }

    private void calcStats() {
        minBookValue = Integer.MAX_VALUE;
        maxBookValue = 0;
        long totalBookValues = 0;
        for (Book book : books) {
            totalBookValues += book.value;
            minBookValue = Math.min(minBookValue, book.value);
            maxBookValue = Math.max(maxBookValue, book.value);
        }
        scoreUpperBound = totalBookValues;
        avgBookValue = totalBookValues / (double) books.size();

        totalBooksInLibs = 0;
        totalConstructTime = 0;
        totalScanPerDay = 0;
        minBooksPerLib = Integer.MAX_VALUE;
        minConstructTime = Integer.MAX_VALUE;
        minScanPerDay = Integer.MAX_VALUE;
        maxBooksPerLib = 0;
        maxConstructTime = 0;
        maxScanPerDay = 0;
        for (Library library : libraries) {
            totalBooksInLibs += library.books.size();
            totalConstructTime += library.signupTime;
            totalScanPerDay += library.booksPerDay;

            minBooksPerLib = Math.min(minBooksPerLib, library.books.size());
            minConstructTime = Math.min(minConstructTime, library.signupTime);
            minScanPerDay = Math.min(minScanPerDay, library.booksPerDay);

            maxBooksPerLib = Math.max(maxBooksPerLib, library.books.size());
            maxConstructTime = Math.max(maxConstructTime, library.signupTime);
            maxScanPerDay = Math.max(maxScanPerDay, library.booksPerDay);
        }
        avgBooksPerLib = totalBooksInLibs / (double) libraries.size();
        avgConstructTime = totalConstructTime / (double) libraries.size();
        avgScanPerDay = totalScanPerDay / (double) libraries.size();
    }

    public void printStats() {
        System.out.println("Num books: " + numBooks);
        System.out.println("Num libs: " + numLibs);
        System.out.println("Num days: " + numDays);
        System.out.println("Total value: " + getScoreUpperBound());
        System.out.println("Min book val: " + minBookValue);
        System.out.println("Max book val: " + maxBookValue);
        System.out.println("AVG book val: " + avgBookValue);
        System.out.println("Total books in libs: " + totalBooksInLibs);
        System.out.println("Min books/lib: " + minBooksPerLib);
        System.out.println("Max books/lib: " + maxBooksPerLib);
        System.out.println("AVG books/lib: " + avgBooksPerLib);
        System.out.println("Total construct time: " + totalConstructTime);
        System.out.println("Min construct: " + minConstructTime);
        System.out.println("Max construct: " + maxConstructTime);
        System.out.println("AVG construct: " + avgConstructTime);
        System.out.println("Total scans per day: " + totalScanPerDay);
        System.out.println("Min books/day: " + minScanPerDay);
        System.out.println("Max books/day: " + maxScanPerDay);
        System.out.println("AVG books/day: " + avgScanPerDay);
        System.out.println();
    }

    private long getScoreUpperBound() {
        return scoreUpperBound;
    }
}
