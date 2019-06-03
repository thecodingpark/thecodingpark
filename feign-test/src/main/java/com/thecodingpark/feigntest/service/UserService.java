package com.thecodingpark.feigntest.service;

import com.thecodingpark.feigntest.api.UserFeignClient;
import com.thecodingpark.feigntest.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    public List<User> getUsers() {
        return this.userFeignClient.getUsers();
    }
}
