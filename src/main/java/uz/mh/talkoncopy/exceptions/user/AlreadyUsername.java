package uz.mh.talkoncopy.exceptions.user;

import uz.mh.talkoncopy.exceptions.ValidationException;

public class AlreadyUsername extends ValidationException {
    public AlreadyUsername(String message) {
        super(message);
    }

    public AlreadyUsername(String message,Throwable cause){super(message, cause);}
}
