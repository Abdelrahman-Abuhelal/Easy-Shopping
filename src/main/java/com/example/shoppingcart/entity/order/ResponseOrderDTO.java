package com.example.shoppingcart.entity.order;

import lombok.Data;

@Data

public class ResponseOrderDTO {

    private Float amount;
    private Long invoiceNumber;
    private String date;
    private String orderDescription;
    private Long orderId;

}
