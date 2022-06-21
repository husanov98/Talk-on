package uz.mh.talkoncopy.repositories.user.user;

import org.springframework.stereotype.Repository;
import uz.mh.talkoncopy.entities.user.User;

import uz.mh.talkoncopy.repositories.base.AbstractRepository;

import java.util.Optional;
@Repository
public interface UserRepository extends AbstractRepository<User,String> {
    Optional<User> findByPhoneNumberAndDeletedFalse(String phoneNumber);
}
