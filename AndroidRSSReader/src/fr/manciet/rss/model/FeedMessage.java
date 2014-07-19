package fr.manciet.rss.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.manciet.rss.model.extractor.NBATagsExtractor;
import fr.manciet.rss.model.utils.NBATeamTag;
import fr.manciet.rss.model.utils.Utils;

/*
 * Represents one RSS message
 */
public class FeedMessage implements Comparable<FeedMessage> {

	static SimpleDateFormat FORMATTER = 
	        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ", Locale.US);
	private String title;
	private String description;
	private String guid;
	private URL link;
	private Date date;
	private NBATeamTag nbaTeam;
	
	public FeedMessage(String title_, URL link_, String description_, Date date_) {
		this.title = title_;
		this.link = link_;
		this.description = description_;
		this.date = date_;
		this.nbaTeam = null;
	}

	public FeedMessage() {
		this.title = "";
		this.link = null;
		this.description = "";
		this.date = null;
		this.nbaTeam = null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = Utils.replaceEscapementCharacters(title);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = Utils.replaceEscapementCharacters(description);
	}

	public URL getLink() {
		return link;
	}

	public void setLink(String link) {
        try {
            this.link = new URL(Utils.replaceEscapementCharacters(link));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

	public Date getDate() {
		return date;
	}

	public void setDate(String date) {
        try {
            this.date = FORMATTER.parse(date.trim());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
		
	public void setNbaTeam(NBATeamTag nbaTeam_) {
		this.nbaTeam = nbaTeam_;
	}
	
	public NBATeamTag getNbaTeamTag() {
		return nbaTeam;
	}
	
	public void setGuid(String guid_){
		this.guid = guid_;
	}
	
	public String getGuid() {
		return this.guid;
	}
	
	public void extractTagFromDescription(NBATagsExtractor extractor) {
		nbaTeam = extractor.getNBATeamFromFeedTitleAndDescription(this.description, this.title);
	}

	@Override
	public String toString() {
		return "- [title = " + title + "\n   description = " + description
				+ "\n   link = " + link + "\n   Date = " + date
				+ "\n  ]\n";
	}

	@Override
	public int compareTo(FeedMessage another) {
		if (another == null) {
			return 1;
		}
        // sort descending, most recent first
        return another.date.compareTo(date);
	}

	public FeedMessage copy() {
		return new FeedMessage(title, link, description, date);
	}

}
