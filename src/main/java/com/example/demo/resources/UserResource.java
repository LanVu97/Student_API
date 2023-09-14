package com.example.demo.resources;

import com.example.demo.Constrants;
import com.example.demo.exceptions.EtAuthException;
import com.example.demo.model.User;
import com.example.demo.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String >> registerUser(@RequestBody User user) throws EtAuthException {

        User newUser = userService.registerUser(user);

        return new ResponseEntity<>(generateJWTToken(newUser), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String >> loginUser(@RequestBody Map<String, Object> userMap) throws EtAuthException {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser( email, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(User user){
        long timeStamp = System.currentTimeMillis();
        String  token = Jwts.builder().signWith(Constrants.generateSecreteKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date(timeStamp))
                .setExpiration(new Date(timeStamp + Constrants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;

    }

}
