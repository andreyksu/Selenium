package ru.annikonenkov.common.exceptions;

public class UnavailableParentWebElement extends UnavailableEntity {

    /** */
    private static final long serialVersionUID = 6990527685293248175L;

    public UnavailableParentWebElement() {
        super();
    }

    public UnavailableParentWebElement(Throwable cause) {
        super(cause);
    }

    public UnavailableParentWebElement(String message) {
        super(message);
    }
    
    public UnavailableParentWebElement(String message, Throwable cause) {
        super(message, cause);
    }

}
