package com.project.www.service;

import com.project.www.repository.QnaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QnaServiceImpl implements QnaService{
    private final QnaMapper qnaMapper;
}
