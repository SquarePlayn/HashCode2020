import java.util.HashSet;
import java.util.Set;

public class Book {

    final int id;
    final int value;
    final Set<Library> libraries = new HashSet<>();

    public Book(int id, int value) {
        this.id = id;
        this.value = value;
    }
}
