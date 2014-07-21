package fr.manciet.rss.reader.activities;

import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import fr.manciet.adapters.FeedMessageAdapter;
import fr.manciet.rss.model.FeedMessage;
import fr.manciet.rss.model.NBAFeedMessage;
import fr.manciet.rss.model.extractor.NBATeamTagExtractor;
import fr.manciet.rss.parser.AndroidSaxFeedParser;

/**
 * 
 * @author François Manciet
 *
 */
public class NBAFeedsFragment extends ListFragment implements OnRefreshListener {
	
	private String url;
	private PullToRefreshLayout mPullToRefreshLayout;
	
	/**
	 * Creates a new NBAFeedsFragments with the address of the NBA RSS feed 
	 * @param url_
	 */
	public NBAFeedsFragment(String url_) {
		this.url = url_;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		ViewGroup viewGroup = (ViewGroup) view;

		// As we're using a ListFragment we create a PullToRefreshLayout
		// manually
		mPullToRefreshLayout = new PullToRefreshLayout(
				viewGroup.getContext());
		int pixel = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 16, getResources()
						.getDisplayMetrics());
		mPullToRefreshLayout.setPadding(pixel, pixel, pixel, pixel);

		// We can now setup the PullToRefreshLayout
		ActionBarPullToRefresh
				.from(getActivity())
				// We need to insert the PullToRefreshLayout into the
				// Fragment's ViewGroup
				.insertLayoutInto(viewGroup)
				// Here we mark just the ListView and it's Empty View as
				// pullable
				.theseChildrenArePullable(android.R.id.list,
						android.R.id.empty).listener(this)
				.setup(mPullToRefreshLayout);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ListView Clicked item value
				FeedMessage itemValue = (FeedMessage) getListView().getItemAtPosition(position);
				// launchWebIntent(Uri.parse(itemValue.getLink()+ ""));
				Toast.makeText(getActivity(),
						itemValue.toString(), Toast.LENGTH_LONG).show();
			}
		});
		new RetrieveFeedTask().execute(url);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListShownNoAnimation(true);
	}
	
	@Override
	public void onRefreshStarted(View view) {
		new RetrieveFeedTask().execute(url);
	}
	
	/**
	 * Startss a new web intent with the Uri
	 * @param uri : web site uri
	 */
	public void launchWebIntent(Uri uri) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
		browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(browserIntent);
	}
	
	/**
	 * Reload the message feeds from the rss flow
	 */
	public void reloadFeeds() {
		new RetrieveFeedTask().execute(url);
	}

	/**
	 * 
	 * @author François Manciet
	 *
	 */
	public class RetrieveFeedTask extends AsyncTask<String, Void, List<FeedMessage>> {

		ProgressDialog progressDialog;
		
		/**
		 * Parse the file obtained from the URLs
		 */
		protected List<FeedMessage> doInBackground(String... urls) {
			AndroidSaxFeedParser androidSaxParser = new AndroidSaxFeedParser(urls[0],
					new NBATeamTagExtractor());
			List<FeedMessage> values = androidSaxParser.parse(new NBAFeedMessage());
			Log.i("RSS", "number of items = " + values.size());
			return values;
		}

		/**
		 * Build the list view with the FeedMessage contained in values
		 */
		protected void onPostExecute(List<FeedMessage> values) {

			//If the list view does not have an adapter, create one with values
			if (getListAdapter() == null) {
				Log.i("DEBUG", "Adapter null");
				FeedMessageAdapter adapter = new FeedMessageAdapter(
					getActivity(), values);
				setListAdapter(adapter);
			//Otherwise, get the adapter and update values inside
			} else {
				Log.i("DEBUG", "Adapter not null");
				FeedMessageAdapter adapter = (FeedMessageAdapter) getListAdapter();
				adapter.clear();
				adapter.addAll(values);
				adapter.notifyDataSetChanged();
				mPullToRefreshLayout.setRefreshComplete();
			}		
		}
	}
}