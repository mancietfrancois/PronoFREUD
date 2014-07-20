package fr.manciet.rss.model;

import java.net.URL;
import java.util.Date;

import fr.manciet.rss.model.extractor.TeamTagExtractor;
import fr.manciet.rss.model.utils.NBATeamTag;

/**
 * 
 * @author François Manciet
 *
 */
public class NBAFeedMessage extends FeedMessage {
	
	private NBATeamTag nbaTeam;
	
	public NBAFeedMessage(){
		super();
		this.nbaTeam = null;
	}
	
	public NBAFeedMessage(String title, URL link, String description, Date date) {
		super(title, link, description, date);
	}

	/**
	 * 
	 * @param nbaTeam_ : set the nbaTeam of the message
	 */
	public void setNbaTeam(NBATeamTag nbaTeam_) {
		this.nbaTeam = nbaTeam_;
	}
	
	/**
	 * 
	 * @return the NbaTeamTag of the message
	 */
	public NBATeamTag getNbaTeamTag() {
		return nbaTeam;
	}
	
	/**
	 * Extract the NbaTeam of the message
	 * @param extractor : the NBATagsExtractor used to extract the team contained in the message
	 */
	public void extractTagFromDescription(TeamTagExtractor extractor) {
		nbaTeam = extractor.getNBATeamFromFeedTitleAndDescription(super.description, super.title).get(0);
	}

	@Override
	public FeedMessage copy() {
		return new NBAFeedMessage(title, link, description, date);
	}

}
