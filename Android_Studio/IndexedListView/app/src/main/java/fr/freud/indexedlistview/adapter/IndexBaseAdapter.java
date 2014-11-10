package fr.freud.indexedlistview.adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by François_2 on 19/10/2014.
 */
public abstract class IndexBaseAdapter extends BaseAdapter {

    protected List<Row> mRows;

    public void setRows(List<Row> _rows) {
        mRows = _rows;
    }
}
