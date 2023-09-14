package com.example.demo.services;

import com.example.demo.exceptions.EtAuthException;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if(email!=null) email=email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(User user) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        String email = user.getEmail();
        if(email != null) email = email.toLowerCase();

        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email format");

        Integer count = userRepository.getCountByEmail(email);
        if(count > 0){
            throw new EtAuthException("Email already in use");
        }
        return userRepository.save(user);

    }

}
