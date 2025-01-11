plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.sidalitech"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2024.0.0"))
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
//	implementation("org.springframework.boot:spring-boot-starter-aop")
//	implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation ("org.springframework.security:spring-security-webflux")//	implementation ("io.jsonwebtoken:jjwt-api:0.11.5") // For JWT parsing
//	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")
//	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5") // For JSON parsing
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
