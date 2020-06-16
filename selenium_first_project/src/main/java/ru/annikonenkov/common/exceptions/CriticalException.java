package ru.annikonenkov.common.exceptions;


public class CriticalException extends Exception {

    /** */
    private static final long serialVersionUID = -1369645793705878037L;
    
    public CriticalException() {
        super();
    }

    public CriticalException(String message) {
        super(message);
    }

}
