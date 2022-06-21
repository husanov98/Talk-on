package uz.mh.talkoncopy.validators.base;

import uz.mh.talkoncopy.dto.base.BaseGenericDto;
import uz.mh.talkoncopy.dto.base.GenericDto;

import javax.validation.ValidationException;
import java.io.Serializable;

public abstract class AbstractValidator <CD extends BaseGenericDto,UD extends GenericDto,K extends Serializable> implements BaseGenericValidator{

    public abstract void validateKey(K id) throws ValidationException;

    public abstract void validOnCreate(CD dto)throws ValidationException;

    public abstract void validOnUpdate(UD dto) throws ValidationException;
}
