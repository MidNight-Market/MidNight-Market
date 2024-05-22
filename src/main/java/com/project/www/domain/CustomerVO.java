package com.project.www.domain;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CustomerVO {
    private long customerCode;
    private String customerEmail;
    private String customerPw;
    private String customerNickName;
    private String customerRegisterDate;
    private String customerClass;
    private String address;

}
