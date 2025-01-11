package com.sidalitech.products_service.repository

import com.sidalitech.products_service.model.Product
import com.sidalitech.products_service.repository.custom_repo.ProductRepositoryCustom
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CoroutineCrudRepository<Product,String> {

}