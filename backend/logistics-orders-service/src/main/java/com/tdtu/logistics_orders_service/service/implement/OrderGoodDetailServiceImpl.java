package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.logistics_orders_service.repository.OrderGoodDetailRepository;
import com.tdtu.logistics_orders_service.service.OrderGoodDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderGoodDetailServiceImpl implements OrderGoodDetailService {

    OrderGoodDetailRepository orderGoodDetailRepository;



}
