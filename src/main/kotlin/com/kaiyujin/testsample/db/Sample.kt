package com.kaiyujin.testsample.db

import org.seasar.doma.Entity
import org.seasar.doma.Id
import org.seasar.doma.jdbc.entity.NamingType
import java.io.Serializable

@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
data class Sample(
    @Id
    val id: Int,
    val name: String
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
