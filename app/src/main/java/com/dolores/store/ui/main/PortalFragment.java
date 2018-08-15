package com.dolores.store.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dolores.store.R;
import com.dolores.store.lightapp.runtime.tencentX5.X5WebViewC;
import com.dolores.store.util.LogUtils;

/**
 * @author shengfq.
 * 首页页签-门户
 *         portal of APP 注意该页面加载的UI资源比较多，能使用缓存最好。
 *         具体可以使用view单例。
 */
public class PortalFragment extends Fragment {
    private final String TAG = "PortalFragment";
    private static final String mHomeUrl = "http://www.vanke.com";
    private X5WebViewC mX5WebView;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LogUtils.d("onCreateView");
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_portal, container, false);
            initHardwareAccelerate();
            initView(mView);
        }
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * 启用硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getActivity().getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

    private void initView(View view) {
        mX5WebView = (com.dolores.store.lightapp.runtime.tencentX5.X5WebViewC) view.findViewById(R.id.x5_webview);
        mX5WebView.loadUrl(mHomeUrl);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        //释放资源
        if (mX5WebView != null)
            mX5WebView.destroy();
        super.onDestroy();
    }
}
