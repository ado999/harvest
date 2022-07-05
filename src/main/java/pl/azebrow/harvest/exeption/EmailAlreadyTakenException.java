package pl.azebrow.harvest.exeption;

public class EmailAlreadyTakenException extends RuntimeException{
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
