package fr.manciet.rss.model;

import java.util.List;

public interface FeedParser {
    List<FeedMessage> parse();
}
