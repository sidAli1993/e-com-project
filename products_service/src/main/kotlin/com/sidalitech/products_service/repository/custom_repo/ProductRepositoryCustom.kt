package com.sidalitech.products_service.repository.custom_repo

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository

@Repository
interface ProductRepositoryCustom {
    suspend fun findById(id:String):Map<String,Any>?

    suspend fun findByName(name:String):Map<String,Any>?

    suspend fun findAll(page:Int,size:Int): Flow<List<Map<String, Any>>>

    suspend fun findByCatId(id:String,page:Int,size:Int):Flow<List<Map<String,Any>>>

    suspend fun findByCatName(name:String,page:Int,size:Int):Flow<List<Map<String,Any>>>
}