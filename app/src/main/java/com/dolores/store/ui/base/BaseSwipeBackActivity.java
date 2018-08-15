package com.dolores.store.ui.base;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.PersistableBundle;
import com.dolores.store.util.LogUtils;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * @author by sheng on 18/4/18.
 * 滑动返回activity,适用于activity栈顶展示
 */

public class BaseSwipeBackActivity extends SwipeBackActivity {
    private final String TAG=BaseSwipeBackActivity.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        LogUtils.d(TAG,"onCreate");
    }


    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return super.getSwipeBackLayout();
    }
}
