package com.android.sectionlist.sample;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Adapter for sections.
 */
public class SectionListAdapter extends BaseAdapter implements ListAdapter,
        OnItemClickListener {
    
    private final SectionListItem[] items;
    private final SparseArray<String> sectionPositions = new SparseArray<String>();
    private final SparseIntArray itemPositions = new SparseIntArray();
    private final Map<View, String> currentViewSections = new HashMap<View, String>();
    private int viewTypeCount;
    protected final LayoutInflater inflater;

    public View headerView;

    private OnItemClickListener linkedListener;
    
    public SectionListAdapter(final LayoutInflater inflater,
            final SectionListItem[] items) {
        this.items = items;
        this.inflater = inflater;
        
        this.headerView=inflater.inflate(R.layout.section_view, null);
        updateSessionCache();
    }

    private synchronized void updateSessionCache() {
        int currentPosition = 0;
        sectionPositions.clear();
        itemPositions.clear();
        viewTypeCount = 2;
        String currentSection = null;
        final int count = items.length;

        for (int i = 0; i < count; i++) {
            final SectionListItem item =items[i];

            boolean sameSection = currentSection == null ? item.section == null
                    : currentSection.equals(item.section);

            if (!sameSection) {
                sectionPositions.put(currentPosition, item.section);
                currentSection = item.section;
                currentPosition++;
            }
            itemPositions.put(currentPosition, i);
            currentPosition++;
        }
    }

    @Override
    public synchronized int getCount() {
        return sectionPositions.size() + itemPositions.size();
    }

    @Override
    public synchronized Object getItem(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position);
        }
        final int linkedItemPosition = getLinkedPosition(position);
        return items[linkedItemPosition];

    }

    public synchronized boolean isSection(final int position) {
        return sectionPositions.get(position)!=null;
    }

    public synchronized String getSectionName(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position);
        }
        return null;

    }

    @Override
    public long getItemId(final int position) {
        if (isSection(position)) {
            return sectionPositions.get(position).hashCode();
        }

        return items[getLinkedPosition(position)].hashCode();

    }

    protected Integer getLinkedPosition(final int position) {
        return itemPositions.get(position);
    }

    @Override
    public int getItemViewType(final int position) {
        if (isSection(position)) {
            return viewTypeCount - 1;
        }else
            return viewTypeCount -2;
        
        
    }

    private View getSectionView(final View convertView, final String section) {
        View theView = convertView;
        if (theView == null) {
            theView = inflater.inflate(R.layout.section_view, null);
        }
        setSectionText(section, theView);
        replaceSectionViewsInMaps(section, theView);
        return theView;
    }

    protected void setSectionText(final String section, final View sectionView) {
        final TextView textView = (TextView) sectionView
                .findViewById(R.id.listTextView);
        textView.setText(section);
    }

    protected synchronized void replaceSectionViewsInMaps(final String section,
            final View theView) {
        if (currentViewSections.containsKey(theView)) {
            currentViewSections.remove(theView);
        }
        currentViewSections.put(theView, section);
    }

    @Override
    public View getView(final int position, final View convertView,
            final ViewGroup parent) {
        if (isSection(position)) {
            return getSectionView(convertView, sectionPositions.get(position));
        }
        return getItemView(getLinkedPosition(position), convertView,
                parent);
    }
    

    public View getItemView( int position,  View convertView,ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.example_list_view, null);
        }
        final SectionListItem currentItem = items[position];
        if (currentItem != null) {
            final TextView textView = (TextView) view
                    .findViewById(R.id.example_text_view);
            if (textView != null) {
                textView.setText(currentItem.item.toString());
            }
        }
        return view;
    }


    @Override
    public int getViewTypeCount() {
        return viewTypeCount;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return items.length==0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(final int position) {
        return true;
    }

    public void makeSectionInvisibleIfFirstInList(final int firstVisibleItem) {
       
        for (int i=0 ;i<sectionPositions.size(); i++) {
            if (sectionPositions.keyAt(i) > firstVisibleItem ) {
                break;
            }
            setSectionText(sectionPositions.get(sectionPositions.keyAt(i)), headerView);
        }
    }

    
    protected void sectionClicked(final String section) {}

    @Override
    public void onItemClick(final AdapterView< ? > parent, final View view,
            final int position, final long id) {
        if (isSection(position)) {
            sectionClicked(getSectionName(position));
        } else if (linkedListener != null) {
            linkedListener.onItemClick(parent, view,
                    getLinkedPosition(position), id);
        }
    }

    public void setOnItemClickListener(final OnItemClickListener linkedListener) {
        this.linkedListener = linkedListener;
    }
}
