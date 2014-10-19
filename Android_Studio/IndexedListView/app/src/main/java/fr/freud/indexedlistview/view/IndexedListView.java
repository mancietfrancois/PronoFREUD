package fr.freud.indexedlistview.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import fr.freud.indexedlistview.R;
import fr.freud.indexedlistview.adapter.IndexBaseAdapter;
import fr.freud.indexedlistview.index.Indexer;

/**
 * Created by François_2 on 19/10/2014.
 */
public class IndexedListView extends RelativeLayout {

    private static final int LIST_POSITION = 1;
    private static final int SIDE_INDEX_POSITION = 0;
    private static final int TOAST_LAYOUT_POSITION = 2;
    private static final int TOAST_TEXT_POSITION = 0;

    private static final int DEFAULT_SIDE_INDEX_WIDTH = 20;
    private static final int DEFAULT_SIDE_INDEX_BACKGROUND = R.drawable.default_index_item_border;
    private static final int DEFAULT_SIDE_INDEX_TEXT_SIZE = 12;
    private static final String DEFAULT_SIDE_INDEX_TEXT_COLOR = "#7C8386";

    private static final int DEFAULT_TOAST_LAYOUT_WIDTH = 100;
    private static final int DEFAULT_TOAST_LAYOUT_HEIGHT = 100;
    private static final int DEFAULT_TOAST_LAYOUT_BACKGROUND = R.drawable.default_toast_layout_border;

    private static final int DEFAULT_TOAST_TEXT_SIZE = 55;
    private static final String DEFAULT_TOAST_TEXT_COLOR = "#2FA6C9";

    private ListView list;
    private LinearLayout sideIndex;
    private RelativeLayout toastLayout;
    private TextView toastText;

    private int sideIndexHeight;
    private static float sideIndexX;
    private static float sideIndexY;
    private int indexListSize;
    private float sideIndexTextSize;
    private Drawable sideIndexBackground;
    private int sideIndexTextColor;


    private GestureDetector mGestureDetector;
    private Indexer indexer;

    private Context ctx;

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
                toastLayout.setVisibility(View.GONE);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    public IndexedListView(Context context) {
        this(context, null);
    }

    public IndexedListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        ctx = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.indexed_list_view, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.IndexedListView, 0, 0);

        //Side index attributes
        float sideIndexWidth = a.getDimension(R.styleable.IndexedListView_side_index_width,
                DEFAULT_SIDE_INDEX_WIDTH);
        sideIndexBackground = a.getDrawable(R.styleable.
                IndexedListView_side_index_background);
        if (sideIndexBackground == null) {
            sideIndexBackground = getResources().getDrawable(DEFAULT_SIDE_INDEX_BACKGROUND);
        }
        sideIndexTextSize = a.getDimension(R.styleable.IndexedListView_side_index_text_size,
                DEFAULT_SIDE_INDEX_TEXT_SIZE);
        sideIndexTextColor = a.getColor(R.styleable.IndexedListView_side_index_text_color,
                Color.parseColor(DEFAULT_SIDE_INDEX_TEXT_COLOR));

        //toast layout attributes
        float toastLayoutWidth = a.getDimension(R.styleable.IndexedListView_toast_layout_width,
                DEFAULT_TOAST_LAYOUT_WIDTH);
        float toastLayoutHeight = a.getDimension(R.styleable.IndexedListView_toast_layout_height,
                DEFAULT_TOAST_LAYOUT_HEIGHT);
        Drawable toastLayoutBackground = a.getDrawable(R.styleable.
                IndexedListView_toast_layout_background);
        if (toastLayoutBackground == null) {
            toastLayoutBackground = getResources().getDrawable(DEFAULT_TOAST_LAYOUT_BACKGROUND);
        }

        //toast text attributes
        float toastTextTextSize = a.getDimension(R.styleable.IndexedListView_toast_text_text_size,
                DEFAULT_TOAST_TEXT_SIZE);
        int toastTextColor = a.getColor(R.styleable.IndexedListView_toast_text_color,
                Color.parseColor(DEFAULT_TOAST_TEXT_COLOR));

        a.recycle();

        list = (ListView) getChildAt(LIST_POSITION);

        sideIndex = (LinearLayout) getChildAt(SIDE_INDEX_POSITION);
        sideIndex.getLayoutParams().width = (int) sideIndexWidth;

        toastLayout = (RelativeLayout) getChildAt(TOAST_LAYOUT_POSITION);
        toastLayout.getLayoutParams().width = (int) toastLayoutWidth;
        toastLayout.getLayoutParams().height = (int) toastLayoutHeight;
        toastLayout.setBackground(toastLayoutBackground);

        toastText = (TextView) toastLayout.getChildAt(TOAST_TEXT_POSITION);
        toastText.setTextSize(toastTextTextSize);
        toastText.setTextColor(toastTextColor);

        mGestureDetector = new GestureDetector(context, new SideIndexGestureListener());
    }

    public void populateList(IndexBaseAdapter listAdapter, Indexer _indexer) {
        indexer = _indexer;
        listAdapter.setRows(indexer.buildAlphabet());
        list.setAdapter(listAdapter);
        updateList();
    }

    public void updateList() {
        sideIndex.removeAllViews();
        List<Object[]> alphabet = indexer.getAlphabet();
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

            tmpTV = new TextView(ctx);
            tmpTV.setText(tmpLetter);
            tmpTV.setGravity(Gravity.CENTER);
            tmpTV.setTextSize(sideIndexTextSize);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1);
            tmpTV.setLayoutParams(params);
            tmpTV.setBackground(sideIndexBackground);
            tmpTV.setTextColor(sideIndexTextColor);
            sideIndex.addView(tmpTV);
        }

        sideIndexHeight = sideIndex.getHeight();

        sideIndex.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // now you know coordinates of touch
                sideIndexX = event.getX();
                sideIndexY = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.i("DEBUG", "ViewVisible");
                        toastLayout.setVisibility(View.VISIBLE);
                        toastLayout.bringToFront();
                        displayListItem();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.i("DEBUG", "ViewInvisible");
                        toastLayout.setVisibility(View.GONE);
                        break;
                    }
                }
                return true;
            }
        });

        sideIndex.setOnGenericMotionListener(new OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View v, MotionEvent event) {
                Log.i("DEBUG", "onDrag");
                return false;
            }
        });
    }

    public void displayListItem() {

        sideIndexHeight = sideIndex.getHeight();
        List<Object[]> alphabet = indexer.getAlphabet();
        HashMap<Object, Integer> sections = indexer.getSections();
        // compute number of pixels for every side index item
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        // compute the item index for given event position belongs to
        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        // get the item (we can do it since we know item index)
        if (itemPosition < alphabet.size()) {
            Object[] indexItem = alphabet.get(itemPosition);
            int subItemPosition = sections.get(indexItem[0]);

            list.setSelection(subItemPosition);
            toastText.setText(indexItem[0] + "");
        }
    }

}
