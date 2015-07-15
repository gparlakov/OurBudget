package com.parlakovi.petqjoro.ourbudget.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parlakovi.petqjoro.ourbudget.R;

import java.util.Collection;

/**
 * Created by gparl_000 on 6/28/2015.
 */
public class SimpleTextArrayAdapter extends ArrayAdapter<IArrayAdapterItem> {

    private final int resourceIdForRow;
    private final boolean mShowAddNew;

    private Collection<? extends IArrayAdapterItem> mCurrentCollection;
    public SimpleTextArrayAdapter(Context context, int resourceIdForRow, int textViewResId, boolean showAddNew) {
        super(context, resourceIdForRow, textViewResId);

        this.resourceIdForRow = resourceIdForRow;
        this.mShowAddNew = showAddNew;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View workingView = getWorkingView(convertView);
        ViewHolder viewHolder = getViewHolder(workingView);

        if (position == this.getCount()){
            // add the "Create new option here"
            viewHolder.titleView.setText("Add new");
        }
        else {
            IArrayAdapterItem entry = getItem(position);

            viewHolder.titleView.setText(entry.getRowText());
        }

        return  workingView;
    }

    @Override
    public long getItemId(int position) {
        if (position < this.getCount() - 1){
            return super.getItemId(position);
        }
        else {
            return -1;
        }
    }

    @Override
    public IArrayAdapterItem getItem(int position) {
        if (position < this.getCount() - 1) {
            return super.getItem(position);
        }
        else{
            return new InsideArrayItem();
        }
    }

    @Override
    public int getCount() {

        int count = super.getCount();
        if (mShowAddNew) {
            count ++;
        }
        return count;
    }

    private View getWorkingView(final View convertView) {
        // The workingView is basically just the convertView re-used if possible
        // or inflated new if not possible
        View workingView = null;

        if(null == convertView) {
            final Context context = getContext();
            final LayoutInflater inflater = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);

            workingView = inflater.inflate(resourceIdForRow, null);
        } else {
            workingView = convertView;
        }

        return workingView;
    }

    private ViewHolder getViewHolder(final View workingView) {
        // The viewHolder allows us to avoid re-looking up view references
        // Since views are recycled, these references will never change
        final Object tag = workingView.getTag();
        ViewHolder viewHolder = null;


        if(null == tag || !(tag instanceof ViewHolder)) {
            viewHolder = new ViewHolder();

            viewHolder.titleView = (TextView) workingView.findViewById(R.id.simple_text_view_title_firstTextView);


            workingView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) tag;
        }

        return viewHolder;
    }

    public Collection<IArrayAdapterItem> getAllItems() {
        return null;
    }

    /**
     * ViewHolder allows us to avoid re-looking up view references
     * Since views are recycled, these references will never change
     */
    private static class ViewHolder {
        public TextView titleView;
    }

    private class InsideArrayItem implements IArrayAdapterItem
    {
        @Override
        public int getId() {
            return 0;
        }

        @Override
        public String getRowText() {
            return "Add new";
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return this.getView(position, convertView, parent);
    }

    @Override
    public void addAll(Collection<? extends IArrayAdapterItem> collection) {

        super.addAll(collection);
    }
}
