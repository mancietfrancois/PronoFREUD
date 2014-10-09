package uk.co.brightec.alphabetscroller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.regex.Pattern;

import uk.co.brightec.alphabetscroller.AlphabetListAdapter.Item;
import uk.co.brightec.alphabetscroller.AlphabetListAdapter.Row;
import uk.co.brightec.alphabetscroller.AlphabetListAdapter.Section;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends ListActivity {

    private AlphabetListAdapter adapter = new AlphabetListAdapter();
    private GestureDetector mGestureDetector;
    private List<Object[]> alphabet = new ArrayList<Object[]>();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    private int sideIndexHeight;
    private static float sideIndexX;
    private static float sideIndexY;
    private int indexListSize;

    class SideIndexGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;
            Log.i("DEBUG", "OnScroll");
            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }
            
            if (e2.getAction() == MotionEvent.ACTION_UP || e1.getAction() == MotionEvent.ACTION_UP) {
            	Log.i("DEBUG", "ViewGone");
            	RelativeLayout mSectionToastLayout = (RelativeLayout) findViewById(R.id.section_toast_layout);
            	mSectionToastLayout.setVisibility(View.GONE);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
        
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_alphabet);

        mGestureDetector = new GestureDetector(this, new SideIndexGestureListener());

        List<String> countries = populateCountries();
        Collections.sort(countries);

        List<Row> rows = new ArrayList<Row>();
        int start = 0;
        int end = 0;
        String previousLetter = null;
        Object[] tmpIndexItem = null;
        Pattern numberPattern = Pattern.compile("[0-9]");

        for (String country : countries) {
            String firstLetter = country.substring(0, 1);

            // Group numbers together in the scroller
            if (numberPattern.matcher(firstLetter).matches()) {
                firstLetter = "#";
            }

            // If we've changed to a new letter, add the previous letter to the alphabet scroller
            if (previousLetter != null && !firstLetter.equals(previousLetter)) {
                end = rows.size() - 1;
                tmpIndexItem = new Object[3];
                tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                tmpIndexItem[1] = start;
                tmpIndexItem[2] = end;
                alphabet.add(tmpIndexItem);

                start = end + 1;
            }

            // Check if we need to add a header row
            if (!firstLetter.equals(previousLetter)) {
                rows.add(new Section(firstLetter));
                sections.put(firstLetter, start);
            }

            // Add the country to the list
            rows.add(new Item(country));
            previousLetter = firstLetter;
        }

        if (previousLetter != null) {
            // Save the last letter
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }
        
//        alphabet.clear();
//        for (int i = 1; i < 32; i++) {
//        	Object[] bidon = new Object[3];
//        	bidon[0] = i + "";
//        	alphabet.add(bidon);
//        }


        adapter.setRows(rows);
        setListAdapter(adapter);

        updateList();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_UP) {
    		RelativeLayout mSectionToastLayout = (RelativeLayout) findViewById(R.id.section_toast_layout);
        	mSectionToastLayout.setVisibility(View.GONE);
    	}
        if (mGestureDetector.onTouchEvent(event)) {
        	
            return true;
        } else {
            return false;
        }
    }

    public void updateList() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        sideIndex.removeAllViews();
        indexListSize = alphabet.size();
        if (indexListSize < 1) {
            return;
        }

        int indexMaxSize = (int) Math.floor(sideIndex.getHeight() / 20);
        int tmpIndexListSize = indexListSize;
        while (tmpIndexListSize > indexMaxSize) {
            tmpIndexListSize = tmpIndexListSize / 2;
        }
        double delta;
        if (tmpIndexListSize > 0) {
            delta = indexListSize / tmpIndexListSize;
        } else {
            delta = 1;
        }

        TextView tmpTV;
        for (double i = 1; i <= indexListSize; i = i + delta) {
            Object[] tmpIndexItem = alphabet.get((int) i - 1);
            String tmpLetter = tmpIndexItem[0].toString();

            tmpTV = new TextView(this);
            tmpTV.setText(tmpLetter);
            tmpTV.setGravity(Gravity.CENTER);
            tmpTV.setTextSize(12);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            tmpTV.setLayoutParams(params);
            tmpTV.setBackgroundResource(R.drawable.border);
            tmpTV.setTextColor(Color.rgb(124, 131, 134));
            sideIndex.addView(tmpTV);
        }

        sideIndexHeight = sideIndex.getHeight();

        sideIndex.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // now you know coordinates of touch
                sideIndexX = event.getX();
                sideIndexY = event.getY();
                
                displayListItem();
                
                RelativeLayout mSectionToastLayout = (RelativeLayout) findViewById(R.id.section_toast_layout);
                switch (event.getAction()) {
		    		case MotionEvent.ACTION_DOWN: {
		    			Log.i("DEBUG", "ViewVisible");
		    			mSectionToastLayout.setVisibility(View.VISIBLE);
		    			mSectionToastLayout.bringToFront();
		    			break;
		    		}
                }
                return false;
            }
        });
    }

    public void displayListItem() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        TextView mSectionToastText = (TextView) findViewById(R.id.section_toast_text);
        sideIndexHeight = sideIndex.getHeight();
        // compute number of pixels for every side index item
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        // compute the item index for given event position belongs to
        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        // get the item (we can do it since we know item index)
        if (itemPosition < alphabet.size()) {
            Object[] indexItem = alphabet.get(itemPosition);
            int subitemPosition = sections.get(indexItem[0]);

            ListView listView = (ListView) findViewById(android.R.id.list);
            listView.setSelection(subitemPosition);
            mSectionToastText.setText(indexItem[0] + "");
        }
    }

    private List<String> populateCountries() {
        List<String> countries = new ArrayList<String>();
        countries.add("Afghanistan");
        countries.add("Albania");
        countries.add("Bahrain");
        countries.add("Bangladesh");
        countries.add("Cambodia");
        countries.add("Cameroon");
        countries.add("Denmark");
        countries.add("Djibouti");
        countries.add("East Timor");
        countries.add("Ecuador");
        countries.add("Fiji");
        countries.add("Finland");
        countries.add("Gabon");
        countries.add("Georgia");
        countries.add("Haiti");
        countries.add("Holy See");
        countries.add("Iceland");
        countries.add("India");
        countries.add("Jamaica");
        countries.add("Japan");
        countries.add("Kazakhstan");
        countries.add("Kenya");
        countries.add("Laos");
        countries.add("Latvia");
        countries.add("Macau");
        countries.add("Macedonia");
        countries.add("Namibia");
        countries.add("Nauru");
        countries.add("Oman");
        countries.add("Pakistan");
        countries.add("Palau");
        countries.add("Qatar");
        countries.add("Romania");
        countries.add("Russia");
        countries.add("Saint Kitts and Nevis");
        countries.add("Saint Lucia");
        countries.add("Taiwan");
        countries.add("Tajikistan");
        countries.add("Uganda");
        countries.add("Ukraine");
        countries.add("Vanuatu");
        countries.add("Venezuela");
        countries.add("Yemen");
        countries.add("Zambia");
        countries.add("Zimbabwe");
        countries.add("0");
        countries.add("2");
        countries.add("9");
        return countries;
    }
}
