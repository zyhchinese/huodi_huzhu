package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.PrimaryNews;
import com.lx.hd.utils.TDevice;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    RequestOptions mOptions;
    PageBean<PrimaryNews> activesBeanPageBean;
    protected int mState = STATE_LOAD_MORE;
    public static final int STATE_NO_MORE = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_INVALID_NETWORK = 3;
    public static final int STATE_HIDE = 5;
    public static final int STATE_REFRESHING = 6;
    public static final int STATE_LOAD_ERROR = 7;
    public static final int STATE_LOADING = 8;
    private final int HEAD_VIEW = 1;
    public static final int VIEW_TYPE_FOOTER = -2;
    public static final int VIEW_TYPE_FOOTER1 = -1;
    FooterViewHolder footerViewHolder;
    OnClickListener onClickListener;

    public interface OnItemClickLitener {
        void onItemClick(int position, long itemId);//这个不是我的那个=吗

    }

    private OnItemClickLitener onItemClickListener;

    /**
     * 添加项点击事件
     *
     * @param onItemClickListener the RecyclerView item click listener
     */
    public void setOnItemClickListener(OnItemClickLitener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MainAdapter() {
    }

    public MainAdapter(Context mContext, PageBean<PrimaryNews> activesBeanPageBean) {
        this.mContext = mContext;
        this.activesBeanPageBean = activesBeanPageBean;

        float w = TDevice.getScreenWidth();
        float h = TDevice.getScreenHeight();
        float fW = 153 / 720 * w;
        float fH = 153 / 1280 * h;
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.icon_default)
                .fitCenter()
                .override((int) fW, (int) fH);
        initListener();
//        dm = new DisplayMetrics();
//        ((Activity) mContext).getWindowManager().getDefaultDisplay()
//                .getMetrics(dm);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_VIEW;
        } else if (position + 2 == getItemCount()) {
            return VIEW_TYPE_FOOTER1;
        } else if (position + 1 == getItemCount()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        if (viewType == HEAD_VIEW) {
//            if(activesBeanPageBean.getRows().size()!=0){
//                MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
//                        mContext).inflate(R.layout.layout_recyclerview_header, parent,
//                        false));
//                if (holder != null) {
//                    holder.itemView.setTag(holder);
//                    holder.itemView.setOnClickListener(onClickListener);
//                }
//                return holder;
//            }else {
//            MyViewHolder holder1 = new MyViewHolder(LayoutInflater.from(
//                    mContext).inflate(R.layout.layout_empty_view, parent,
//                    false));
//            return holder1;
//            }


//        } else
//        if (viewType == VIEW_TYPE_FOOTER1) {
//            if (activesBeanPageBean.getRows().size() != 0) {
//                return new FooterViewHolder1(LayoutInflater.from(
//                        mContext).inflate(R.layout.layout_news_list_footer, parent, false));
//            } else {
//                MyViewHolder holder1 = new MyViewHolder(LayoutInflater.from(
//                        mContext).inflate(R.layout.layout_empty_view, parent,
//                        false));
//                return holder1;
//            }

//        } else if (viewType == VIEW_TYPE_FOOTER) {
////            return new FooterViewHolder(LayoutInflater.from(
////                    mContext).inflate(R.layout.recycler_footer_view, parent, false));
//        } else {
        MyViewHolder1 holder = new MyViewHolder1(LayoutInflater.from(
                mContext).inflate(R.layout.item_main_recycler, parent,
                false));
        if (holder != null) {
            holder.itemView.setTag(holder);
            holder.itemView.setOnClickListener(onClickListener);
        }
        return holder;
    }
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof MyViewHolder) {
//            MyViewHolder viewHolder = (MyViewHolder) holder;
//        } else if (holder instanceof FooterViewHolder1) {
//        } else if (holder instanceof FooterViewHolder) {
//            FooterViewHolder fvh = (FooterViewHolder) holder;
//            footerViewHolder = fvh;
//            fvh.itemView.setVisibility(View.VISIBLE);
//            if (activesBeanPageBean.getRows().size() == 0) {
//                mState = STATE_NO_MORE;
//            }
//            switch (mState) {
//                case STATE_INVALID_NETWORK:
//                    fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_network_error));
//                    fvh.pb_footer.setVisibility(View.GONE);
//                    break;
//                case STATE_LOAD_MORE:
//                    fvh.tv_footer.setText("上滑加载更多");
//                    fvh.pb_footer.setVisibility(View.GONE);
//                    break;
//                case STATE_LOADING:
//                    fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_loading));
//                    fvh.pb_footer.setVisibility(View.VISIBLE);
//                    break;
//                case STATE_NO_MORE:
//                    fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_not_more));
//                    fvh.pb_footer.setVisibility(View.GONE);
//                    break;
//                case STATE_REFRESHING:
//                    fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_refreshing));
//                    fvh.pb_footer.setVisibility(View.GONE);
//                    break;
//                case STATE_LOAD_ERROR:
//                    fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_load_error));
//                    fvh.pb_footer.setVisibility(View.GONE);
//                    break;
//                case STATE_HIDE:
//                    fvh.itemView.setVisibility(View.GONE);
//                    break;
//            }
//        } else {
        MyViewHolder1 viewHolder = (MyViewHolder1) holder;
//        if (position == 1) {
//            viewHolder.viewLine.setVisibility(View.GONE);
//        } else {
//            viewHolder.viewLine.setVisibility(View.VISIBLE);
//        }
        if (position < activesBeanPageBean.getRows().size()) {
            Glide.with(mContext)
                    .load(Constant.BASE_URL + activesBeanPageBean.getRows().get(position).getFolder() + activesBeanPageBean.getRows().get(position).getAutoname())
                    .apply(mOptions)
                    .into(viewHolder.imgIcon);

            viewHolder.tvTitle.setText(activesBeanPageBean.getRows().get(position).getTitle());
            viewHolder.tvContent.setText(activesBeanPageBean.getRows().get(position).getContent());
            viewHolder.tvCreateTime.setText(activesBeanPageBean.getRows().get(position).getCreatetime());
        }
    }
