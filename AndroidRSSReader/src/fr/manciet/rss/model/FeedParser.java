package fr.manciet.rss.model;

import java.util.List;

/**
 * 
 * @author François Manciet
 *
 */
public interface FeedParser {
    List<FeedMessage> parse();
}
