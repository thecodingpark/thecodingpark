package com.thecodingpark.feigntest.api;

import com.thecodingpark.feigntest.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="userFeignClient")
public interface UserFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    List<User> getUsers();

}