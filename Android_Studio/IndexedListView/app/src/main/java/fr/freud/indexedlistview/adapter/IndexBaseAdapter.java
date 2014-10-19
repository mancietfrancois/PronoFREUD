package fr.freud.indexedlistview.adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by François_2 on 19/10/2014.
 */
public abstract class IndexBaseAdapter extends BaseAdapter {

    protected List<Row> rows;

    public void setRows(List<Row> _rows) {
        rows = _rows;
    }
}
