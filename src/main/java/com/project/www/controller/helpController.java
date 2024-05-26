package com.project.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/help/*")
@Controller
public class helpController {

  @GetMapping("/desk")
  public void desk(){}

}
