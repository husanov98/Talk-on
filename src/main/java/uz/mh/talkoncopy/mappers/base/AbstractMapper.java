package uz.mh.talkoncopy.mappers.base;

import uz.mh.talkoncopy.dto.base.BaseGenericDto;
import uz.mh.talkoncopy.dto.base.GenericDto;
import uz.mh.talkoncopy.entities.base.BaseGenericEntity;

import java.util.List;

public interface AbstractMapper<E extends BaseGenericEntity, D extends GenericDto, CD extends BaseGenericDto, UP extends GenericDto> extends BaseGenericMapper{
    D toDto(E entity);

    List<D> toDto(List<E> entities);

    E fromCreateDto(CD createDto);

    E fromUpdateDto(UP updateDto);
}
