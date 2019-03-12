package com.forum.web.controller.index;

import com.forum.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {

    @RequestMapping("/index")
    public String toIndex(Model model){
        return "index.html";
    }

}
