package android.support.v4.app;

/**
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
 * <p>
 * <pre>
 *     author: vic
 *     time  : 18-6-2
 *     desc  : ${END}
 * </pre>
 */
import static android.os.Build.VERSION.SDK_INT;

import android.app.AppOpsManager;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Helper for accessing features in {@link android.app.AppOpsManager}.
 */
public final class AppOpsManagerCompat {

    /**
     * Result from {@link #noteOp}: the given caller is allowed to
     * perform the given operation.
     */
    public static final int MODE_ALLOWED = 0;

    /**
     * Result from {@link #noteOp}: the given caller is not allowed to perform
     * the given operation, and this attempt should <em>silently fail</em> (it
     * should not cause the app to crash).
     */
    public static final int MODE_IGNORED = 1;

    /**
     * Result from {@link #noteOp}: the given caller should use its default
     * security check.  This mode is not normally used; it should only be used
     * with appop permissions, and callers must explicitly check for it and
     * deal with it.
     */
    public static final int MODE_DEFAULT = 3;

    private AppOpsManagerCompat() {}

    /**
     * Gets the app op name associated with a given permission.
     *
     * @param permission The permission.
     * @return The app op associated with the permission or null.
     */
    public static String permissionToOp(@NonNull String permission) {
        if (SDK_INT >= 23) {
            return AppOpsManager.permissionToOp(permission);
        } else {
            return null;
        }
    }

    /**
     * Make note of an application performing an operation.  Note that you must pass
     * in both the uid and name of the application to be checked; this function will verify
     * that these two match, and if not, return {@link #MODE_IGNORED}.  If this call
     * succeeds, the last execution time of the operation for this app will be updated to
     * the current time.
     * @param context Your context.
     * @param op The operation to note.  One of the OPSTR_* constants.
     * @param uid The user id of the application attempting to perform the operation.
     * @param packageName The name of the application attempting to perform the operation.
     * @return Returns {@link #MODE_ALLOWED} if the operation is allowed, or
     * {@link #MODE_IGNORED} if it is not allowed and should be silently ignored (without
     * causing the app to crash).
     * @throws SecurityException If the app has been configured to crash on this op.
     */
    public static int noteOp(@NonNull Context context, @NonNull String op, int uid,
                             @NonNull String packageName) {
        if (SDK_INT >= 23) {
            AppOpsManager appOpsManager = context.getSystemService(AppOpsManager.class);
            return appOpsManager.noteOp(op, uid, packageName);
        } else {
            return MODE_IGNORED;
        }
    }

    /**
     * Make note of an application performing an operation on behalf of another
     * application when handling an IPC. Note that you must pass the package name
     * of the application that is being proxied while its UID will be inferred from
     * the IPC state; this function will verify that the calling uid and proxied
     * package name match, and if not, return {@link #MODE_IGNORED}. If this call
     * succeeds, the last execution time of the operation for the proxied app and
     * your app will be updated to the current time.
     * @param context Your context.
     * @param op The operation to note.  One of the OPSTR_* constants.
     * @param proxiedPackageName The name of the application calling into the proxy application.
     * @return Returns {@link #MODE_ALLOWED} if the operation is allowed, or
     * {@link #MODE_IGNORED} if it is not allowed and should be silently ignored (without
     * causing the app to crash).
     * @throws SecurityException If the app has been configured to crash on this op.
     */
    public static int noteProxyOp(@NonNull Context context, @NonNull String op,
                                  @NonNull String proxiedPackageName) {
        if (SDK_INT >= 23) {
            AppOpsManager appOpsManager = context.getSystemService(AppOpsManager.class);
            return appOpsManager.noteProxyOp(op, proxiedPackageName);
        } else {
            return MODE_IGNORED;
        }
    }
}
