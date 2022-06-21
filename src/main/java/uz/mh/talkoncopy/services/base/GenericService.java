package uz.mh.talkoncopy.services.base;

import uz.mh.talkoncopy.criteria.base.BaseGenericCriteria;
import uz.mh.talkoncopy.dto.base.BaseGenericDto;

import java.io.Serializable;
import java.util.List;

public interface GenericService<
        D extends BaseGenericDto,
        K extends Serializable,
        C extends BaseGenericCriteria
        >extends BaseGenericService {
    D get(K id);

    List<D> getAll(C criteria);
}
