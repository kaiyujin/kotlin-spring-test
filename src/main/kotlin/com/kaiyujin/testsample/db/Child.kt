package com.kaiyujin.testsample.db

import org.seasar.doma.Entity
import org.seasar.doma.GeneratedValue
import org.seasar.doma.GenerationType
import org.seasar.doma.Id
import org.seasar.doma.SequenceGenerator
import org.seasar.doma.jdbc.entity.NamingType
import java.io.Serializable

@Entity(immutable = true, naming = NamingType.SNAKE_LOWER_CASE)
data class Child(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(sequence = "child_id_seq")
    val id: Int?,
    val parentId: Int,
    val name: String
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
