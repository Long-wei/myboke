package com.boke.index.controller;

import com.boke.index.service.PersonalInformationService;
import com.boke.index.model.User;
import com.boke.index.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonalInformationController {
    @Autowired
    private PersonalInformationService personalInformationService;

    @PostMapping("/personal/updateUser")
    public Result updateUser(@RequestBody User user){
        int i = personalInformationService.updateUser(user);
        if(i==0){
            return Result.error("error");
        }
        return Result.success("success");
    }
}
