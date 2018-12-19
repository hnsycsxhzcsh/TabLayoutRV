package com.tablayoutrv;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tablayoutrv.bean.ParentCategory;
import com.tablayoutrv.bean.RvData;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Gson mGson = new Gson();
    private static final int SPAN = 2;
    private static final int RV_HEADER_COUNT = 2;

    private RecyclerView mRvHome;
    private TabLayout mTlMain;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private RvAdapter mHomeSecondRvAdapter;
    private ArrayList<RvData> mRvDatas;
    private RvAdapter.TabLayoutViewHolder mHolderTabLayout;
    private ArrayList<ParentCategory> mParentCategoryList;
    private LinearLayout mLlMainTablayout;
    private boolean stopChange = false;
    private boolean stopScroll = false;
    private int[] mLocationTitleTab = new int[2];
    private int[] mLocationTitle = new int[2];
    private int[] mLocationRvTab = new int[2];
    private int titleX = 0;
    private int titleY = 0;
    private int rvTabX = 0;
    private int rvTabY = 0;
    private LinearLayout mLlHomeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvHome = findViewById(R.id.rv_list);
        mTlMain = findViewById(R.id.tl_home);
        mLlMainTablayout = findViewById(R.id.ll_main_tablayout);
        mLlHomeTitle = findViewById(R.id.ll_home_title);

        //测试数据
        getData();
        //初始化RecyclerView
        initRv();
    }

    public void initRvTabLayout(RvAdapter.TabLayoutViewHolder holderTabLayout) {
        //判断现在的数据和之前的TabLayout中的数据是否相同
        if (isEqualTabValue(holderTabLayout)) {
            return;
        }
        this.mHolderTabLayout = holderTabLayout;

        mHolderTabLayout.mTlHome.setSelectedTabIndicatorColor(Color.rgb(255, 0, 0));
        mTlMain.setSelectedTabIndicatorColor(Color.rgb(255, 0, 0));

        if (mHolderTabLayout.mTlHome.getTabCount() > 0) {
            mHolderTabLayout.mTlHome.removeAllTabs();
        }

        for (int i = 0; i < mParentCategoryList.size(); i++) {
            mHolderTabLayout.mTlHome.addTab(mHolderTabLayout.mTlHome.newTab().
                    setText(mParentCategoryList.get(i).getName()), i);
        }

        if (mTlMain.getTabCount() > 0) {
            mTlMain.removeAllTabs();
        }

        for (int i = 0; i < mParentCategoryList.size(); i++) {
            mTlMain.addTab(mTlMain.newTab().
                    setText(mParentCategoryList.get(i).getName()), i);
        }

        //两次监听会有异常，添加监听前先删除
        mHolderTabLayout.mTlHome.removeOnTabSelectedListener(holderListener);
        mHolderTabLayout.mTlHome.addOnTabSelectedListener(holderListener);

        mTlMain.removeOnTabSelectedListener(mainTabListener);
        mTlMain.addOnTabSelectedListener(mainTabListener);

        mRvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                RecyclerView.SCROLL_STATE_IDLE     滑动停止
//                RecyclerView.SCROLL_STATE_DRAGGING     手动正在滑动进行时
//                RecyclerView.SCROLL_STATE_SETTLING     惯性滑动进行时
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                getTitleXY();
                getRvTabXY();
                int position = findFirstVisibleItem();
                if (rvTabY > 0) {
                    int height = mLlHomeTitle.getHeight();
                    if (rvTabY <= titleY + height) {
                        mLlMainTablayout.setVisibility(View.VISIBLE);
                    } else {
                        mLlMainTablayout.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (position >= RV_HEADER_COUNT - 1) {
                        mLlMainTablayout.setVisibility(View.VISIBLE);
                    } else if (position <= RV_HEADER_COUNT - 2) {
                        mLlMainTablayout.setVisibility(View.INVISIBLE);
                    }
                }

                if (stopScroll) {
                    stopScroll = false;
                    return;
                }

                if (position >= 6) {
                    RvData bean = mRvDatas.get(position - RV_HEADER_COUNT);
                    String shortName = bean.getParentcategory().getName();
                    int selectedTabPosition = mTlMain.getSelectedTabPosition();
                    String tabText = (String) mTlMain.getTabAt(selectedTabPosition).getText();
                    if (!shortName.equals(tabText)) {
                        for (int i = 0; i < mParentCategoryList.size(); i++) {
                            String name = mParentCategoryList.get(i).getName();
                            if (shortName.equals(name)) {
                                stopChange = true;
                                mTlMain.getTabAt(i).select();
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    //找到第一个可见的item的位置
    private int findFirstVisibleItem() {
        int aa[] = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(null);
        if (aa != null) {
            return aa[0];
        } else {
            return -1;
        }
    }

    private void getTitleXY() {
        mLlHomeTitle.getLocationOnScreen(mLocationTitle);
        titleX = mLocationTitle[0];
        titleY = mLocationTitle[1];
    }

    private void getRvTabXY() {
        if (mHolderTabLayout != null) {
            mHolderTabLayout.mLlTlHome.getLocationOnScreen(mLocationRvTab);
            rvTabX = mLocationRvTab[0];
            rvTabY = mLocationRvTab[1];
        }
    }

    //recyclerview中的tablayout监听
    private TabLayout.OnTabSelectedListener holderListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            mTlMain.getTabAt(position).select();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    //页面中的tablayout的监听
    private TabLayout.OnTabSelectedListener mainTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            //当页面中的tablayout被选中时
            //获取现在的tab选中位置
            int position = tab.getPosition();
            //使RecyclerView中的tab也切换到相同的一个位置
            mHolderTabLayout.mTlHome.getTabAt(position).select();
            //如果当前tab的位置不是第一个，那么
            if (position != 0) {
                mLlMainTablayout.setVisibility(View.VISIBLE);
            }

            if (stopChange) {
                stopChange = false;
                return;
            }
            String text = (String) mTlMain.getTabAt(position).getText();
            for (int i = 0; i < mRvDatas.size(); i++) {
                RvData bean = mRvDatas.get(i);
                if (text.equals(bean.getParentcategory().getName())) {
                    stopScroll = true;
                    mStaggeredGridLayoutManager.scrollToPositionWithOffset
                            (RV_HEADER_COUNT + i, mLlMainTablayout.getHeight());
                    break;
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    //初始化RecyclerView
    private void initRv() {
        //流式布局初始化RecyclerView
        mStaggeredGridLayoutManager = new
                StaggeredGridLayoutManager(SPAN, StaggeredGridLayoutManager.VERTICAL);
        mRvHome.setLayoutManager(mStaggeredGridLayoutManager);

        mHomeSecondRvAdapter = new RvAdapter(this, RV_HEADER_COUNT, mRvDatas);
        mRvHome.setAdapter(mHomeSecondRvAdapter);
        mRvHome.addItemDecoration(new SpaceGridItemDecoration(getResources().
                getDimensionPixelOffset(R.dimen.item_homesecond_recycler_pading),
                SPAN, RV_HEADER_COUNT));
    }

    //测试数据
    private void getData() {
        //recyclerview 的数据
        String dataStr = getResources().getString(R.string.rvdata);
        Type collectionType = new TypeToken<ArrayList<RvData>>() {
        }.getType();
        mRvDatas = mGson.fromJson(dataStr, collectionType);

        //tablayout 的数据
        String tbdataStr = getResources().getString(R.string.tbdata);
        Type collectionType2 = new TypeToken<ArrayList<ParentCategory>>() {
        }.getType();
        mParentCategoryList = mGson.fromJson(tbdataStr, collectionType2);
    }

    //判断目前tablayout中的数据是否和最新的数据是否相同
    private boolean isEqualTabValue(RvAdapter.TabLayoutViewHolder holderTabLayout) {
        boolean isEqual = true;
        int tabCount = holderTabLayout.mTlHome.getTabCount();
        if (tabCount == 0 || tabCount != mParentCategoryList.size()) {
            isEqual = false;
        } else {
            for (int i = 0; i < mParentCategoryList.size(); i++) {
                String name = mParentCategoryList.get(i).getName();
                String tabName = holderTabLayout.mTlHome.getTabAt(i).getText().toString();
                if (!name.equals(tabName)) {
                    isEqual = false;
                    break;
                }
            }
        }
        return isEqual;
    }
}
