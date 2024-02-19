package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.OrderDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Item;
import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderService {

    Long register(OrderDTO orderDTO);

    PageResultDTO<OrderDTO, Object[]> getList(PageRequestDTO requestDTO); //목록 처리
    OrderDTO getOrder(Long ono);
    OrderDTO getMid(String mid);
    OrderDTO getOM(Long ono, String mid);

    OrderDTO getIno(Long ino);

    default OrderDTO entitiesToDTO(Order order) {
        OrderDTO orderDTO = OrderDTO.builder()
                .ono(order.getOno())
                .mid(order.getMember().getMid())
                .ino(order.getItem().getIno())
                .totalPrice(order.getTotalPrice())
                .odate(order.getOdate())
                .regDate(order.getRegDate())
                .modDate(order.getModDate())
                .build();

        return orderDTO;

    }

    default Map<String, Object> dtoToEntity(OrderDTO orderDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Order order = Order.builder()
                .ono(orderDTO.getOno())
                .member(Member.builder().mid(orderDTO.getMid()).build())
                .item(Item.builder().ino(orderDTO.getIno()).build())
                .totalPrice(orderDTO.getTotalPrice())
                .odate(orderDTO.getOdate())
                .build();
        entityMap.put("order",order);

        return entityMap;
    }

    Object count();

    List<Order> getOrdersByMid(String mid);

}
