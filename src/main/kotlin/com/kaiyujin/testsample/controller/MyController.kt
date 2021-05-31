package com.kaiyujin.testsample

import com.kaiyujin.testsample.db.Sample
import com.kaiyujin.testsample.service.MyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(
    val myService: MyService
) {
    @GetMapping("/")
    fun check(): ResponseEntity<Any> {
        myService.plus(2, 3)
        println(myService.getDatasource())
        return ResponseEntity("{ \"status\": \"OK\" }", HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getSample(@PathVariable("id") id: Int): Sample {
        return myService.findSampleById(id)
    }
}
