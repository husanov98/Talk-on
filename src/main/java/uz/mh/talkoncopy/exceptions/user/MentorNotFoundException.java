package uz.mh.talkoncopy.exceptions.user;

import uz.mh.talkoncopy.exceptions.ValidationException;

public class MentorNotFoundException extends ValidationException {
    public MentorNotFoundException(String message) {
        super(message);
    }

    public MentorNotFoundException(String message,Throwable cause){super(message, cause);}
}
