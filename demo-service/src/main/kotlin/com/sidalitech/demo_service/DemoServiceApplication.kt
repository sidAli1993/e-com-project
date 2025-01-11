package com.sidalitech.demo_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoServiceApplication

fun main(args: Array<String>) {
	runApplication<DemoServiceApplication>(*args)
}
