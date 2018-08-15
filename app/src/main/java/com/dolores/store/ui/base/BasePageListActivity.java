package com.dolores.store.ui.base;

/**
 * Created by sheng on 18/3/27.
 */

public abstract class BasePageListActivity<T> extends BaseActivity {

    public abstract void processData(T response);

}
