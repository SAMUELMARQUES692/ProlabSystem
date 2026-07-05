package prolab.system.exception;

public class CaminhaoNotFoundException extends RuntimeException{
    public CaminhaoNotFoundException(String message) {
        super(message);
    }
}
