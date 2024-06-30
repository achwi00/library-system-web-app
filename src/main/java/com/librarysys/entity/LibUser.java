package com.librarysys.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "libuser")
public class LibUser
{
    @Id
    private ObjectId userId;
    private String email;
    private String password;
    private String name;
    private String surname;
    private UserRole role;
    private String sessionKey;
    private String cardNumber;

    private enum UserRole{
        CUSTOMER,
        LIBRARIAN,
        ADMIN;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public ObjectId getUserId()
    {
        return userId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public UserRole getRole()
    {
        return role;
    }

    public String getSessionKey()
    {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
    }

    public void setRole(String role)
    {
        if(role.equalsIgnoreCase("customer")) this.role = UserRole.CUSTOMER;
        else if (role.equalsIgnoreCase("librarian")) this.role = UserRole.LIBRARIAN;
        else if (role.equalsIgnoreCase("admin")) this.role = UserRole.ADMIN;
    }
}
