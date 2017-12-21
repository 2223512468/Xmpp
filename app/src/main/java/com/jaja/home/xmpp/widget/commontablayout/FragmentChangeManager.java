package com.jaja.home.xmpp.widget.commontablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

public class FragmentChangeManager {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    /** Fragment切换数组 */
    private ArrayList<Fragment> mFragments;
    private ArrayList<Boolean> mIsAdd;
    /** 当前选中的Tab */
    private int mCurrentTab;

    public FragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<Fragment> fragments) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
        mIsAdd=new ArrayList<>();
        initFragments();
    }

    /** 初始化fragments */
    private void initFragments() {
        for (Fragment fragment : mFragments) {
            mIsAdd.add(false);
        }
        setFragments(0);
    }

    /** 界面切换控制 */
    public void setFragments(int index) {
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            if (i == index) {
                if(mIsAdd.get(i)){
                    ft.show(fragment);
                }else {
                    mIsAdd.set(i,true);
                    mFragmentManager.beginTransaction().add(mContainerViewId, fragment).commitAllowingStateLoss();
                }

            } else {
                if(mIsAdd.get(i)){
                    ft.hide(fragment);
                }
            }
            ft.commitAllowingStateLoss();
        }
        mCurrentTab = index;
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public Fragment getCurrentFragment() {
        return mFragments.get(mCurrentTab);
    }
}