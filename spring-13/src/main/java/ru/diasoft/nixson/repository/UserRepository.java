package ru.diasoft.nixson.repository;

import org.springframework.data.repository.CrudRepository;
import ru.diasoft.nixson.domain.User;

public interface UserRepository extends CrudRepository<User,String> {
}
