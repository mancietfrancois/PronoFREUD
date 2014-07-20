package fr.manciet.rss.reader.activities;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import fr.manciet.adapters.FeedMessageAdapter;
import fr.manciet.androidrssreader.R;
import fr.manciet.rss.model.FeedMessage;
import fr.manciet.rss.read.AndroidSaxFeedParser;

/**
 * 
 * @author François Manciet
 *
 */
public class NBAFeedsFragment extends Fragment {
	
	private String url;
	private View rootView;
	private ListView listView;
	
	/**
	 * Creates a new NBAFeedsFragments with the address of the NBA RSS feed 
	 * @param url_
	 */
	public NBAFeedsFragment(String url_) {
		this.url = url_;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(
				R.layout.fragment_rss_reader, container,
				false);
		listView = (ListView) rootView.findViewById(R.id.listViewRSSFeeds);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ListView Clicked item value
				FeedMessage itemValue = (FeedMessage) listView
						.getItemAtPosition(position);

				// launchWebIntent(Uri.parse(itemValue.getLink()+ ""));
				Toast.makeText(getActivity(),
						itemValue.toString(), Toast.LENGTH_LONG).show();
			}
		});
		new RetrieveFeedTask().execute(url);
		return rootView;
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
			AndroidSaxFeedParser androidSaxParser = new AndroidSaxFeedParser(
					urls[0]);
			List<FeedMessage> values = androidSaxParser.parse();
			Log.i("RSS", "number of items = " + values.size());
			return values;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//Initiate a progressDialog 
			progressDialog = ProgressDialog.show(NBAFeedsFragment.this.getActivity(),
					"Wait", "Downloading...");
		}

		/**
		 * Build the list view with the FeedMessage contained in values
		 */
		protected void onPostExecute(List<FeedMessage> values) {

			//If the list view does not have an adapter, create one with values
			if (listView.getAdapter() == null) {
				FeedMessageAdapter adapter = new FeedMessageAdapter(
					getActivity(), values);
				listView.setAdapter(adapter);
			//Otherwise, get the adapter and update values inside
			} else {
				FeedMessageAdapter adapter = (FeedMessageAdapter) listView.getAdapter();
				adapter.clear();
				adapter.addAll(values);
				adapter.notifyDataSetChanged();
			}
			//Dismiss the progressDialog
			progressDialog.dismiss();			
		}
	}

}