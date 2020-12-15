package ru.annikonenkov.common.exceptions;

public class UnavailableTargetElement extends UnavailableEntity {

    /** */
    private static final long serialVersionUID = 7925299172578969948L;

    public UnavailableTargetElement() {
        super();
    }

    public UnavailableTargetElement(Throwable cause) {
        super(cause);
    }

    public UnavailableTargetElement(String message) {
        super(message);
    }

    public UnavailableTargetElement(String message, Throwable cause) {
        super(message, cause);
    }

}
