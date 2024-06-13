package com.project.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersVO {

    private long id;
    private String merchantUid;
    private String customerId;
    private long productId;
    private long qty;
    private long payPrice;
    private String ordersDate;
    private String status;
    private String statusDate;
    private boolean orderConfirmed; //주문확정 체크
    private boolean isReviewComment; //상품주문한 사람이 리뷰를 달았는지 안달았는지 확인
    private ProductVO productVO;


}