//    }

    public void toLoad() {
        mState = STATE_LOAD_MORE;
        notifyDataSetChanged();
    }

    public void loadFail() {
        mState = STATE_LOAD_ERROR;
        notifyDataSetChanged();
    }

    public void isLoadMore(boolean b, int size) {
        if (b) {

            if (footerViewHolder != null) {
                mState = STATE_LOADING;
                notifyDataSetChanged();
            } else {

            }

        } else {
            if (footerViewHolder != null) {
                if (size == 0) {
                    mState = STATE_NO_MORE;
                } else {
                    mState = STATE_LOAD_MORE;
                }

//                footerViewHolder.tv_footer.setVisibility(View.GONE);
//                footerViewHolder.pb_footer.setVisibility(View.GONE);
                notifyDataSetChanged();
            }

        }
    }

    @Override
    public int getItemCount() {
        return activesBeanPageBean.getRows().size() ;
    }

    public void addAll(List<PrimaryNews> items) {
        if (items != null) {
            activesBeanPageBean.getRows().addAll(items);
            notifyItemRangeInserted(this.activesBeanPageBean.getRows().size(), items.size());
        }
    }

    /**
     * 初始化listener
     */
    private void initListener() {
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(int position, long itemId) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, itemId);
            }
        };

    }

    /**
     * 可以共用同一个listener，相对高效
     */
    public static abstract class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract void onClick(int position, long itemId);
    }


    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pb_footer;
        public TextView tv_footer;

        public FooterViewHolder(View view) {
            super(view);
            pb_footer = (ProgressBar) view.findViewById(R.id.pb_footer);
            tv_footer = (TextView) view.findViewById(R.id.tv_footer);
        }
    }




    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {
        public View viewLine;
        public ImageView imgIcon;
        public TextView tvTitle, tvContent, tvCreateTime;

        public MyViewHolder1(View view) {
            super(view);
            viewLine = itemView.findViewById(R.id.view_lines);
            imgIcon = (ImageView) view.findViewById(R.id.img_icon);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvContent = (TextView) view.findViewById(R.id.tv_conent);
            tvCreateTime = (TextView) view.findViewById(R.id.tv_time);
        }
    }



