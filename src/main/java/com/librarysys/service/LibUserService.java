package com.librarysys.service;

import com.librarysys.entity.Book;
import com.librarysys.entity.LibUser;
import com.librarysys.repository.LibUserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibUserService
{
    @Autowired
    private LibUserRepository libUserRepository;

    public LibUser addUser(String email, String password, String name, String surname, String role) {
        LibUser libUser = new LibUser();
        libUser.setEmail(email);
        libUser.setPassword(password);
        libUser.setName(name);
        libUser.setSurname(surname);
        libUser.setRole(role);
        return libUserRepository.save(libUser);
    }
    public LibUser addUser(String name, String surname, String role, String cardNum) {
        LibUser libUser = new LibUser();
        libUser.setName(name);
        libUser.setSurname(surname);
        libUser.setRole(role);
        libUser.setCardNumber(cardNum);
        return libUserRepository.save(libUser);
    }
    public boolean doesExist(String email, String password){
        return libUserRepository.existsByEmailAndPassword(email, password);
    }

    public ObjectId findUser(String email, String password){
        LibUser libUser = libUserRepository.findByEmailAndPassword(email, password);
        if(libUser != null) return libUser.getUserId();
        else throw new RuntimeException("User not found!");
    }

    public ObjectId findUserByCard(String cardNum){
        LibUser libUser = libUserRepository.findByCardNumber(cardNum);
        if(libUser != null) return libUser.getUserId();
        else throw new RuntimeException("User not found!");
    }

    public boolean existsBySessionKey(String sessionKey){
        return libUserRepository.existsBySessionKey(sessionKey);
    }

    public void updateSessionKey(ObjectId id, String sessionKey){
        LibUser libUser = libUserRepository.findByUserId(id);
        libUser.setSessionKey(sessionKey);
        libUserRepository.save(libUser);
    }

    public ObjectId findIdBySessionKey(String sessionKey){
        LibUser libUser = libUserRepository.findBySessionKey(sessionKey);
        return libUser.getUserId();
    }

    public List<LibUser> getAllReaders(){
        List<LibUser> users = libUserRepository.findAllByRole(LibUser.UserRole.CUSTOMER);
        if(users.get(0) != null) return users;
        else return null;
    }

    public void deleteUserByCard(String cardNumber){
        libUserRepository.deleteLibUserByCardNumber(cardNumber);
    }

}
