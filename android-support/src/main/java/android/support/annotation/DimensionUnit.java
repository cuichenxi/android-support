package android.support.annotation;

/**
 * @author vic Zhou
 * @time 2018-3-26 0:31
 * @des ${TODO}
 */

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Typedef for the {@link Dimension#unit} attribute.
 *
 * @hide
 */
@IntDef({Dimension.PX,
        Dimension.DP,
        Dimension.SP}
// Important: If updating these constants, also update
// ../../../../external-annotations/android/support/annotation/annotations.xml
)
@Retention(SOURCE)
@interface DimensionUnit {
}
