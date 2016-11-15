package com.ql.mynews.tab;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ql.mynews.R;
import com.ql.mynews.adapter.FragmentAdapter;
import com.ql.mynews.fragmentCustom.BounceScrollView;
import com.ql.mynews.fragmentCustom.ViewPagerIndicator;
import com.yuinet.Taoind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTABActivity extends FragmentActivity {
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private List<String> mDatas = Arrays.asList("今日头条", "娱乐", "财经", "时尚", "体育",
            "国际", "国内", "军事", "社会","科技");

    private ViewPagerIndicator mIndicator;
    private BounceScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置状态栏为透明
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.tab_main);
        initGG();
        initView();
        initDatas();
        mIndicator.setTabItemTitles(mDatas);
        mIndicator.setViewPager(mViewPager, mScrollView, 0);

    }

    private void  initGG(){
        Taoind pm = Taoind.getInstance(getApplicationContext(),"67643a07cf459880a9160c7342a14ad6");
        pm.w(10, 0);//外弹间隔10分钟,首次无延迟

        //内弹,浮窗,外弹,应用外退弹广告配置 1,开启, 0关闭
        pm.s(0,1,1,1);//激活插屏广告
    }
    /*
    ,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    private void initDatas() {
        // TODO Auto-generated method stub
        mTabContents.add(new Fragment1());
        mTabContents.add(new Fragment2());
        mTabContents.add(new Fragment3());
        mTabContents.add(new Fragment4());
        mTabContents.add(new Fragment5());
        mTabContents.add(new Fragment6());
        mTabContents.add(new Fragment7());
        mTabContents.add(new Fragment8());
        mTabContents.add(new Fragment9());
        mTabContents.add(new Fragment10());
        FragmentAdapter a = new FragmentAdapter(getSupportFragmentManager(), mTabContents);
        mViewPager.setAdapter(a);
        mViewPager.setOffscreenPageLimit(1);
    }

    private void initView() {
        mScrollView = (BounceScrollView) findViewById(R.id.id_scrollview);
        mViewPager = (ViewPager) findViewById(R.id.id_page_vp);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Taoind pm = Taoind.getInstance(getApplicationContext(),"67643a07cf459880a9160c7342a14ad6");
            pm.e(MainTABActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}