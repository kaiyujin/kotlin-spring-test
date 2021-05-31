package com.kaiyujin.testsample

import com.kaiyujin.testsample.db.Child
import com.kaiyujin.testsample.db.SequenceRepository
import com.kaiyujin.testsample.exception.HTTPNotFoundException
import com.kaiyujin.testsample.service.MyService
import com.kaiyujin.testtool.DbTestUtils
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDate
import javax.sql.DataSource

@SpringBootTest
class MyServiceSpec(
    myService: MyService,
    sequenceRepository: SequenceRepository,
    dataSource: DataSource
) : FunSpec({

    context("Standardized Tests of Computation") {
        test("Public method test") {
            myService.plus(1, 2) shouldBe 3
            myService.plus(1000, -1) shouldBe 999
        }

        test("Private method test") {
            ReflectionTestUtils.invokeMethod<Int>(myService, "privatePlus", 1, 2) shouldBe 3
        }
    }

    test("Mock test") {
        mockkStatic(LocalDate::class)
        every { LocalDate.now() } returns LocalDate.of(2021, 5, 10)
        myService.tomorrow() shouldBe LocalDate.of(2021, 5, 11)
    }

    test("Property test") {
        myService.getDatasource() shouldBe "jdbc:postgresql://localhost:45432/unit_sample"
    }

    context("Database test") {
        test("Simple test") {
            DbSetup(
                DataSourceDestination(dataSource),
                Operations.sequenceOf(
                    Operations.deleteAllFrom("SAMPLE"),
                    Operations.insertInto("sample")
                        .columns("id", "name")
                        .values(1, "unittest1")
                        .values(2, "unittest2")
                        .build()
                )
            ).launch()
            myService.findSampleById(1).name shouldBe "unittest1"
            shouldThrow<HTTPNotFoundException> {
                myService.findSampleById(0)
            }
        }

        test("Foreign key & Sequence test") {
            DbSetup(
                DataSourceDestination(dataSource),
                Operations.sequenceOf(
                    Operations.sql(DbTestUtils.disableTrigger("child")),
                    Operations.deleteAllFrom("sample", "child")
                )
            ).launch()

            sequenceRepository.resetSeq("child_id_seq")
            myService.insertChild(Child(id = null, parentId = 901, name = "X1"))
            myService.insertChild(Child(id = null, parentId = 902, name = "X2"))
            myService.findChildById(1).name shouldBe "X1"
            myService.findChildById(2).name shouldBe "X2"

            // Cleaning
            DbSetup(
                DataSourceDestination(dataSource),
                Operations.sequenceOf(
                    Operations.deleteAllFrom("child"),
                    Operations.sql(DbTestUtils.enableTrigger("child")),
                )
            ).launch()
        }
    }
})
