package uz.mh.talkoncopy.services.base;

import uz.mh.talkoncopy.mappers.base.BaseGenericMapper;
import uz.mh.talkoncopy.repositories.base.BaseGenericRepository;
import uz.mh.talkoncopy.validators.base.BaseGenericValidator;

public abstract class AbstractService<R extends BaseGenericRepository, M extends BaseGenericMapper, V extends BaseGenericValidator> implements BaseGenericService {
    protected final M mapper;

    protected final V validator;

    protected final R repository;

    protected AbstractService(M mapper, V validator, R repository) {
        this.mapper = mapper;
        this.validator = validator;
        this.repository = repository;
    }
}
