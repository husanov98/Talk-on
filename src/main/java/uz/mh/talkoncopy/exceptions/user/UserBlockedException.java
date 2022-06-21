package uz.mh.talkoncopy.exceptions.user;

import uz.mh.talkoncopy.exceptions.ValidationException;

public class UserBlockedException extends ValidationException {
    public UserBlockedException(String message) {
        super(message);
    }
}
