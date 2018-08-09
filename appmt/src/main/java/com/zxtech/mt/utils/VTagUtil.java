package com.zxtech.mt.utils;

import android.view.View;

/**
 * Created by syp616 on 2017/5/25.
 */

public class VTagUtil {
    public static void setTag(Object tag, View childView) {
        if (childView != null) {
            childView.setTag(tag);
        }

    }

    public static Object getTag(View childView) {
        return childView != null ? childView.getTag() : null;
    }

    public static boolean isDifferentTag(Object tag, View childView) {
        if (tag != null && tag.equals(getTag(childView))) {
            return false;
        } else {
            setTag(tag, childView);
            return true;
        }
    }
}
