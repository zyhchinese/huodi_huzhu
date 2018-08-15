package com.lx.hd.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.lx.hd.R;

public class LoadListView extends ListView implements OnScrollListener {

	View footer;// 底部布局
	int totalItemCount;// ListView加载数据总量
	int lastVisibaleItem;// 底部显示的数据下标
	boolean isLoading;// 是否在加载
	OnLoaderListener loaderListener;// 加载监听

	public LoadListView(Context context) {
		super(context);
		initView(context);
	}

	public LoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public LoadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		/**
		 * scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动 ; scrollState =
		 * SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）; scrollState =
		 * SCROLL_STATE_IDLE(0) 停止滚动 ; 当滚动到最后一行,且停止滚动，加载数据;
		 */
		if (totalItemCount == lastVisibaleItem && scrollState == SCROLL_STATE_IDLE) {
			if (!isLoading) {
				isLoading = true;
				footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
				loaderListener.onLoad();// 加载更多
			}

		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
						 int totalItemCount) {
		/**
		 * 滚动一直回调直到停止滚动时才停止回调，单击时回调一次； 列表到达结尾之前，我们要加载数据模块；
		 * firstVisibleItem表示当前屏幕显示的第一个listItem在整个listView里的位置(下标从0开始);
		 * visibleItemCount表示当前屏幕可见的listItem(部分显示的listItem也算)总数；
		 * totalItemCount表示listView里listItem总数。
		 */
		this.lastVisibaleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;

	}

	/**
	 * 底部进度调加载到listview
	 *
	 * @param context
	 */
	private void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.footer_layout, null);
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		this.addFooterView(footer);// 加到底部
		this.setOnScrollListener(this);// 监听滚动到底部
	}

	/**
	 * 加载完毕
	 */

	public void loadComplete() {
		isLoading = false;
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
	}

	public void setLoaderListener(OnLoaderListener loaderListener) {
		this.loaderListener = loaderListener;
	}

	// 加载更多回调接口
	public interface OnLoaderListener {
		public void onLoad();
	}
}
