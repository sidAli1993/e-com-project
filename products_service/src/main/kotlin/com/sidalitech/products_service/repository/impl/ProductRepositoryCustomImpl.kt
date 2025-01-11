package com.sidalitech.products_service.repository.impl

import com.sidalitech.products_service.common.toNonNullMap
import com.sidalitech.products_service.common.toNullableMap
import com.sidalitech.products_service.model.dto_response.DTOUsers
import com.sidalitech.products_service.repository.custom_repo.ProductRepositoryCustom
import com.sidalitech.products_service.service.ApiService
import com.sidalitech.products_service.utils.JwtContextHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class ProductRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate,
    private val apiService: ApiService
) : ProductRepositoryCustom {
    override suspend fun findById(id: String): Map<String, Any>? {
        val matchOperation = Aggregation.match(Criteria.where("_id").`is`(id))
        val lookUpOperation = Aggregation.lookup("category", "catId", "_id", "productCategory")
        val unWindOperation = Aggregation.unwind("productCategory", true)
        val projectOperation = Aggregation.project()
            .andExclude("catId") // Exclude the 'catId' field
        val aggregation = Aggregation.newAggregation(matchOperation, lookUpOperation, unWindOperation, projectOperation)
        val results = mongoTemplate.aggregate(
            aggregation,
            "product",
            Map::class.java
        ).mappedResults.filterIsInstance<Map<String, Any>>().firstOrNull()
        val vendorId = results?.get("vendorId") as? String
        val user = vendorId?.let { apiService.getUserById("/auth/id/$it") }
        val updatedResults = results?.toMutableMap()?.also { mutableResults ->
            user?.let {
                mutableResults["user"] = it.data
            }
        }
        return updatedResults
    }

    override suspend fun findByName(name: String): Map<String, Any>? {
        val aggregation= Aggregation.newAggregation(
            Aggregation.match(Criteria.where("name").`is`(name)),
            Aggregation.lookup("category","catId","_id","productCategory"),
            Aggregation.unwind("productCategory",true),)

        val productMap= mongoTemplate.aggregate(aggregation,"product",Map::class.java)
            .mappedResults.filterIsInstance<Map<String,Any>>().firstOrNull()

        val vendorId= productMap?.get("vendorId") as String
        val user=apiService.getUserById("/auth/id/$vendorId")
        val updatedProductMap= productMap.toMutableMap().also { product->
            user?.let{
                product["user"]=it.data
            }
        }
        return updatedProductMap
    }

    override suspend fun findAll(page:Int,size:Int): Flow<List<Map<String, Any>>> = flow{
        val toSkip=(page-1)*size
        val aggregation = Aggregation.newAggregation(
            Aggregation.lookup("category", "catId", "_id", "productCategory"),
            Aggregation.skip(toSkip.toLong()),
            Aggregation.limit(size.toLong())
        )
       val results= withContext(Dispatchers.IO){
           mongoTemplate.aggregate(
               aggregation,
               "product",
               Map::class.java
           ).mappedResults.filterIsInstance<Map<String, Any>>()
       }
        val vendorIds=results.map { it["vendorId"] as String }
        val users=apiService.getAllUsersByIds(vendorIds,"/auth/ids")
        val usersMap: Map<String, DTOUsers> = users.data.associateBy { it.id }
        val updatedMap = results.map { product ->
            val vendorId = product["vendorId"] as String
            val user = usersMap[vendorId]
            if (user != null) {
                product + ("user" to user)  // Merging user object into the product map
            } else {
                product
            }
        }
        emit(updatedMap)
    }

    override suspend fun findByCatId(id: String,page:Int,size:Int): Flow<List<Map<String, Any>>> = flow{
        val toSkip=(page-1)*size
        val aggregation= Aggregation.newAggregation(
            Aggregation.match(Criteria.where("catId").`is`(id)),
            Aggregation.skip(toSkip.toLong()),
            Aggregation.limit(size.toLong()),
            Aggregation.lookup("category","catId","_id","productCategory"),
            Aggregation.unwind("productCategory",true)
        )
        val result= mongoTemplate.aggregate(aggregation,"product",Map::class.java).mappedResults.filterIsInstance<Map<String,Any>>()
        val vendorIds=result.map { it["vendorId"] as String }
        val users=apiService.getAllUsersByIds(vendorIds,"/auth/ids")
        val userMap=users.data.associateBy { it.id }
        val productsMap=result.map { product->
            val vendorId=product["vendorId"] as String
            val user=userMap[vendorId]
            if (user!=null){
                product + ("user" to user)
            }else{
                product
            }
        }
        emit(productsMap)
    }

    override suspend fun findByCatName(name: String, page: Int, size: Int): Flow<List<Map<String, Any>>> = flow {
        val toSkip = (page - 1) * size
        val categoryAggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("catName").`is`(name)),
            Aggregation.project("_id")
        )
        val categoryResult = mongoTemplate.aggregate(categoryAggregation, "category", Map::class.java).mappedResults.firstOrNull()
        if (categoryResult==null) {
            emit(emptyList())
            return@flow
        }
        val categoryId = categoryResult["_id"] as String
        println(categoryId)
        val aggregation= Aggregation.newAggregation(
            Aggregation.match(Criteria.where("catId").`is`(categoryId)),
            Aggregation.skip(toSkip.toLong()),
            Aggregation.limit(size.toLong()),
            Aggregation.lookup("category","catId","_id","productCategory"),
            Aggregation.unwind("productCategory",true)
        )
        val result= mongoTemplate.aggregate(aggregation,"product",Map::class.java).mappedResults.filterIsInstance<Map<String,Any>>()
        val vendorIds=result.map { it["vendorId"] as String }
        val users=apiService.getAllUsersByIds(vendorIds,"/auth/ids")
        val userMap=users.data.associateBy { it.id }
        val productsMap=result.map { product->
            val vendorId=product["vendorId"] as String
            val user=userMap[vendorId]
            if (user!=null){
                product + ("user" to user)
            }else{
                product
            }
        }
        emit(productsMap)
    }

}