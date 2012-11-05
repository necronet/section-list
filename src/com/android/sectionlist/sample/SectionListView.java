package com.android.sectionlist.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * View displaying the list with sectioned header.
 */
public class SectionListView extends ListView implements OnScrollListener {

    private View headerView;

    public SectionListView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
        commonInitialisation();
    }

    public SectionListView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        commonInitialisation();
    }

    public SectionListView(final Context context) {
        super(context);
        commonInitialisation();
    }

    protected final void commonInitialisation() {
        setOnScrollListener(this);
        setVerticalFadingEdgeEnabled(false);
        setFadingEdgeLength(0);
    }

    @Override
    public void setAdapter(final ListAdapter adapter) {
        if (!(adapter instanceof SectionListAdapter)) {
            throw new IllegalArgumentException(
                    "The adapter needds to be of type "
                            + SectionListAdapter.class + " and is "
                            + adapter.getClass());
        }
        super.setAdapter(adapter);
        final ViewParent parent = getParent();
        
        if (headerView != null) 
            ((ViewGroup) parent).removeView(headerView);
        
        headerView = ((SectionListAdapter) adapter).headerView;
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        
        if(parent instanceof LinearLayout)
            ((LinearLayout)parent).addView(headerView, 0);
        else
            ((ViewGroup) parent).addView(headerView, lp);
        
        if (adapter.isEmpty()) {
            headerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem,
            final int visibleItemCount, final int totalItemCount) {
        final SectionListAdapter adapter = (SectionListAdapter) getAdapter();
        if (adapter != null) 
            adapter.makeSectionInvisibleIfFirstInList(firstVisibleItem);
        
    }

    @Override
    public void onScrollStateChanged(final AbsListView view,
            final int scrollState) {}

}
