package com.fw.bo.homepage.controller;

import com.fw.bo.homepage.service.BoCommunityService;
import com.fw.core.dto.bo.BoFaqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/bo/homepage/community")
public class BoCommunityController {
    private final BoCommunityService boCommunityService;

    @Value("${bo.session.key-name}")
    private String BO_SESSION_KEY;

    @GetMapping({"", "/"})
    String communityList(Model model, BoFaqDTO boFaqDTO){


        return "/bo/homepage/community/list";
    }
}
