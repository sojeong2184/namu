package com.durianfirst.durian.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    //상품 페이지에서 주문할 상품의 아이디와 주문 수량을 전달받을 DTO

    @NotNull(message = "상품 이름은 필수 입력값입니다.")
    private Long ono; //주문번호

    private Long ino; //상품번호
    
    private String mid; //회원번호

    private int totalPrice; //구매가격

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    @Max(value = 5, message = "최대 주문 수량은 5개 입니다.")
    private int count;


    private LocalDateTime odate; //주문일

    private LocalDateTime regDate; //등록일

    private LocalDateTime modDate; //수정일

}
