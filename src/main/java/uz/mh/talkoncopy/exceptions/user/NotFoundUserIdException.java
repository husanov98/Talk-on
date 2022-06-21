package uz.mh.talkoncopy.exceptions.user;


import uz.mh.talkoncopy.exceptions.ValidationException;

public class NotFoundUserIdException extends ValidationException {

    public NotFoundUserIdException(String message) {
        super(message);
    }

    public NotFoundUserIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
