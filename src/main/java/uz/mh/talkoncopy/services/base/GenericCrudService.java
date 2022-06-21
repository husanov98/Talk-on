package uz.mh.talkoncopy.services.base;

import uz.mh.talkoncopy.criteria.base.BaseGenericCriteria;
import uz.mh.talkoncopy.dto.base.BaseGenericDto;
import uz.mh.talkoncopy.dto.base.GenericDto;

import java.io.Serializable;

public interface GenericCrudService<
        D extends GenericDto,
        CD extends BaseGenericDto,
        UD extends GenericDto,
        K extends Serializable,
        C extends BaseGenericCriteria
        > extends GenericService<D,K,C>{
        K create(CD dto);
        void delete(K id);

        void update(UD dto);
        }
