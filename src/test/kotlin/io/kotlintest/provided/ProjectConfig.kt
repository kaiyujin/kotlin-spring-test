package io.kotlintest.provided

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.test.AssertionMode
import io.kotest.extensions.spring.SpringExtension

class ProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringExtension)
    override val assertionMode = AssertionMode.Warn
}
