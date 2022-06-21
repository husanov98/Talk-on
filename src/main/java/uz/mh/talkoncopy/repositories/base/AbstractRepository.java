package uz.mh.talkoncopy.repositories.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import uz.mh.talkoncopy.entities.base.BaseGenericEntity;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractRepository<E extends BaseGenericEntity,K extends Serializable> extends BaseGenericRepository, JpaRepository<E,K> {

}
