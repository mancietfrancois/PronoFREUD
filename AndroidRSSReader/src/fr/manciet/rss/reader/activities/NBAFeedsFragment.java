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

public class NBAFeedsFragment extends Fragment {
	
	private String url;
	
	public NBAFeedsFragment(String url_) {
		this.url = url_;
		
	}
	
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(
				R.layout.fragment_rss_reader, container,
				false);
		new RetrieveFeedTask().execute(url);
		return rootView;
	}
	
	public void launchWebIntent(Uri uri) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
		browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(browserIntent);
	}
	
	public void reloadFeeds() {
		new RetrieveFeedTask().execute(url);
	}

	public class RetrieveFeedTask extends AsyncTask<String, Void, List<FeedMessage>> {

		ProgressDialog progressDialog;
		
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
			progressDialog = ProgressDialog.show(NBAFeedsFragment.this.getActivity(),
					"Wait", "Downloading...");
		}

		protected void onPostExecute(List<FeedMessage> values) {
			final ListView listView = (ListView) rootView.findViewById(R.id.listViewRSSFeeds);
			if (listView.getAdapter() == null) {
				FeedMessageAdapter adapter = new FeedMessageAdapter(
					getActivity(), values);
				listView.setAdapter(adapter);
			} else {
				FeedMessageAdapter adapter = (FeedMessageAdapter) listView.getAdapter();
				adapter.clear();
				adapter.addAll(values);
				adapter.notifyDataSetChanged();
			}

			progressDialog.dismiss();
			// ListView Item Click Listener
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
		}
	}

}