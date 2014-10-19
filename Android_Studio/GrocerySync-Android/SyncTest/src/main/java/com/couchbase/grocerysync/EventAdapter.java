package com.couchbase.grocerysync;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.QueryRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 04/08/14.
 */
public class EventAdapter extends ArrayAdapter<QueryRow> {

    private List<QueryRow> list;
    private final Context context;
    private View.OnClickListener onClickListener;

    public EventAdapter(Context context, int resource, int textViewResourceId, List<QueryRow> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.list = objects;
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRow(view.getId()== R.id.imageButtonExt ? "ext" : "home", Integer.valueOf((String) view.getTag()));
                notifyDataSetChanged();
            }
        };
    }

    private static class ViewHolder {
        ImageView iconHome, iconExt, arobase;
    }

    public void updateRow(String prono, int id) {

        QueryRow row = (QueryRow) list.get(id);
        Document document = row.getDocument();
        Map<String, Object> curProperties = document.getProperties();
        Map<String, Object> newProperties = new HashMap<String, Object>();
        newProperties.putAll(curProperties);

        newProperties.put("prono",prono);
        try
        {
            document.putProperties(newProperties);

        }

        catch(
        Exception e
        )

        {
            Toast.makeText(getContext(), "Error updating database, see logs for details", Toast.LENGTH_LONG).show();
            Log.e("TAG", "Error updating database", e);
        }

    }
    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        if (itemView == null) {
            LayoutInflater vi = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = vi.inflate(R.layout.event_list_item, null);
            ViewHolder vh = new ViewHolder();
            vh.iconHome = (ImageView) itemView.findViewById(R.id.imageButtonHome);
            vh.iconHome.setOnClickListener(onClickListener);
            vh.iconHome.setTag(position + "");

            vh.iconExt = (ImageView) itemView.findViewById(R.id.imageButtonExt);
            vh.iconExt.setOnClickListener(onClickListener);
            vh.iconExt.setTag(position + "");

            vh.arobase = (ImageView) itemView.findViewById(R.id.textViewAt);

            vh.arobase.setEnabled(false);
            itemView.setTag(vh);
        }

       Event.Pronostic prono = Event.Pronostic.DRAW;

       QueryRow row = list.get(position);
       Document document = row.getDocument();

        try {
          prono = Event.Pronostic.valueOf((String) document.getCurrentRevision().getProperty("prono").toString().toUpperCase());
        } catch (Exception e) {

            Log.e(MainActivity.TAG, "Error Displaying document", e);
        }
        ImageView iconHome = ((ViewHolder)itemView.getTag()).iconHome,
                iconExt = ((ViewHolder)itemView.getTag()).iconExt;

       switch (prono){
           case HOME:
               iconExt.setEnabled(true);
               iconExt.setBackgroundColor(Color.TRANSPARENT);
               iconHome.setEnabled(false);
               iconHome.setBackgroundColor(Color.WHITE);
               break;
           case EXT:
               iconExt.setEnabled(false);
               iconExt.setBackgroundColor(Color.WHITE);
               iconHome.setEnabled(true);
               iconHome.setBackgroundColor(Color.TRANSPARENT);
               break;
           case DRAW:
               iconExt.setEnabled(true);
               iconExt.setBackgroundColor(Color.TRANSPARENT);
               iconHome.setEnabled(true);
               iconHome.setBackgroundColor(Color.TRANSPARENT);
               break;
       }


       int resHomeIcon = getContext().getResources().getIdentifier("ic_" + document.getCurrentRevision().getProperty("home").toString().toLowerCase() , "drawable", context.getPackageName());
       int resExtIcon = getContext().getResources().getIdentifier("ic_" + (String)document.getCurrentRevision().getProperty("ext").toString().toLowerCase(), "drawable", context.getPackageName());

       iconExt.setImageResource(resExtIcon);

       iconHome.setImageResource(resHomeIcon);
       return itemView;
    }
}