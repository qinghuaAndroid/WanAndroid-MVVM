package com.example.devlibrary.databinding;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.example.devlibrary.R;

import java.util.List;

/**
 * Description:
 * Created by FQH on 2019/6/15.
 */
public abstract class BaseDataBindingSectionAdapter<T extends SectionEntity, B extends ViewDataBinding> extends BaseSectionQuickAdapter<T, BaseBindingViewHolder<B>> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public BaseDataBindingSectionAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseBindingViewHolder<B> helper, T item) {
        convertHead(helper.getBinding(), item);
        helper.getBinding().executePendingBindings();
    }

    @Override
    protected BaseBindingViewHolder<B> createBaseViewHolder(View view) {
        BaseBindingViewHolder<B> holder = new BaseBindingViewHolder<>(view);
        B b = (B) view.getTag(R.id.BaseQuickAdapter_databinding_support);
        holder.setBinding(b);
        return holder;
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        B b = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        View view;
        if (b == null) {
            return super.getItemView(layoutResId, parent);
        } else {
            view = b.getRoot();
        }
        view.setTag(R.id.BaseQuickAdapter_databinding_support, b);
        return view;
    }

    @Override
    protected BaseBindingViewHolder<B> createBaseViewHolder(ViewGroup parent, int layoutResId) {

        View view = getItemView(layoutResId, parent);
        BaseBindingViewHolder<B> holder = new BaseBindingViewHolder<>(view);
        B b = (B) view.getTag(R.id.BaseQuickAdapter_databinding_support);
        holder.setBinding(b);
        return holder;
    }

    @Override
    protected void convert(BaseBindingViewHolder<B> helper, T item) {
        convert(helper.getBinding(), item);
        helper.getBinding().executePendingBindings();
    }

    protected abstract void convert(B b, T item);
    protected abstract void convertHead(B b, T item);
}
