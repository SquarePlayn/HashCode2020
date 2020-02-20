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

    public Problem(String name, int numBooks, int numLibs, int numDays, ArrayList<Library> libraries, ArrayList<Book> books) {
        this.name = name;
        this.numBooks = numBooks;
        this.numLibs = numLibs;
        this.numDays = numDays;
        this.libraries = libraries;
        this.books = books;
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

        System.out.println("Num books: " + numBooks);
        System.out.println("Num libs: " + numLibs);
        System.out.println("Num days: " + numDays);
        System.out.println();

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
    }
}
