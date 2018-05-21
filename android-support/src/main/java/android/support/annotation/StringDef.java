
package android.support.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes that the annotated String element, represents a logical
 * type and that its value should be one of the explicitly named constants.
 * <p>
 * Example:
 * <pre><code>
 *  &#64;Retention(SOURCE)
 *  &#64;StringDef({
 *     POWER_SERVICE,
 *     WINDOW_SERVICE,
 *     LAYOUT_INFLATER_SERVICE
 *  })
 *  public @interface ServiceName {}
 *  public static final String POWER_SERVICE = "power";
 *  public static final String WINDOW_SERVICE = "window";
 *  public static final String LAYOUT_INFLATER_SERVICE = "layout_inflater";
 *  ...
 *  public abstract Object getSystemService(@ServiceName String name);
 * </code></pre>
 */
@Retention(SOURCE)
@Target({ANNOTATION_TYPE})
public @interface StringDef {
    /** Defines the allowed constants for this element */
    String[] value() default {};
}
