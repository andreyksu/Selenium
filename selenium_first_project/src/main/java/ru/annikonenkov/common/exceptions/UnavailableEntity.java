package ru.annikonenkov.common.exceptions;

public class UnavailableEntity extends Exception {

    /** */
    private static final long serialVersionUID = 3174566217301148285L;

    public UnavailableEntity() {
        super();
    }

    public UnavailableEntity(Throwable cause) {
        super(cause);
    }

    public UnavailableEntity(String message) {
        super(message);
    }

    public UnavailableEntity(String message, Throwable cause) {
        super(message, cause);
    }

}
