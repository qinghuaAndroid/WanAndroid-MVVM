package com.example.devlibrary.widget;


/**
 * Created by BlingBling on 2016/10/11.
 */

public final class LoadMoreView extends com.chad.library.adapter.base.loadmore.LoadMoreView {

    @Override
    public int getLayoutId() {
        return com.chad.library.R.layout.brvah_quick_view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return com.chad.library.R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return com.chad.library.R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return com.chad.library.R.id.load_more_load_end_view;
    }
}
