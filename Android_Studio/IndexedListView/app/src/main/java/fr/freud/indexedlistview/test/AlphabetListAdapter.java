package fr.freud.indexedlistview.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import fr.freud.indexedlistview.adapter.IndexBaseAdapter;
import fr.freud.indexedlistview.adapter.Row;

public class AlphabetListAdapter extends IndexBaseAdapter {

	private int rowSectionResourceId;
	private int rowItemResourceId;
	
	public AlphabetListAdapter(int _rowSectionResourceId, int _rowItemResourceId) {
		rowSectionResourceId = _rowSectionResourceId;
		rowItemResourceId = _rowItemResourceId;
	}
	
	public void setRowSectionResourceId(int id){
		rowSectionResourceId = id;
	}
	
	public void setRowItemResourceId(int id){
		rowItemResourceId = id;
	}
    

    @Override
    public int getCount() {
        return mRows.size();
    }

    @Override
    public Row getItem(int position) {
        return mRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    
    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Section) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        
        if (getItemViewType(position) == 0) { // Item
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(rowItemResourceId, parent, false);  
            }
            
            Item item = (Item) getItem(position);
            item.inflateView(view);
        } else { // Section
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(rowSectionResourceId, parent, false);  
            }
            
            Section section = (Section) getItem(position);
            section.inflateView(view);
        }
        
        return view;
    }

}
