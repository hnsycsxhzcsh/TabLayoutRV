package com.tablayoutrv;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tablayoutrv.bean.RvData;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by HARRY on 2018/12/18 0018.
 */

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_TABLAYOUT = 1;
    private static final int ITEM_TYPE_CONTENT = 2;

    private final MainActivity activity;
    private final int headerCount;
    private final ArrayList<RvData> mRvDatas;
    private final LayoutInflater mLayoutInflater;


    public RvAdapter(MainActivity mainActivity, int rvHeaderCount, ArrayList<RvData> mRvDatas) {
        this.activity = mainActivity;
        this.headerCount = rvHeaderCount;
        this.mRvDatas = mRvDatas;
        mLayoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == ITEM_TYPE_HEADER) {
            return ITEM_TYPE_HEADER;
        } else if (position == ITEM_TYPE_TABLAYOUT) {
            return ITEM_TYPE_TABLAYOUT;
        } else {
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.item_rv_header, parent, false));
        } else if (viewType == ITEM_TYPE_TABLAYOUT) {
            return new TabLayoutViewHolder(mLayoutInflater.inflate(R.layout.item_rv_tablayout, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new MainViewHolder(mLayoutInflater.inflate(R.layout.item_recycleritem_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            initHolder(holder);
        } else if (holder instanceof TabLayoutViewHolder) {
            initHolder(holder);
            TabLayoutViewHolder holderTabLayout = (TabLayoutViewHolder) holder;
            activity.initRvTabLayout(holderTabLayout);
        } else if (holder instanceof MainViewHolder) {
            MainViewHolder holderMain = (MainViewHolder) holder;
            RvData bean = mRvDatas.get(position - headerCount);

            int outPading = (int) activity.getResources().getDimension(R.dimen.item_homesecond_recycler_pading);
            int ivWidth = (getWindowWid(activity) - 3 * outPading ) / 2;
            ViewGroup.LayoutParams layoutParams = holderMain.mIv.getLayoutParams();
            layoutParams.height = ivWidth;
            holderMain.mIv.setLayoutParams(layoutParams);

//            String url = "http://img.sunsky-online.com/upload/store/product_l/" +bean.getProduct().getItemNo() + ".jpg";
//            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(holderMain.mIv);

            holderMain.mTvCate.setText(bean.getParentcategory().getName()+":"+bean.getCategory().getName());
        }
    }

    @Override
    public int getItemCount() {
        return headerCount + getContentItemCount();
    }

    private int getContentItemCount() {
        return mRvDatas.size();
    }

    public class TabLayoutViewHolder extends RecyclerView.ViewHolder {
        public TabLayout mTlHome;
        public LinearLayout mLlTlHome;

        public TabLayoutViewHolder(View itemView) {
            super(itemView);
            mTlHome = (TabLayout) itemView.findViewById(R.id.tl_home);
            mLlTlHome = (LinearLayout) itemView.findViewById(R.id.ll_home_second_tablayout);
        }
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIv;
        private TextView mTvCate;

        public MainViewHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv_homebottom_recycleritem);
            mTvCate = (TextView) itemView.findViewById(R.id.tv_category);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //StaggeredGridLayoutManager 模式下，设置一列的item占满全屏
    private void initHolder(RecyclerView.ViewHolder holder) {
        StaggeredGridLayoutManager.LayoutParams layoutParams =
                (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.setFullSpan(true);
    }

    public int getWindowWid(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

}
