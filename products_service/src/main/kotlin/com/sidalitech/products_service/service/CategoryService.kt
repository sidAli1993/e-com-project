package com.sidalitech.products_service.service

import com.sidalitech.products_service.common.toNonNullMap
import com.sidalitech.products_service.model.Category
import com.sidalitech.products_service.repository.CategoryRepository
import jakarta.ws.rs.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class CategoryService @Autowired constructor(
    private val catRepo: CategoryRepository
) {
    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    suspend fun save(category: Category): Category {
        return catRepo.save(category)
    }

    suspend fun getAll(): List<Category> {
        val catList = mutableListOf<Category>()
        catRepo.findAll().collect {
            catList.add(it)
        }
        return catList
    }

    suspend fun findByName(catName: String): Category? {
        val query=Query(Criteria.where("catName").`is`(catName))
       val category= mongoTemplate.findOne(query,Category::class.java)
        return category
    }

    suspend fun findById(id: String): Category? {
        return catRepo.findById(id)
    }

    suspend fun isExist(id:String):Boolean{
       return catRepo.existsById(id)
    }

    suspend fun updateCategory(id: String, category: Category): Category {
        if (findById(id) == null) throw NotFoundException("category not found")
        val query = Query(Criteria.where("id").`is`(id))
        val update = Update()
        val catMap = category.toNonNullMap()
        catMap.forEach { (key, value) ->
            if (key != "id")
                update.set(key, value)
        }
        category.id=id
        mongoTemplate.updateFirst(query, update, Category::class.java)
        return category
    }
}