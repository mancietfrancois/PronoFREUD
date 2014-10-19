package uk.co.brightec.alphabetscroller;

import java.util.Locale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ListView;

public class CustomList extends ListView{
	
	private static int indWidth = 20;
	private String section = "A";
	private boolean showLetter = false;
	
	private Paint textPaint2;
	
	public CustomList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		textPaint2 = new Paint();
	}

	public CustomList(Context context, AttributeSet attrs) {
		super(context, attrs);
		textPaint2 = new Paint();
	}

	public CustomList(Context context, String keyList) {
		super(context);
		textPaint2 = new Paint();
	}

	public CustomList(Context context) {
		super(context);
		textPaint2 = new Paint();
	}
	
	public void enableShowLetter(Boolean b) {
		showLetter = b;
	}
	
	public void setSection(String s) {
		section = s;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// We draw the letter in the middle
		if (showLetter & section != null && !section.equals("")) {

			
			textPaint2.setColor(Color.DKGRAY);
			textPaint2.setTextSize(2 * indWidth);

			canvas.drawText(section.toUpperCase(Locale.ENGLISH), getWidth() / 2,
					getHeight() / 2, textPaint2);
		}
	}
	
	

}
