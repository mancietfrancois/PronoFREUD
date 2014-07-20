package fr.manciet.rss.model.utils;

/**
 * 
 * @author François Manciet
 *
 */
public class Utils {
	/**
	 * Change illegal characters contained in the stringToBeEscaped
	 * @param stringToBeEscaped : the string to be escaped
	 * @return: stringToBeEscaped without illegal characters
	 */
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
