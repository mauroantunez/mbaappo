package com.app.mbaappo.mbaappo.FirebaseUI;

/**
 * Created by Antunez on 16/6/2017.
 */

import android.support.annotation.RestrictTo;

/**
 * Convenience class for checking argument conditions.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class Preconditions {
    public static <T> T checkNotNull(T o) {
        if (o == null) throw new IllegalArgumentException("Argument cannot be null.");
        return o;
    }}
