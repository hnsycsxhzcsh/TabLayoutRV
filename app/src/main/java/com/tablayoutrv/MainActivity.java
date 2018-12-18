package com.tablayoutrv;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvHome = findViewById(R.id.rv_list);
        mTlMain = findViewById(R.id.tl_home);

        //测试数据
        getData();
        //初始化RecyclerView
        initRv();


    }

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

    public void initRvTabLayout(RvAdapter.TabLayoutViewHolder holderTabLayout) {
        //判断现在的数据和之前的TabLayout中的数据是否相同
        if (isEqualTabValue(holderTabLayout)) {
            return;
        }
        this.mHolderTabLayout = holderTabLayout;

        mHolderTabLayout.mTlHome.setSelectedTabIndicatorColor(Color.rgb(244, 201, 57));
        mTlMain.setSelectedTabIndicatorColor(Color.rgb(244, 201, 57));

        if (mHolderTabLayout.mTlHome.getTabCount() > 0) {
            mHolderTabLayout.mTlHome.removeAllTabs();
        }

        for (int i = 0; i < mParentCategoryList.size(); i++) {
            mHolderTabLayout.mTlHome.addTab(mHolderTabLayout.mTlHome.newTab().
                    setText(mParentCategoryList.get(i).getShortName()), i);
        }

        if (mTlMain.getTabCount() > 0) {
            mTlMain.removeAllTabs();
        }

        for (int i = 0; i < mParentCategoryList.size(); i++) {
            mTlMain.addTab(mTlMain.newTab().
                    setText(mParentCategoryList.get(i).getShortName()), i);
        }

//        //两次监听会有异常，添加监听前先删除
//        mHolderTabLayout.mTlHome.removeOnTabSelectedListener(holderListener);
//        mHolderTabLayout.mTlHome.addOnTabSelectedListener(holderListener);
//
//        mTlMain.removeOnTabSelectedListener(mainTabListener);
//        mTlMain.addOnTabSelectedListener(mainTabListener);


    }

    private boolean isEqualTabValue(RvAdapter.TabLayoutViewHolder holderTabLayout) {
        boolean isEqual = true;
        int tabCount = holderTabLayout.mTlHome.getTabCount();
        if (tabCount == 0 || tabCount != mParentCategoryList.size()) {
            isEqual = false;
        } else {
            for (int i = 0; i < mParentCategoryList.size(); i++) {
                String name = mParentCategoryList.get(i).getShortName();
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
