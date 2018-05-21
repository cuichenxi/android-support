package android.support.annotation;

/**
 * @author vic Zhou
 * @time 2018-3-26 0:32
 */

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Typedef for the {@link VisibleForTesting#otherwise} attribute.
 *
 * @hide
 */
@IntDef({VisibleForTesting.PRIVATE,
        VisibleForTesting.PACKAGE_PRIVATE,
        VisibleForTesting.PROTECTED,
        VisibleForTesting.NONE}
// Important: If updating these constants, also update
// ../../../../external-annotations/android/support/annotation/annotations.xml
)
@Retention(SOURCE)
@interface ProductionVisibility {
}
