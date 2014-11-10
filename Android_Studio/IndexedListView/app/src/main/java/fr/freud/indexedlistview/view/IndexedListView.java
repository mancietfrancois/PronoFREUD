package fr.freud.indexedlistview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private static final int SIDE_INDEX_POSITION = 0;
    private static final int LIST_POSITION = 1;
    private static final int TOAST_LAYOUT_POSITION = 3;
    private static final int TOAST_LAYOUT_ARROW_POSITION = 2;
    private static final int TOAST_TEXT_POSITION = 0;

    private static final int DEFAULT_SIDE_INDEX_WIDTH = 20;
    private static final int DEFAULT_SIDE_INDEX_BACKGROUND = R.drawable.default_index_item_border;
    private static final int DEFAULT_SIDE_INDEX_TEXT_SIZE = 8;
    private static final String DEFAULT_SIDE_INDEX_TEXT_COLOR = "#bebebe";

    private static final int DEFAULT_TOAST_TEXT_SIZE = 18;
    private static final String DEFAULT_TOAST_TEXT_COLOR = "#bebebe";


    private ListView mList;
    private LinearLayout mSideIndex;
    private RelativeLayout mToastLayout;
    private RelativeLayout mToastLayoutArrow;
    private TextView mToastText;

    private int mSideIndexHeight;
    private static float mSideIndexY;
    private int mIndexListSize;
    private float mSideIndexTextSize;
    private Drawable mSideIndexBackground;
    private int mSideIndexTextColor;

    private Indexer mIndexer;
    private Context mContext;

    public IndexedListView(Context context) {
        this(context, null);
    }

    public IndexedListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.indexed_list_view, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.IndexedListView, 0, 0);

        //Side index attributes
        float sideIndexWidth = a.getDimension(R.styleable.IndexedListView_side_index_width,
                DEFAULT_SIDE_INDEX_WIDTH);
        mSideIndexBackground = a.getDrawable(R.styleable.
                IndexedListView_side_index_background);
        if (mSideIndexBackground == null) {
            mSideIndexBackground = getResources().getDrawable(DEFAULT_SIDE_INDEX_BACKGROUND);
        }
        mSideIndexTextSize = a.getDimension(R.styleable.IndexedListView_side_index_text_size,
                DEFAULT_SIDE_INDEX_TEXT_SIZE);
        mSideIndexTextColor = a.getColor(R.styleable.IndexedListView_side_index_text_color,
                Color.parseColor(DEFAULT_SIDE_INDEX_TEXT_COLOR));

        //toast text attributes
        float toastTextTextSize = a.getDimension(R.styleable.IndexedListView_toast_text_text_size,
                DEFAULT_TOAST_TEXT_SIZE);
        int toastTextColor = a.getColor(R.styleable.IndexedListView_toast_text_color,
                Color.parseColor(DEFAULT_TOAST_TEXT_COLOR));
        a.recycle();

        //Instantiate views and layouts
        mList = (ListView) getChildAt(LIST_POSITION);

        mSideIndex = (LinearLayout) getChildAt(SIDE_INDEX_POSITION);
        mSideIndex.getLayoutParams().width = (int) sideIndexWidth;

        mToastLayout = (RelativeLayout) getChildAt(TOAST_LAYOUT_POSITION);

        mToastText = (TextView) mToastLayout.getChildAt(TOAST_TEXT_POSITION);
        mToastText.setTextSize(toastTextTextSize);
        mToastText.setTextColor(toastTextColor);

        //We move the toast close to the side index
        mToastLayoutArrow = (RelativeLayout) getChildAt(TOAST_LAYOUT_ARROW_POSITION);
        mToastLayoutArrow.setX(50);
        mToastLayout.setX(50);
    }

    /**
     * Used to populate the list with an adapter and an index.
     * @param _listAdapter
     * @param _indexer
     */
    public void populateList(IndexBaseAdapter _listAdapter, Indexer _indexer) {
        mIndexer = _indexer;
        _listAdapter.setRows(mIndexer.buildAlphabet());
        mList.setAdapter(_listAdapter);
        updateList();
    }

    /**
     * Create and place the scrolling bar with the references to the data into the list
     */
    public void updateList() {
        mSideIndex.removeAllViews();
        List<Object[]> alphabet = mIndexer.getAlphabet();
        mIndexListSize = alphabet.size();
        //the list is empty
        if (mIndexListSize < 1) {
            return;
        }

        int indexMaxSize = (int) Math.floor(mSideIndex.getHeight() / 20);
        int tmpIndexListSize = mIndexListSize;
        while (tmpIndexListSize > indexMaxSize) {
            tmpIndexListSize = tmpIndexListSize / 2;
        }
        double delta;
        if (tmpIndexListSize > 0) {
            delta = mIndexListSize / tmpIndexListSize;
        } else {
            delta = 1;
        }

        TextView tmpTV;

        //Place the search icon at the top of the index bar
        ImageView iconSearch = new ImageView(mContext);
        iconSearch.setBackgroundResource(R.drawable.ic_action_search);
        LinearLayout.LayoutParams paramsView = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
        iconSearch.setLayoutParams(paramsView);
        mSideIndex.addView(iconSearch);

        //Place each index value in the index bar
        for (double i = 1; i <= mIndexListSize; i = i + delta) {
            Object[] tmpIndexItem = alphabet.get((int) i - 1);
            String tmpLetter = tmpIndexItem[0].toString();
            tmpTV = new TextView(mContext);
            tmpTV.setText(tmpLetter);
            tmpTV.setTypeface(Typeface.SANS_SERIF);
            tmpTV.setGravity(Gravity.CENTER);
            tmpTV.setTextSize(mSideIndexTextSize);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1);
            tmpTV.setLayoutParams(params);
            tmpTV.setBackground(mSideIndexBackground);
            tmpTV.setTextColor(mSideIndexTextColor);
            mSideIndex.addView(tmpTV);
        }

        mSideIndexHeight = mSideIndex.getHeight();
        mSideIndex.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // now you know coordinates of touch
                mSideIndexY = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        moveToast(mSideIndexY);
                        mToastLayout.setVisibility(View.VISIBLE);
                        mToastLayout.bringToFront();
                        mToastLayoutArrow.setVisibility(View.VISIBLE);
                        displayListItem();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                        moveToast(mSideIndexY);
                        displayListItem();
                        break;
                    case MotionEvent.ACTION_UP: {
                        mToastLayout.setVisibility(View.GONE);
                        mToastLayoutArrow.setVisibility(View.GONE);
                        break;
                    }
                }
                return true;
            }
        });
    }

    /**
     * Display the letter in the toast
     */
    public void displayListItem() {
        mSideIndexHeight = mSideIndex.getHeight();
        List<Object[]> alphabet = mIndexer.getAlphabet();
        HashMap<Object, Integer> sections = mIndexer.getSections();
        // compute number of pixels for every side index item
        double pixelPerIndexItem = (double) mSideIndexHeight / mIndexListSize;

        // compute the item index for given event position belongs to
        int itemPosition = (int) (mSideIndexY / pixelPerIndexItem);

        // get the item (we can do it since we know item index)
        if (itemPosition < alphabet.size() && itemPosition >= 0) {
            Object[] indexItem = alphabet.get(itemPosition);
            int subItemPosition = sections.get(indexItem[0]);
            mList.setSelection(subItemPosition);
            mToastText.setText(indexItem[0] + "");
        }
    }

    /**
     * Move the toast at the position y
     * @param y: the position y where the toast is moved
     */
    private void moveToast(float y) {
        float offSet = mToastLayout.getLayoutParams().height / 2;
        if (y - offSet >= 0 && y + offSet <= mList.getHeight()) {
            mToastLayout.setY(y - offSet);
            mToastLayoutArrow.setY(y - offSet - 8);
        } else if (y - offSet < 0) {
            mToastLayout.setY(0);
            mToastLayoutArrow.setY(0 - 8);
        } else if (y + offSet > mList.getHeight()) {
            mToastLayout.setY(mList.getHeight() - 2 * offSet);
            mToastLayoutArrow.setY(mList.getHeight() - 2 * offSet - 8);
        }
    }

}
