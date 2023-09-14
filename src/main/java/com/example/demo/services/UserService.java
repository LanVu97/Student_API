package com.example.demo.services;

import com.example.demo.exceptions.EtAuthException;
import com.example.demo.model.User;

public interface UserService {
    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(User user) throws EtAuthException;
}
