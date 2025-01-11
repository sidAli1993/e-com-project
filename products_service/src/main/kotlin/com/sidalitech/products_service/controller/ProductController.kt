package com.sidalitech.products_service.controller

import com.sidalitech.products_service.common.*
import com.sidalitech.products_service.model.Product
import com.sidalitech.products_service.model.response_model.BaseResponse
import com.sidalitech.products_service.service.CategoryService
import com.sidalitech.products_service.service.ProductService
import com.sidalitech.products_service.utils.JwtContextHolder
import com.sidalitech.products_service.utils.JwtTokenProvider
import jakarta.annotation.security.RolesAllowed
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.ValidationException
import jakarta.ws.rs.PathParam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import java.time.Duration
import kotlin.system.measureTimeMillis

@RestController
@RequestMapping("/product")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var categoryService: CategoryService

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    @PostMapping()
    fun save(@Valid @RequestBody product: Product): ResponseEntity<BaseResponse<Any>> {
        val prod = runBlocking(Dispatchers.IO) {
            if (!categoryService.isExist(product.catId)) throw ValidationException("category not exists")
            if (productService.getByName(product.name) != null)
                throw ValidationException("product name already exists")

            val userId=jwtTokenProvider.getIdFromToken(JwtContextHolder.getToken() ?: "")
            product.vendorId=userId
            productService.create(product)
        }
        val category = runBlocking(Dispatchers.IO) {
            categoryService.findById(prod.catId)
        } ?: throw ValidationException("category not found")

//
        val pMap = prod.toNullableMap().toMutableMap()
        pMap.remove("catId")
        pMap["productCategory"] = category

        return buildResponse(
            "success",
            "product created successfully",
            HttpStatus.CREATED,
            data = pMap
        )
    }
    fun fetchDataFromFlow(): Flow<String> {
        return flow {
            val data = (1..20).map { "Record $it" } // 1 million records
            data.forEach {
                delay(400)
                emit(it)  // Emit data
            }
        }
    }
    fun fetchDataSynchronously(): List<String> {
        val data = (1..5000000).map { "Record $it" } // 5 million records
        return data // Return the full list synchronously
    }

    @GetMapping("/stream-data")
    fun streamData(@RequestHeader("X-Token") token:String): ResponseEntity<Flux<Map<String, Any>>> {
        val roles=jwtTokenProvider.getRolesFromToken(token)
        val userId=jwtTokenProvider.getIdFromToken(token)
        println(userId)
        if (roles.contains("ROLE_USER")) throw ResponseStatusException(HttpStatus.FORBIDDEN,"Un-Authorized access")

        productService.getUserInfo(userId)
        val flux: Flux<String> = fetchDataFromFlow().asFlux()

        val responseFlux: Flux<Map<String, Any>> = flux
            .map { record ->
            mapOf(
                "status" to "success",
                "message" to "products list",
                "data" to record
            )
        }

        return ResponseEntity.ok(responseFlux)
    }
    @GetMapping("/stream-data-n")
    fun streamDataFlow(): Flow<Map<String, Any>> {
        val resp=fetchDataFromFlow().map {
            mapOf(
                "status" to "success",
                "message" to "products list",
                "data" to it
            )
        }
        return resp
    }

    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<BaseResponse<Any>> {
        val prodList = runBlocking(Dispatchers.IO) {
            productService.getAll(page, size)
        }
        return buildResponse(
            "success",
            "products list",
            HttpStatus.OK,
            data = prodList
        )
    }

    @GetMapping("name/{name}")
    fun getByName(@PathVariable name: String): ResponseEntity<BaseResponse<Any>> {
        val product = runBlocking(Dispatchers.IO) {
            productService.getByName(name)
        }
        if (product == null) throw Exception("product not found")
        return buildResponse(
            "success",
            "product found",
            HttpStatus.OK,
            data = product
        )
    }

    @GetMapping("id/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<BaseResponse<Any>> {
        val product = runBlocking {
            productService.findById(id)
        }
        if (product == null) throw Exception("product not found")
        return buildResponse(
            "success",
            "product found",
            HttpStatus.OK,
            data = product
        )
    }

    @GetMapping("category/name/{catName}")
    fun getByCatName(
        @PathVariable catName: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<BaseResponse<Any>> {
        val products = runBlocking(Dispatchers.IO) {
            productService.findByCatName(catName, page, size)
        }
        if (products.isEmpty()) throw Exception("products not found with this name")
        return buildResponse(
            "success",
            "product found",
            HttpStatus.OK,
            data = products
        )
    }

    @GetMapping("category/id/{catId}")
    fun getByCatId(
        @PathVariable catId: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<BaseResponse<Any>> {
        val products = runBlocking(Dispatchers.IO) {
            productService.getByCategoryId(catId, page, size)
        }
        if (products.isEmpty()) throw Exception("products not found with this id")
        return buildResponse(
            "success",
            "product found",
            HttpStatus.OK,
            data = products
        )
    }

    @PutMapping("update/{id}")
    fun updateProduct(@PathVariable id: String, @RequestBody product: Product): ResponseEntity<BaseResponse<Any>> {
        val prod = runBlocking {
            productService.updateProduct(id, product)
        } ?: throw ValidationException("product not exists")

        return buildResponse(
            "success",
            "product updated successfully",
            HttpStatus.CREATED,
            data = product
        )
    }
}