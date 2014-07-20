package fr.manciet.rss.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author François Manciet
 *
 */
public abstract class BaseFeedParser implements FeedParser {
	
	// names of the XML tags
    public static final String PUB_DATE = "pubDate";
    public static final String DESCRIPTION = "description";
    public static final String LINK = "link";
    public static final String TITLE = "title";
    public static final String GUID = "guid";
    public static final String ITEM = "item";
    
    final URL feedUrl;

    /**
     * Initiate the parser with the URL of the RSS flow
     * @param feedUrl : the URL of the RSS flow
     */
    protected BaseFeedParser(String feedUrl){
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Open the stream contained in the feedUrl
     * @return : an InputStream of the feedUrl
     */
    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
