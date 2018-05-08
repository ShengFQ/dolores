package com.dolores.store.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dolores.store.BuildConfig;
import com.dolores.store.R;
import com.dolores.store.adapter.PagerGridAdapter;
import com.dolores.store.lightapp.runtime.WebviewJump;
import com.dolores.store.util.LogUtils;
import com.dolores.store.widget.GlideImageLoader;
import com.gcssloop.widget.PagerConfig;
import com.gcssloop.widget.PagerGridLayoutManager;
import com.gcssloop.widget.PagerGridSnapHelper;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author shengfq.
 * portal of APP 注意该页面加载的UI资源比较多，能使用缓存最好。
 * 具体可以使用view单例。
 */
public class PortalFragment extends Fragment {
    private final String TAG="PortalFragment";
    List banner_images=new ArrayList();
    List<String> banner_titles=new ArrayList<String>();
    @Bind(R.id.banner_news)
    Banner banner_news;

    @Bind(R.id.recycler_view_pager)
    RecyclerView recyclerView_functional;

    @Bind(R.id.swipe_layout_refresh)
    SwipeRefreshLayout swipe_layout_refresh;

    PagerGridAdapter pagerGridAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_portal, container, false);
        ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initView();
       // initEvent();
    }
    private void init(){
        banner_images.add(getResources().getDrawable(R.drawable.video_main));
        banner_images.add(getResources().getDrawable(R.drawable.video_cate));
        banner_images.add(getResources().getDrawable(R.drawable.video_game));
        banner_titles.add(getResources().getString(R.string.banner_title_main));
        banner_titles.add(getResources().getString(R.string.banner_title_cate));
        banner_titles.add(getResources().getString(R.string.banner_title_game));
    }
    private void initView(){
        //设置banner样式
        banner_news.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner_news.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner_news.setImages(banner_images);
        //设置banner的动画效果
        banner_news.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合
        banner_news.setBannerTitles(banner_titles);
        //设置自动轮播，默认为true
        banner_news.isAutoPlay(true);
        //设置轮播时间
        banner_news.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner_news.setIndicatorGravity(BannerConfig.CENTER);
        //设置点击事件
        banner_news.setOnBannerListener(bannerListener);
        //banner设置方法全部调用完毕时最后调用
        banner_news.start();
        //////////////////////////////////////////
        // 1.水平分页布局管理器
        PagerGridLayoutManager layoutManager = new PagerGridLayoutManager(
                2, 3, PagerGridLayoutManager.VERTICAL);
        recyclerView_functional.setLayoutManager(layoutManager);

// 2.设置滚动辅助工具
        PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
        pageSnapHelper.attachToRecyclerView(recyclerView_functional);
        layoutManager.setPageListener(pageListener);
        layoutManager.setAllowContinuousScroll(true);//允许连续滚动
        layoutManager.setOrientationType(PagerGridLayoutManager.HORIZONTAL);//设置滚动方向
        PagerConfig.setShowLog(BuildConfig.DEBUG);
        pagerGridAdapter=new PagerGridAdapter();
        pagerGridAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
            @Override
            public void onChanged() {
                super.onChanged();
                int count=pagerGridAdapter.getItemCount();
                LogUtils.d(TAG,"item count:"+count);
            }
        });
        recyclerView_functional.setAdapter(pagerGridAdapter);
    }

    PagerGridLayoutManager.PageListener pageListener=new PagerGridLayoutManager.PageListener() {
        @Override
        public void onPageSizeChanged(int pageSize) {
            LogUtils.d(TAG, "总页数 = " + pageSize);
        }

        @Override
        public void onPageSelect(int pageIndex) {
            LogUtils.d(TAG, "当前页 = " + pageIndex);
        }
    };
    OnBannerListener bannerListener=new OnBannerListener() {
        @Override
        public void OnBannerClick(int position) {
            //position 0
            switch (position){
                case 0:
                    WebviewJump.gotoNewsWebviewActivity(getActivity(),"https://www.xiaomi.com","xiaomi",null);
                    break;
                case 1:
                    WebviewJump.gotoNewsWebviewActivity(getActivity(),"https://www.jd.com","jd",null);
                    break;
                case 2:
                    WebviewJump.gotoNewsWebviewActivity(getActivity(),"https://www.qq.com","qq",null);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onStart() {
        super.onStart();
        banner_news.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner_news.stopAutoPlay();
    }
}
