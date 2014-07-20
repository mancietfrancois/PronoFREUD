package fr.manciet.rss.model.extractor;

import java.util.ArrayList;

import fr.manciet.rss.model.utils.NBATeamTag;

/**
 * 
 * @author François Manciet
 *
 */
public abstract class TeamTagExtractor {
	
	/**
	 * Parse a string and build an ArrayList of the NBATeamTag contained in title and description
	 * @param description
	 * @param title
	 * @return the NBATeamTag contained in the String
	 */
	public abstract ArrayList<NBATeamTag> getNBATeamFromFeedTitleAndDescription(String title, String description);
}
