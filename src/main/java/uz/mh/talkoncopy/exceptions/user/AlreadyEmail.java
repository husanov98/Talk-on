package uz.mh.talkoncopy.exceptions.user;

import uz.mh.talkoncopy.exceptions.ValidationException;

public class AlreadyEmail extends ValidationException {
    public AlreadyEmail(String message) {
        super(message);
    }

    public AlreadyEmail(String message,Throwable cause){super(message, cause);}
}
