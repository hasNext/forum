package com.forum.web.controller.index;

import com.forum.utils.sequence.KeyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    private KeyFactory keyFactory;

    @RequestMapping("/test")
    public String test(){
        int i = keyFactory.generateKey("test1");
        return i + "";
    }

}
