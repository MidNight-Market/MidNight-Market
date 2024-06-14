package com.project.www.controller;

import com.project.www.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/qna/*")
@RequiredArgsConstructor
@Controller
public class QnaController {
    private final QnaService qsv;


}
