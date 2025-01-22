package com.sidalitech.products_service.service

import com.sidalitech.products_service.common.toNonNullMap
import com.sidalitech.products_service.model.Product
import com.sidalitech.products_service.repository.ProductRepository
import com.sidalitech.products_service.repository.custom_repo.ProductRepositoryCustom
import jakarta.validation.ValidationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.ServerResponse.async

@Service
class ProductService @Autowired constructor(
    private val productRepository: ProductRepository,
    private val productRepositoryCustom: ProductRepositoryCustom
) {

    @Autowired
    lateinit var apiService: ApiService
    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Autowired
    lateinit var categoryService: CategoryService

    suspend fun create(product: Product): Product {
        return productRepository.save(product)
    }

    suspend fun getAll(page: Int = 1, size: Int = 10): MutableList<Map<String, Any>> {
        val map = mutableListOf<Map<String, Any>>()
        productRepositoryCustom.findAll(page, size).collect {
            map.addAll(it)
        }
        return map
    }

    suspend fun findById(id: String): Map<String, Any>? {
        return productRepositoryCustom.findById(id)
    }

    suspend fun getByName(name: String): Map<String, Any>? {
        return productRepositoryCustom.findByName(name)
    }

    suspend fun findByCatName(name: String, page: Int, size: Int): List<Map<String, Any>> {
        val products = mutableListOf<Map<String, Any>>()
        productRepositoryCustom.findByCatName(name, page, size).collect {
            products.addAll(it)
        }
        return products
    }

    suspend fun getByCategoryId(id: String, page: Int, size: Int): List<Map<String, Any>> {
        val products = mutableListOf<Map<String, Any>>()
        productRepositoryCustom.findByCatId(id, page, size).collect {
            products.addAll(it)
        }
        return products
    }
    suspend fun findByIds(ids:List<String>):List<Map<String,Any>>{
        val products= mutableListOf<Map<String,Any>>()
        productRepositoryCustom.findByIds(ids).collect{
            products.addAll(it)
        }
        return products
    }

    suspend fun isExist(id: String): Boolean {
        return productRepository.existsById(id)
    }

    fun getUserInfo(id:String){
        val api1 = runBlocking { apiService.getUserById("/auth/id/$id") }
        println(api1)
    }
    suspend fun updateProduct(id: String, product: Product): Product? {
        if (!isExist(id)) throw ValidationException("Product not exists")
        val query = Query(Criteria.where("id").`is`(id))
        val update = Update()
        val pMap = product.toNonNullMap()
        pMap.forEach { (key, value) ->
            if (key != "id" && key != "catId" && key!="vendorId") {
                update.set(key, value)
            }
        }
        product.id = id
        mongoTemplate.updateFirst(query, update, Product::class.java)
        return product
    }
}