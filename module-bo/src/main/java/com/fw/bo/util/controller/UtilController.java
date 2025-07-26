package com.fw.bo.util.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class UtilController {
    @GetMapping("/error/403")
    public String util403(ModelMap modelMap) {
        return "/util/403";
    }
}
