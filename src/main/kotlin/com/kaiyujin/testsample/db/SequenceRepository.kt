package com.kaiyujin.testsample.db

import org.seasar.doma.Dao
import org.seasar.doma.Select
import org.seasar.doma.Sql
import org.seasar.doma.boot.ConfigAutowireable
import org.springframework.stereotype.Repository

/**
 * テスト用にSequenceの値をリセットするメソッドを持つInterface
 *
 * DBSetupには値を取得するSQLを投げる機構がない。（Documentに明示的にデータ投入するものなのでないと記載）
 * PostgresqlはAlter sequenceは無効
 * 上記2点＋通常のRepositoryの中で都度定義すると間違って呼ばれた場合に事故となるので別interfaceとしている。
 * テスト用のDB実行がこれだけであれば＠SpringBootTest + datasource利用で更に安全性を高める方法もあり。
 */
@ConfigAutowireable
@Dao
@Repository
interface SequenceRepository {
    @Sql("select setval(/* sequenceName */'test', 1, false)")
    @Select
    fun resetSeq(sequenceName: String): Int
}
