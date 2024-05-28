package com.project.www.controller;

import com.project.www.domain.HelpVO;
import com.project.www.service.HelpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/help/*")
@Controller
public class HelpController {
  private final HelpService hsv;
  private int isOK;

  @GetMapping("/desk")
  public void desk(){}

  @GetMapping("/register")
  public void register(){
  }

  @PostMapping("/register")
  public String register(HelpVO hvo){
    hvo.setCustomerId("test");
    isOK = hsv.register(hvo);

    return "redirect:/help/list";
  }

  @GetMapping("/list")
  public void list(Model m){
    List<HelpVO> list = hsv.getList();
    m.addAttribute("list", list);
}

//  @GetMapping("/modify")
//  public void modify(HelpVO hvo){
//  }
//
//  @PutMapping("/modify")
//  public String modify(){
//    return "redirect:/help/list";
//  }


}
