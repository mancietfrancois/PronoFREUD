package fr.manciet.rss.model;

import java.util.List;

/**
 * 
 * @author Fran�ois Manciet
 *
 */
public interface FeedParser {
    List<FeedMessage> parse();
}
