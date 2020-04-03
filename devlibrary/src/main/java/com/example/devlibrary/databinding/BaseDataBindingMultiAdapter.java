package com.example.devlibrary.databinding;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Description:
 * Created by FQH on 2019/6/15.
 */
public abstract class BaseDataBindingMultiAdapter<T extends MultiItemEntity, B extends ViewDataBinding> extends BaseMultiItemQuickAdapter<T, BaseBindingViewHolder<B>> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseDataBindingMultiAdapter(List<T> data) {
        super(data);
    }


    @Override
    protected BaseBindingViewHolder<B> createBaseViewHolder(View view) {
        return new BaseBindingViewHolder<>(view);
    }

    @Override
    protected BaseBindingViewHolder<B> createBaseViewHolder(ViewGroup parent, int layoutResId) {
        B b = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        View view;
        if (b == null) {
            view = getItemView(layoutResId, parent);
        } else {
            view = b.getRoot();
        }
        BaseBindingViewHolder<B> holder = new BaseBindingViewHolder<>(view);
        holder.setBinding(b);
        return holder;
    }

    @Override
    protected void convert(BaseBindingViewHolder<B> helper, T item) {
        convert(helper.getBinding(), item);
        helper.getBinding().executePendingBindings();
    }

    protected abstract void convert(B b, T item);
}
