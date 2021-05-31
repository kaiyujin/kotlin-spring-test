package com.kaiyujin.testsample.db

import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.Select
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.Result
import org.springframework.stereotype.Repository

@ConfigAutowireable
@Dao
@Repository
interface ChildRepository {
    @Insert
    fun insert(child: Child): Result<Child>

    @Select
    fun findById(id: Int): Child?
}
