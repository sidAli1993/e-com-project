package com.sidalitech.order_service.common

import com.sidalitech.order_service.common.global_exception.NotFoundException
import com.sidalitech.order_service.common.global_exception.UnauthorizedException
import com.sidalitech.order_service.common.global_exception.ValidationException
import com.sidalitech.order_service.model.response_model.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Any>> {
        val errors = ex.bindingResult.allErrors.associate { 
            it.objectName to (it.defaultMessage ?: "Validation error")
        }
        return ResponseEntity(
            BaseResponse(
                status = "failed",
                message = "Validation failed",
                data = errors
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<BaseResponse<Any>> {
        return ResponseEntity(
            BaseResponse(
                status = "failed",
                message = ex.message ?: "An error occurred"
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<BaseResponse<Any>> {
        return ResponseEntity(
            BaseResponse(
                status = "failed",
                message = ex.message ?: "Validation error",
                data = null
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<BaseResponse<Any>> {
        return ResponseEntity(
            BaseResponse(
                status = "failed",
                message = ex.message ?: "Resource not found",
                data = null
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<BaseResponse<Any>> {
        return ResponseEntity(
            BaseResponse(
                status = "failed",
                message = ex.message ?: "Unauthorized access",
                data = null
            ),
            HttpStatus.UNAUTHORIZED
        )
    }

}