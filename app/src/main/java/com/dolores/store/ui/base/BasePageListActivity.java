package com.dolores.store.ui.base;

import com.android.volley.Request;
import com.dolores.store.http.HttpClient;

/**
 * Created by sheng on 18/3/27.
 */

public abstract class BasePageListActivity<T> extends BaseActivity {

    public abstract void processData(T response);

}
