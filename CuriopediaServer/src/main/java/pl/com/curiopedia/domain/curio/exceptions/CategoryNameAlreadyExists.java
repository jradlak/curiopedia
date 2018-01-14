package pl.com.curiopedia.domain.curio.exceptions;

/**
 * Created by jakub on 14.01.18.
 */
public class CategoryNameAlreadyExists extends Exception {
    public CategoryNameAlreadyExists() {
    }

    public CategoryNameAlreadyExists(String s) {
        super(s);
    }
}
