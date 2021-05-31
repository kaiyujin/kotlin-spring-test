package com.kaiyujin.testsample.service

import com.kaiyujin.testsample.db.Child
import com.kaiyujin.testsample.db.ChildRepository
import com.kaiyujin.testsample.db.Sample
import com.kaiyujin.testsample.db.SampleRepository
import com.kaiyujin.testsample.exception.HTTPNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class MyService(
    val nestedMyService: NestedMyService,
    val sampleRepository: SampleRepository,
    val childRepository: ChildRepository
) {
    fun plus(n: Int, m: Int): Int {
        nestedMyService.hello()
        return n + m
    }

    private fun privatePlus(n: Int, m: Int): Int {
        return n + m
    }

    fun tomorrow(): LocalDate {
        return LocalDate.now().plusDays(1)
    }

    @Value("\${spring.datasource.url}")
    val dataSource: String? = null

    fun getDatasource(): String {
        return dataSource!!
    }

    fun findSampleById(id: Int): Sample {
        return sampleRepository.findById(id) ?: throw HTTPNotFoundException("not found sample.")
    }

    @Transactional
    fun insertChild(child: Child): Child {
        return childRepository.insert(child).entity
    }

    fun findChildById(id: Int): Child {
        return childRepository.findById(id) ?: throw HTTPNotFoundException("not found child.")
    }
}
