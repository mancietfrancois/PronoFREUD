package fr.manciet.rss.model.extractor;

import java.util.ArrayList;

import fr.manciet.rss.model.utils.NBATags;
import fr.manciet.rss.model.utils.NBATeamTag;
import android.util.Log;

/**
 * 
 * @author François Manciet
 *
 */
public class NBATagsExtractor {
	
	/**
	 * Parse a string and build an ArrayList of the NBATeamTag contained in the String
	 * @param string : the string to be parsed
	 * @return: an ArrayList of the NBATeamTag contained in the String
	 */
	private ArrayList<NBATeamTag> getNBATeamTagFromString(String string) {
		ArrayList<NBATeamTag> tags = new ArrayList<>();
		for (NBATeamTag tag : NBATags.teamsKeywords.keySet()) {
			String [] teamKeyWords = NBATags.teamsKeywords.get(tag);
			for (int i = 0;	i < teamKeyWords.length; i++) {
				if (string.contains(teamKeyWords[i])) {
					Log.i("DEBUG", "Tag spotted! : " + tag);
					tags.add(tag);
				}
			}
		}
		return tags;
	}
	
	/**
	 * Parse a string and build an ArrayList of the NBATeamTag contained in title and description
	 * @param description
	 * @param title
	 * @return an ArrayList of the NBATeamTag contained in the String
	 */
	public NBATeamTag getNBATeamFromFeedTitleAndDescription(String description, String title) {
		ArrayList<NBATeamTag> tags = getNBATeamTagFromString(title);	
		if (tags.size() == 0) {
			tags = getNBATeamTagFromString(description);
		}
		Log.i("DEBUG", "Number of tags :" + tags.size());
		if (tags.contains(NBATeamTag.SUMMER_LEAGUE)) {
			return NBATeamTag.SUMMER_LEAGUE; 
		}
		if (tags.contains(NBATeamTag.NBA)) {
			return NBATeamTag.NBA;
		}
		if (!tags.isEmpty()) {
			return tags.get(0);
		}
		Log.i("DEBUG", "No tags were found. NBA Tag is associated to this FeedMessage");
		return NBATeamTag.NBA;
	}
}
