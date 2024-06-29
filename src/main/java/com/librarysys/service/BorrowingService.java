package com.librarysys.service;

import com.librarysys.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowingService
{
    @Autowired
    private BorrowingRepository borrowingRepository;
}
