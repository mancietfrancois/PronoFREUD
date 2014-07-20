package fr.manciet.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.manciet.androidrssreader.R;
import fr.manciet.rss.model.FeedMessage;

/**
 * 
 * @author François Manciet
 *
 */
public class FeedMessageAdapter extends ArrayAdapter<FeedMessage> {

	private final Context context;
	private final List<FeedMessage> values;

	/**
	 * Adapter built with the list of FeedMessage extracted from RSS flow
	 * Inflated with the layout row_layout (icon, title and description of the FeedMessage)
	 * @param context : context of the application
	 * @param values : Data extracted from the RSS flow
	 */
	public FeedMessageAdapter(Context context, List<FeedMessage> values) {
		super(context, R.layout.row_layout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_layout, parent, false);

		//Extraction of each field : icon, title and description
		TextView title = (TextView) rowView.findViewById(R.id.feedMessageTitle);
		TextView description = (TextView) rowView
				.findViewById(R.id.feedMessageDescription);
		ImageView imageView = (ImageView) rowView
				.findViewById(R.id.feedMessageIcon);
		
		//Set text according to the given feedMessage
		title.setText(values.get(position).getTitle());
		description.setText(values.get(position).getDescription());

		//Set the source of the icon according to the NBATeamTag of the FeedMessage
		int drawingRessource;
		switch (values.get(position).getNbaTeamTag()) {
		case ATL:
			drawingRessource = R.drawable.ic_atl;
			break;
		case BKN:
			drawingRessource = R.drawable.ic_bkn;
			break;
		case BOS:
			drawingRessource = R.drawable.ic_bos;
			break;
		case CHA:
			drawingRessource = R.drawable.ic_cha;
			break;
		case CHI:
			drawingRessource = R.drawable.ic_chi;
			break;
		case CLE:
			drawingRessource = R.drawable.ic_cle;
			break;
		case DAL:
			drawingRessource = R.drawable.ic_dal;
			break;
		case DEN:
			drawingRessource = R.drawable.ic_den;
			break;
		case DET:
			drawingRessource = R.drawable.ic_det;
			break;
		case GSW:
			drawingRessource = R.drawable.ic_gsw;
			break;
		case HOU:
			drawingRessource = R.drawable.ic_hou;
			break;
		case IND:
			drawingRessource = R.drawable.ic_ind;
			break;
		case LAC:
			drawingRessource = R.drawable.ic_lac;
			break;
		case LAL:
			drawingRessource = R.drawable.ic_lal;
			break;
		case MEM:
			drawingRessource = R.drawable.ic_mem;
			break;
		case MIA:
			drawingRessource = R.drawable.ic_mia;
			break;
		case MIL:
			drawingRessource = R.drawable.ic_mil;
			break;
		case MIN:
			drawingRessource = R.drawable.ic_min;
			break;
		case NOP:
			drawingRessource = R.drawable.ic_nop;
			break;
		case NYK:
			drawingRessource = R.drawable.ic_nyk;
			break;
		case OKC:
			drawingRessource = R.drawable.ic_okc;
			break;
		case ORL:
			drawingRessource = R.drawable.ic_orl;
			break;
		case PHI:
			drawingRessource = R.drawable.ic_phi;
			break;
		case PHX:
			drawingRessource = R.drawable.ic_phx;
			break;
		case POR:
			drawingRessource = R.drawable.ic_por;
			break;
		case SAC:
			drawingRessource = R.drawable.ic_sac;
			break;
		case SAS:
			drawingRessource = R.drawable.ic_sas;
			break;
		case TOR:
			drawingRessource = R.drawable.ic_tor;
			break;
		case UTA:
			drawingRessource = R.drawable.ic_uta;
			break;
		case WAS:
			drawingRessource = R.drawable.ic_was;
			break;
		case NBA:
			drawingRessource = R.drawable.ic_nba;
			break;
		case SUMMER_LEAGUE:
			drawingRessource = R.drawable.ic_summer_league;
			break;
		default:
			drawingRessource = R.drawable.ic_launcher;
			break;
		}

		imageView.setImageResource(drawingRessource);
		return rowView;
	}
}
