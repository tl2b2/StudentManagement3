package raisetech.StudentManagement.exception;

public class TestException extends Exception{
    public TestException() {
        super();
    }

    public TestException(Throwable cause) {
        super(cause);
    }

    public TestException(String message) {
        super(message);
    }

    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    protected TestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
