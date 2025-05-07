package com.sidalitech.order_service.reposittory

import com.sidalitech.order_service.model.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository :MongoRepository<Order,String>{

}