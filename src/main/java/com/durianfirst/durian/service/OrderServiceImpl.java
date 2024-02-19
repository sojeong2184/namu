package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.OrderDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Order;
import com.durianfirst.durian.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Long register(OrderDTO orderDTO) {

        Map<String, Object> entityMap = dtoToEntity(orderDTO);
        Order order = (Order) entityMap.get("order");

        orderRepository.save(order);

        return order.getOno();
    }

    @Override
    public PageResultDTO<OrderDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("ono").descending());

        Page<Object[]> result = orderRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });


        Function<Object[], OrderDTO> fn = (arr -> entitiesToDTO(
                (Order)arr[0])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public OrderDTO getOrder(Long ono) { //해당 번호로 조회
        List<Object[]> result = orderRepository.getOrderWithAll(ono);

        Order order = (Order) result.get(0)[0];

        return entitiesToDTO(order);

    }

    @Override
    public OrderDTO getMid(String mid) { //해당 아이디로 조회
        List<Object[]> result = orderRepository.getOrderMidWithAll(mid);

        Order order = (Order) result.get(0)[0];

        return entitiesToDTO(order);

    }

    @Override
    public OrderDTO getOM(Long ono, String mid) { //해당아이디와 번호로 조회

        List<Object[]> result = orderRepository.getOrderMidWithAll(mid);
        Order order = (Order) result.get(0)[0];
        return entitiesToDTO(order);
    }

    @Override
    public OrderDTO getIno(Long ino) { //해당 제품번호로 조회
        List<Object[]> result = orderRepository.getOrderInoWithAll(ino);

        Order order = (Order) result.get(0)[0];

        return entitiesToDTO(order);

    }

    @Override
    public Object count() {
        orderRepository.count();
        return orderRepository.count();
    }

    @Override
    // 사용자가 작성한 글만을 조회하는 메서드
    public List<Order> getOrdersByMid(String mid) {
        return orderRepository.findByMember_Mid(mid);
    }

}
