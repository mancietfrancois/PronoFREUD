package fr.manciet.rss.model.utils;

public class Utils {
	
	public static String replaceEscapementCharacters(String stringToBeEscaped) {
		while (stringToBeEscaped.contains("\n")) {
			stringToBeEscaped = stringToBeEscaped.replace("\n", "");
		}
		while (stringToBeEscaped.contains("\t")) {
			stringToBeEscaped = stringToBeEscaped.replace("\t", "");
		}
		stringToBeEscaped = stringToBeEscaped.replace("&amp;apos;", "'");
		return stringToBeEscaped;
	}

}
