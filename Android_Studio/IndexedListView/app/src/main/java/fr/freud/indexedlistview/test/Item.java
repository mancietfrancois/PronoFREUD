package fr.freud.indexedlistview.test;

import android.view.View;
import android.widget.TextView;

import fr.freud.indexedlistview.R;
import fr.freud.indexedlistview.adapter.Row;

public class Item extends Row {
	
	public final String text;
	private static final int textViewID = R.id.textView1;

    public Item(String text) {
        this.text = text;
    }

    public void inflateView(View view) {
		TextView textView = (TextView) view.findViewById(textViewID);
		textView.setText(text);
	}

}
