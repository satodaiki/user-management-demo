package com.satodai.demo.login.domain.service;

import com.satodai.demo.login.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao dao;
}
