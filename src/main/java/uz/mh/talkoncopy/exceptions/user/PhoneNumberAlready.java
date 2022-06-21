package uz.mh.talkoncopy.exceptions.user;


import uz.mh.talkoncopy.exceptions.ValidationException;

public class PhoneNumberAlready extends ValidationException {
    public PhoneNumberAlready(String message) {
        super(message);
    }

    public PhoneNumberAlready(String message, Throwable cause) {
        super(message, cause);
    }
}
