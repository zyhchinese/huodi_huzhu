package com.lx.hd.nav;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.lx.hd.R;
import com.lx.hd.base.fragment.BaseFragment;
import com.lx.hd.ui.fragment.HuoYuanKuaiYunFragment;
import com.lx.hd.ui.fragment.OrderFragment;
import com.lx.hd.ui.fragment.PrimaryFragment;
import com.lx.hd.ui.fragment.QZFragment;
import com.lx.hd.ui.fragment.ShoppingMainFragment;
import com.lx.hd.ui.fragment.UserFragment;
import com.lx.hd.ui.fragment.testFragment;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavFragment extends BaseFragment implements View.OnClickListener {
    NavigationButton mNavPrimary;
    NavigationButton mNav1;
    NavigationButton mNav2;
    NavigationButton mNavMe;

    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    private NavigationButton mCurrentNavButton;
    private OnNavigationReselectListener mOnNavigationReselectListener;

    public NavFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

//        ShapeDrawable lineDrawable = new ShapeDrawable(new BorderShape(new RectF(0, 1, 0, 0)));
//        lineDrawable.getPaint().setColor(getResources().getColor(R.color.list_divider_color));
//        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{
//                new ColorDrawable(getResources().getColor(R.color.white)),
//                lineDrawable
//        });
//        root.setBackgroundDrawable(layerDrawable);
        mNavPrimary = findView(R.id.nav_item_primary);
        mNav1 = findView(R.id.nav_item_tweet);
        mNav2 = findView(R.id.nav_item_explore);
        mNavMe = findView(R.id.nav_item_me);

        mNavPrimary.setOnClickListener(this);
        mNav1.setOnClickListener(this);
        mNav2.setOnClickListener(this);
        mNavMe.setOnClickListener(this);

        mNavPrimary.init(R.drawable.tab_icon_primary,
                R.string.main_tabmy_name_primary,
                PrimaryFragment.class);

//        mNav1.init(R.drawable.tab_icon_shop,
//                R.string.main_tab_name_1,
//                OrderFragment.class);
////                testFragment.class);

        mNav1.init(R.drawable.tab_icon_shop111,
                R.string.main_tab_name_1,
                HuoYuanKuaiYunFragment.class);
//                testFragment.class);

//        mNav2.init(R.drawable.tab_icon_find,
//                R.string.main_tab_name_2,
//                QZFragment.class);

        mNav2.init(R.drawable.tab_icon_shop,
                R.string.main_tab_name_2,
                OrderFragment.class);

        mNavMe.init(R.drawable.tab_icon_me,
                R.string.main_tab_name_my,
                UserFragment.class);

    }

    @Override
    public void onClick(View v) {
        if (v instanceof NavigationButton) {
            NavigationButton nav = (NavigationButton) v;
            doSelect(nav);
        } else if (v.getId() == R.id.nav_item_tweet_pub) {

        }
    }

    public void setup(Context context, FragmentManager fragmentManager, int contentId, OnNavigationReselectListener listener) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mContainerId = contentId;
        mOnNavigationReselectListener = listener;

        // do clear
        clearOldFragment();
        // do select first
        doSelect(mNavPrimary);
    }

    public void select(int index) {
        if (mNavMe != null)
            doSelect(mNavMe);
    }
    public void select1(int index) {
        if (mNav2 != null)
            doSelect(mNav2);
    }

    @SuppressWarnings("RestrictedApi")
    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (transaction == null || fragments == null || fragments.size() == 0)
            return;
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this && fragment != null) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit)
            transaction.commitNow();
    }

    private void doSelect(NavigationButton newNavButton) {
        // If the new navigation is me info fragment, we intercept it
        /*
        if (newNavButton == mNavMe) {
            if (interceptMessageSkip())
                return;
        }
        */

        NavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;
    }

    private void doTabChanged(NavigationButton oldNavButton, NavigationButton newNavButton) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(mContext,
                        newNavButton.getClx().getName(), null);
                ft.add(mContainerId, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }


    private void onReselect(NavigationButton navigationButton) {
        OnNavigationReselectListener listener = mOnNavigationReselectListener;
        if (listener != null) {
            listener.onReselect(navigationButton);
        }
    }

    public interface OnNavigationReselectListener {
        void onReselect(NavigationButton navigationButton);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
