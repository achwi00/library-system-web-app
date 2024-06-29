package com.librarysys.service;

import com.librarysys.repository.LibUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibUserService
{
    @Autowired
    private LibUserRepository libUserRepository;
}
