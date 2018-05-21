package co.bxvip.android.commonlib.db.utils

import co.bxvip.android.commonlib.db.DatabaseHelper
import co.bxvip.android.commonlib.utils.CommonInit
import co.bxvip.tools.ACache
import java.util.ArrayList

/**
 *
 * ┌───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│ │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│ ┌┐    ┌┐    ┌┐
 * └───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘ └┘    └┘    └┘
 * ┌──┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐┌───┬───┬───┐┌───┬───┬───┬───┐
 * │~`│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp ││Ins│Hom│PUp││N L│ / │ * │ - │
 * ├──┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤├───┼───┼───┤├───┼───┼───┼───┤
 * │Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ ││Del│End│PDn││ 7 │ 8 │ 9 │   │
 * ├────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤└───┴───┴───┘├───┼───┼───┤ + │
 * │Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │             │ 4 │ 5 │ 6 │   │
 * ├─────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤    ┌───┐    ├───┼───┼───┼───┤
 * │Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │    │ ↑ │    │ 1 │ 2 │ 3 │   │
 * ├────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤┌───┼───┼───┐├───┴───┼───┤ E││
 * │Ctrl│Ray │Alt │         Space         │ Alt│code│fuck│Ctrl││ ← │ ↓ │ → ││   0   │ . │←─┘│
 * └────┴────┴────┴───────────────────────┴────┴────┴────┴────┘└───┴───┴───┘└───────┴───┴───┘
 *
 * <pre>
 *     author: vic
 *     time  : 18-5-20
 *     desc  : ${END}
 * </pre>
 */

class DBInnerUtils private constructor() {
    companion object {
        val ctx by lazy {
            CommonInit.ctx
        }

        val aCache by lazy {
            ACache.get(ctx)
        }

        val DB_VERSION by lazy {
            var version = aCache.getAsString("db-version-string")
            if (version != null && version != "") {
                try {
                    version.toInt()
                } catch (e: Exception) {
                    version = null
                }
            }
            if (version == null || version == "") {
                version = "1"
                aCache.put("db-version-string", version)
            }
            version.toInt()
        }

        val DB_NAME by lazy {
            var name = aCache.getAsString("db-name-string")
            if (name == null || name == "") {
                name = "ormlite-db-date.db"
                aCache.put("db-name-string", name)
            }
            name
        }

        val dbInstance by lazy {
            DatabaseHelper()
        }

        val TABLE_LIST by lazy {
            var res = ArrayList<String>()
            var tables = aCache.getAsObject("db-table-array-arraylist")
            try {
                if (tables != null)
                    res.addAll(tables as ArrayList<String>)
            } catch (e: Exception) {

            }
            res
        }

        val showDBLog by lazy {
            val debug = aCache.getAsObject("db-log-debug")
            debug != null && debug == "true"
        }
    }
}