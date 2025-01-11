package com.sidalitech.products_service.controller

import com.sidalitech.products_service.common.buildResponse
import com.sidalitech.products_service.common.exceptions.NotFoundException
import com.sidalitech.products_service.common.exceptions.ValidationException
import com.sidalitech.products_service.model.Category
import com.sidalitech.products_service.model.response_model.BaseResponse
import com.sidalitech.products_service.service.CategoryService
import jakarta.validation.Valid
import jakarta.ws.rs.PathParam
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class CategoryController {
    @Autowired
    lateinit var categoryService: CategoryService

    @PostMapping("/category")
    fun save(@Valid @RequestBody category: Category):ResponseEntity<BaseResponse<Any>>{
        val cat= runBlocking {
            if (categoryService.findByName(category.catName)!=null) throw ValidationException("category already exists")
            categoryService.save(category)
        }

        return buildResponse(
            "success",
            "category has been added",
            HttpStatus.CREATED,
            data = cat
        )
    }

    @GetMapping("/category")
    fun getCategories():ResponseEntity<BaseResponse<Any>>{
        val catList= runBlocking { categoryService.getAll() }
        if (catList.isEmpty()) throw Exception("No categories found")

        return buildResponse(
            "success",
            "categories found",
            HttpStatus.OK,
            data = catList
        )
    }

    @GetMapping("/getCatByName/{catName}")
    fun findByName(@PathVariable catName:String):ResponseEntity<BaseResponse<Any>>{
        if (catName.isEmpty()) throw Exception("name is empty")
            val category= runBlocking { categoryService.findByName(catName) } ?: throw Exception("category not found")

        return buildResponse(
            "success",
            "category found",
            HttpStatus.OK,
            data = category
        )
    }

    @GetMapping("/category/{Id}")
    fun findById(@PathVariable Id:String):ResponseEntity<BaseResponse<Any>>{
        if (Id.isEmpty()) throw Exception("Id is empty")
        val category= runBlocking { categoryService.findById(Id) } ?: throw NotFoundException("category not found")

        return buildResponse(
            "success",
            "category found",
            HttpStatus.OK,
            data = category
        )
    }

    @PutMapping("/category/update/{id}")
    fun update(@PathVariable id:String,@RequestBody category: Category):ResponseEntity<BaseResponse<Any>>{
        if (id.isEmpty()) throw ValidationException("id is empty")
        val updatedCategory= runBlocking { categoryService.updateCategory(id,category) }
        return buildResponse(
            "success",
            "category updated successfully",
            HttpStatus.CREATED,
            data = updatedCategory
        )
    }
}