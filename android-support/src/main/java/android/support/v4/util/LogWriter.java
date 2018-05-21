package android.support.v4.util;

import android.support.annotation.RestrictTo;
import android.util.Log;

import java.io.Writer;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * Helper for accessing features in {@link android.util.LogWriter}.
 *
 * @hide
 */
@RestrictTo(LIBRARY_GROUP)
public class LogWriter extends Writer {
    private final String mTag;
    private StringBuilder mBuilder = new StringBuilder(128);

    /**
     * Create a new Writer that sends to the log with the given priority
     * and tag.
     *
     * @param tag A string tag to associate with each printed log statement.
     */
    public LogWriter(String tag) {
        mTag = tag;
    }

    @Override public void close() {
        flushBuilder();
    }

    @Override public void flush() {
        flushBuilder();
    }

    @Override public void write(char[] buf, int offset, int count) {
        for(int i = 0; i < count; i++) {
            char c = buf[offset + i];
            if ( c == '\n') {
                flushBuilder();
            }
            else {
                mBuilder.append(c);
            }
        }
    }

    private void flushBuilder() {
        if (mBuilder.length() > 0) {
            Log.d(mTag, mBuilder.toString());
            mBuilder.delete(0, mBuilder.length());
        }
    }
}
