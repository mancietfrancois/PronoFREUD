package fr.manciet.rss.model.extractor;

import java.util.ArrayList;

import fr.manciet.rss.model.utils.NBATags;
import fr.manciet.rss.model.utils.NBATeamTag;
import android.util.Log;

public class NBATagsExtractor {
	
	
	private ArrayList<NBATeamTag> getNBATeamTagFromString(String string) {
		ArrayList<NBATeamTag> tags = new ArrayList<>();
		for (NBATeamTag tag : NBATags.teamsKeywords.keySet()) {
			String [] teamKeyWords = NBATags.teamsKeywords.get(tag);
			for (int i = 0;	i < teamKeyWords.length; i++) {
				if (string.contains(teamKeyWords[i])) {
					Log.i("DEBUG", "Tag : " + tag);
					tags.add(tag);
				}
			}
		}
		return tags;
	}
	
	public NBATeamTag getNBATeamFromFeedTitleAndDescription(String description, String title) {
		ArrayList<NBATeamTag> tags = getNBATeamTagFromString(title);	
		if (tags.size() == 0) {
			tags = getNBATeamTagFromString(description);
		}
		Log.i("DEBUG", "Nombre de tags : " + tags.size());
		if (tags.contains(NBATeamTag.SUMMER_LEAGUE)) {
			return NBATeamTag.SUMMER_LEAGUE; 
		}
		if (tags.contains(NBATeamTag.NBA)) {
			return NBATeamTag.NBA;
		}
		return tags.get(0);
	}
}
