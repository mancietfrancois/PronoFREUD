package fr.manciet.rss.parser;

import java.util.List;

import fr.manciet.rss.model.FeedMessage;

/**
 * 
 * @author François Manciet
 *
 */
public interface FeedParser {
	
	/**
	 * Parse a message accordlingly to the type of the FeedMessage
	 * @param messageType
	 * @return
	 */
    List<FeedMessage> parse(FeedMessage messageType);
}
