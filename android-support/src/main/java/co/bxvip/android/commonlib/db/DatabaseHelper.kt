package co.bxvip.android.commonlib.db

import android.database.sqlite.SQLiteDatabase
import co.bxvip.android.commonlib.db.utils.DBInnerUtils.Companion.DB_NAME
import co.bxvip.android.commonlib.db.utils.DBInnerUtils.Companion.DB_VERSION
import co.bxvip.android.commonlib.db.utils.DBInnerUtils.Companion.ctx
import co.bxvip.android.commonlib.db.utils.DBInnerUtils.Companion.dbInstance
import co.bxvip.tools.ACache
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import java.util.concurrent.atomic.AtomicInteger

/**
 * <pre>
 *     author: vic
 *     time  : 18-1-31
 * </pre>
 */
class DatabaseHelper : OrmLiteSqliteOpenHelper(ctx, ctx.externalCacheDir?.path + "/" + DB_NAME, null, DB_VERSION) {

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {

    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        try {
            var count = 0
            (oldVersion until newVersion).map {
                val updateString = ACache.get(ctx).getAsString("check-db-update-string-$it")// version:updateString;updateString;updateString
                if (updateString != null && updateString != "") {
                    val split = updateString.split(":")
                    val updateVersion = split[0]
                    val updateSplitStrings = split[0].split(";")
                    if (oldVersion < updateVersion.toInt()) {
                        count++
                        updateSplitStrings.map { value ->
                            try {
                                ACache.get(ctx).put("check-db-update-string-$it", "")
                                database?.execSQL(value)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
            if (count == 0) {
                val cursor = database?.rawQuery("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name", null)
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        do {
                            if (cursor.getString(0) != null)
                                database.execSQL("DROP TABLE IF EXISTS ${cursor.getString(0)}")
                        } while (cursor.moveToNext())
                    }
                }
                cursor?.close()
                onCreate(database, connectionSource)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val counter = AtomicInteger() // 引用计数器
    private var db: SQLiteDatabase? = null

    fun <T> use(f: SQLiteDatabase.() -> T): T? {
        try {
            return openDatabase()?.f()
        } finally {
            closeDatabase()
        }
    }

    @Synchronized
    private fun openDatabase(): SQLiteDatabase? {
        if (counter.incrementAndGet() == 1) {
            db = writableDatabase
        }
        return db
    }

    @Synchronized
    private fun closeDatabase() {
        if (counter.decrementAndGet() == 0) {
            db?.close()
        }
    }

//    /**
//     * 增加字段
//     * @param table 表
//     * @param columnName 增加列名
//     * @param columnType 列类型
//     * @param defaultValue 默认值
//     */
//    fun addColumn(table: Class<*>, columnName: String, columnType: Any, defaultValue: Any) {
//        try {
//            val column: String
//            var value = defaultValue.toString()
//            if (columnType === String::class.java || columnType === Date::class.java) {
//                column = "TEXT"
//                value = "'" + defaultValue.toString() + "'"
//            } else if (columnType === Long::class.java || columnType === Int::class.java) {
//                column = "INTEGER"
//            } else if (columnType === Boolean::class.java) {
//                column = "NUMERIC"
//            } else if (columnType === Double::class.java || columnType === Float::class.java) {
//                column = "REAL"
//            } else {
//                column = "TEXT"
//                value = "'" + defaultValue.toString() + "'"
//            }
//            val sql = ("ALTER TABLE '" + table.simpleName.toLowerCase()
//                    + "' ADD COLUMN " + columnName + " " + column + " DEFAULT " + value + ";")
//            getDao(table).executeRaw(sql)
//        } catch (e: SQLException) {
//            e.printStackTrace()
//        }
//
//    }
}

val database: DatabaseHelper
    get() = dbInstance