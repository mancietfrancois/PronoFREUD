package fr.manciet.rss.read;

import java.util.ArrayList;
import java.util.List;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;
import fr.manciet.rss.model.BaseFeedParser;
import fr.manciet.rss.model.FeedMessage;
import fr.manciet.rss.model.extractor.NBATagsExtractor;

/**
 * 
 * @author François Manciet
 *
 */
public class AndroidSaxFeedParser extends BaseFeedParser {
	
	private NBATagsExtractor nbaTagsExtractor;

	public AndroidSaxFeedParser(String feedUrl) {
		super(feedUrl);
		nbaTagsExtractor = new NBATagsExtractor();
	}

	/**
	 * Parse the file obtained from the input stream and return
	 * a list of all the feed messages
	 */
	public List<FeedMessage> parse() {
		final FeedMessage currentMessage = new FeedMessage();
		RootElement root = new RootElement("rss");
		final List<FeedMessage> messages = new ArrayList<FeedMessage>();
		Element channel = root.getChild("channel");
		Element item = channel.getChild(BaseFeedParser.ITEM);
		item.setEndElementListener(new EndElementListener() {
			public void end() {
				messages.add(currentMessage.copy());
			}
		});
		item.getChild(BaseFeedParser.TITLE).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setTitle(body);
					}
				});
		item.getChild(BaseFeedParser.LINK).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setLink(body);
					}
				});
		item.getChild(BaseFeedParser.DESCRIPTION).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setDescription(body);
					}
				});
		item.getChild(BaseFeedParser.PUB_DATE).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setDate(body);
					}
				});
		item.getChild(BaseFeedParser.GUID).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setGuid(body);
					}
				});
		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8,
					root.getContentHandler());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return extractNbaTags(messages);
	}
	
	/**
	 * Extract the nba tags contained in the list of messages
	 * @param messages : the messages extracted from the urlfeed
	 * @return : a list of messages with the nba tags setted
	 */
	public List<FeedMessage> extractNbaTags(List<FeedMessage> messages) {
		
		for (int i = 0; i < messages.size(); i++) {
			messages.get(i).extractTagFromDescription(nbaTagsExtractor);
		}
		return messages;
	}
}
