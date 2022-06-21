package uz.mh.talkoncopy.exceptions.user;

import uz.mh.talkoncopy.exceptions.ValidationException;

public class UserNotFoundException extends ValidationException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message,Throwable cause){super(message, cause);}
}
