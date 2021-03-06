package fr.manciet.rss.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.manciet.rss.model.extractor.TeamTagExtractor;
import fr.manciet.rss.model.utils.Utils;

/**
 * 
 * @author Fran�ois Manciet
 *
 */
public abstract class FeedMessage implements Comparable<FeedMessage> {

	static SimpleDateFormat FORMATTER = 
	        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ", Locale.US);
	protected String title;
	protected String description;
	protected String guid;
	protected URL link;
	protected Date date;

	
	/**
	 * Build a new FeedMessage with a title, a description, a link to a web page and a date
	 * @param title_ : the title of the RSS flow
	 * @param link_ : the link to Website containes in the RSS message
	 * @param description_ : the description of the RSS message
	 * @param date_ : the date of the message
	 */
	public FeedMessage(String title_, URL link_, String description_, Date date_) {
		this.title = title_;
		this.link = link_;
		this.description = description_;
		this.date = date_;
	}

	/**
	 * Build a new FeedMessage
	 */
	public FeedMessage() {
		this.title = "";
		this.link = null;
		this.description = "";
		this.date = null;
	}

	/**
	 * 
	 * @return the title of the message
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title : set the title of the message
	 */
	public void setTitle(String title) {
		this.title = Utils.replaceEscapementCharacters(title);
	}

	/**
	 * 
	 * @return the description of the message
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description : set the description of the message
	 */
	public void setDescription(String description) {
		this.description = Utils.replaceEscapementCharacters(description);
	}

	/**
	 * 
	 * @return the link of the message
	 */
	public URL getLink() {
		return link;
	}

	/**
	 * 
	 * @param link : set the link of the message
	 */
	public void setLink(String link) {
        try {
            this.link = new URL(Utils.replaceEscapementCharacters(link));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * 
	 * @return the date of the message
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 
	 * @param date : set the date of the message
	 */
	public void setDate(String date) {
        try {
            this.date = FORMATTER.parse(date.trim());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
	
	/**
	 * 
	 * @param guid : set the guid of the message
	 */
	public void setGuid(String guid_){
		this.guid = guid_;
	}
	
	/**
	 * 
	 * @return the guid of the message
	 */
	public String getGuid() {
		return this.guid;
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

	/**
	 * 
	 * @return a new FeedMessage which is the same a the FeedMessage used
	 */
	public abstract FeedMessage copy();
	
	public abstract void extractTagFromDescription(TeamTagExtractor extractor);

}
