package com.librarysys.repository;

import com.librarysys.entity.LibUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LibUserRepository extends MongoRepository<LibUser, String>
{
    LibUser save(LibUser libUser);

    boolean existsByEmailAndPassword(String email, String password);

    LibUser findByEmailAndPassword(String email, String password);

    boolean existsBySessionKey(String sessionKey);

    LibUser findBySessionKey(String sessionKey);
    LibUser findByUserId(ObjectId userId);

    LibUser findByCardNumber(String cardnumber);
    List<LibUser> findAllByRole(LibUser.UserRole role);
    void deleteLibUserByCardNumber(String cardNumber);
}
