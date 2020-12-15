package ru.annikonenkov.common.exceptions;

public class UnavailableParentElement extends UnavailableEntity {

    /** */
    private static final long serialVersionUID = 6990527685293248175L;

    public UnavailableParentElement() {
        super();
    }

    public UnavailableParentElement(Throwable cause) {
        super(cause);
    }

    public UnavailableParentElement(String message) {
        super(message);
    }
    
    public UnavailableParentElement(String message, Throwable cause) {
        super(message, cause);
    }

}
