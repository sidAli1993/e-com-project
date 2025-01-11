package com.sidalitech.products_service.repository

import com.sidalitech.products_service.model.Category
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
interface CategoryRepository :CoroutineCrudRepository<Category,String> {
    fun findByCatName(catName:String):Category?
}