package uz.mh.talkoncopy.exceptions.user;


import uz.mh.talkoncopy.exceptions.ValidationException;

public class NotFoundUserException extends ValidationException {
    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
