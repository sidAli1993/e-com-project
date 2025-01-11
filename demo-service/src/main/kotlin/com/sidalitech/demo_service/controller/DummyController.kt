package com.sidalitech.demo_service.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class DummyController {

    @GetMapping("/demo")
    fun welcome(@RequestHeader("X-Username")username:String,@RequestHeader("X-User-Roles") roles:List<String>):ResponseEntity<Any>{
        return ResponseEntity.ok("welcome to sid ali technologies $username with roles :$roles")
//        return ResponseEntity.ok("welcome to sid ali technologies")
    }

    @GetMapping("/admin")
    fun adminEndpoint(): ResponseEntity<String> {
        return ResponseEntity.ok("Welcome, Admin!")
    }

    @GetMapping("/user")
    fun userEndpoint(): ResponseEntity<String> {
        return ResponseEntity.ok("Welcome, User!")
    }
}