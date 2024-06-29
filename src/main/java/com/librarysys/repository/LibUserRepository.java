package com.librarysys.repository;

import com.librarysys.entity.LibUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibUserRepository extends MongoRepository<LibUser, String>
{
    LibUser save(LibUser libUser);
}
