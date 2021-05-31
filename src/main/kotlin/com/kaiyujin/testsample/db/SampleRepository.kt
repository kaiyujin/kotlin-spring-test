package com.kaiyujin.testsample.db

import org.seasar.doma.Dao
import org.seasar.doma.Select
import org.seasar.doma.boot.ConfigAutowireable
import org.springframework.stereotype.Repository

@ConfigAutowireable
@Dao
@Repository
interface SampleRepository {
    @Select
    fun findById(id: Int): Sample?
}
