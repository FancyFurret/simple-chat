package main.java.simplechat.core.exceptions;

public class ChatException extends Exception {
    private String message;
    private Exception innerException;

    public ChatException(String message, Exception e) {
        this.message = message;
        innerException = e;
    }

    public String printMessage() {
        printStackTrace();
        return message + " (Check console output for more information)";
    }

    @Override
    public void printStackTrace() {
        innerException.printStackTrace();
    }
}
