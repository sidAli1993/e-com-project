package com.sidalitech.products_service.utils

object JwtContextHolder {
    private var tokenHolder: String?=null

    fun setToken(token: String) {
        tokenHolder=token
    }

    fun getToken(): String? {
        val localToken= tokenHolder
        clear()
        return localToken
    }

    fun clear() {
        tokenHolder=null
    }
}
