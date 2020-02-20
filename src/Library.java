import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Library {

    final int id;
    final int numBooks;
    final int signupTime;
    final int booksPerDay;
    final Set<Book> books = new HashSet<>();

    List<Book> booksOrder = new ArrayList<>();

    public Library(int id, int numBooks, int signupTime, int booksPerDay) {
        this.id = id;
        this.numBooks = numBooks;
        this.signupTime = signupTime;
        this.booksPerDay = booksPerDay;
    }

    public Library cloneSolution() {
        Library library = new Library(id, numBooks, signupTime, booksPerDay);
        library.booksOrder.addAll(booksOrder);
        return library;
    }

    public int canStillScan(int day, int numDays) {
        return Math.min(booksOrder.size(), (numDays - day) * booksPerDay);
    }
}
