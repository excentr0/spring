package com.excentro.geekbrains.persistence.repo;

import com.excentro.geekbrains.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}
