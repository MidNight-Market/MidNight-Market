package com.project.www.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HelpVO {
    private long hno; //순번
    private String id; //작성자id
    private String title;
    private String content;
    private String reply; //답변유무
    private String customerId; //고객번호
    private String regAt; //작성일
}
