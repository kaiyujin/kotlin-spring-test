package com.kaiyujin.testtool

class DbTestUtils {

    companion object {

        fun resetSequenceSql(sequenceName: String): String {
            return "update flyway_schema_history set execution_time = (select setval('$sequenceName', 1, false)) where version = '0000'"
        }

        fun enableTrigger(tableName: String): String {
            return "ALTER TABLE $tableName ENABLE TRIGGER ALL"
        }

        fun disableTrigger(tableName: String): String {
            return "ALTER TABLE $tableName DISABLE TRIGGER ALL"
        }
    }
}
