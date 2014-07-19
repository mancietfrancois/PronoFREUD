package fr.manciet.rss.model.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NBATags {

	public static final String[] ATLANTA_TAGS = { "Atlanta", "Hawks" };
	public static final String[] BROOKLYN_TAGS = { "Brooklyn", "Nets" };
	public static final String[] BOSTON_TAGS = { "Boston", "Celtics" };
	public static final String[] CHARLOTTE_TAGS = { "Charlotte", "Hornets" };
	public static final String[] CHICAGO_TAGS = { "Chicago", "Bulls" };
	public static final String[] CLEVELAND_TAGS = { "Cleveland", "Cavaliers", "Cavs", "James", "Lebron" };
	public static final String[] DALLAS_TAGS = { "Dallas", "Mavericks" };
	public static final String[] DENVER_TAGS = { "Denver", "Nuggets" };
	public static final String[] DETROIT_TAGS = { "Detroit", "Pistons" };
	public static final String[] GOLDEN_STATE_TAGS = { "Golden State", "Warriors" };
	public static final String[] HOUSTON_TAGS = { "Houston", "Rockets" };
	public static final String[] INDIANA_TAGS = { "Indiana", "Pacers" };
	public static final String[] CLIPPERS_TAGS = { "Clippers", "Doc Rivers", "Chris Paul", "Blake Griffin" };
	public static final String[] LAKERS_TAGS = { "Lakers", "L.A.", "Julius Randle", "Kobe Bryant" };
	public static final String[] MEMPHIS_TAGS = { "Memphis", "Grizzlies" };
	public static final String[] MIAMI_TAGS = { "Miami", "Heat" };
	public static final String[] MILWAUKEE_TAGS = { "Milwaukee", "Bucks" };
	public static final String[] MINNESOTA_TAGS = { "Minnesota", "Timberwolves" };
	public static final String[] NEW_ORLEANS_TAGS = { "New Orleans", "Pelicans" };
	public static final String[] NEW_YORK_TAGS = { "New York", "Knicks" };
	public static final String[] OKLAHOMA_TAGS = { "Oklahoma", "Thunder" };
	public static final String[] ORLANDO_TAGS = { "Orlando", "Magic" };
	public static final String[] PHILADELPHIA_TAGS = { "Philadelphia", "Sixers", "Nerlens Noel" };
	public static final String[] PHOENIX_TAGS = { "Phoenix", "Suns" };
	public static final String[] PORTLAND_TAGS = { "Portland", "Trailblazers", "Blazers" };
	public static final String[] SACRAMENTO_TAGS = { "Sacramento", "Kings" };
	public static final String[] SAN_ANTONIO_TAGS = { "San Antonio", "Spurs" };
	public static final String[] TORONTO_TAGS = { "Toronto", "Raptors" };
	public static final String[] UTAH_TAGS = { "Utah", "Jazz" };
	public static final String[] WASHINGTON_TAGS = { "Washington", "Wizards" };
	
	public static final String[] SUMMER_LEAGUE_TAGS = { "Summer League", "Las Vegas" };
	public static final String[] NBA_TAGS = { "Adam Silver", "NBA", "Silver" };

	public static final Map<NBATeamTag, String[]> teamsKeywords;
	static {
		HashMap<NBATeamTag, String[]> aMap = new HashMap<>();
		aMap.put(NBATeamTag.ATL, ATLANTA_TAGS);
		aMap.put(NBATeamTag.BKN, BROOKLYN_TAGS);
		aMap.put(NBATeamTag.BOS, BOSTON_TAGS);
		aMap.put(NBATeamTag.CHA, CHARLOTTE_TAGS);
		aMap.put(NBATeamTag.CHI, CHICAGO_TAGS);
		aMap.put(NBATeamTag.CLE, CLEVELAND_TAGS);
		aMap.put(NBATeamTag.DAL, DALLAS_TAGS);
		aMap.put(NBATeamTag.DEN, DENVER_TAGS);
		aMap.put(NBATeamTag.DET, DETROIT_TAGS);
		aMap.put(NBATeamTag.GSW, GOLDEN_STATE_TAGS);
		aMap.put(NBATeamTag.HOU, HOUSTON_TAGS);
		aMap.put(NBATeamTag.IND, INDIANA_TAGS);
		aMap.put(NBATeamTag.LAC, CLIPPERS_TAGS);
		aMap.put(NBATeamTag.LAL, LAKERS_TAGS);
		aMap.put(NBATeamTag.MEM, MEMPHIS_TAGS);
		aMap.put(NBATeamTag.MIA, MIAMI_TAGS);
		aMap.put(NBATeamTag.MIL, MILWAUKEE_TAGS);
		aMap.put(NBATeamTag.MIN, MINNESOTA_TAGS);
		aMap.put(NBATeamTag.NOP, NEW_ORLEANS_TAGS);
		aMap.put(NBATeamTag.NYK, NEW_YORK_TAGS);
		aMap.put(NBATeamTag.OKC, OKLAHOMA_TAGS);
		aMap.put(NBATeamTag.ORL, ORLANDO_TAGS);
		aMap.put(NBATeamTag.PHI, PHILADELPHIA_TAGS);
		aMap.put(NBATeamTag.PHX, PHOENIX_TAGS);
		aMap.put(NBATeamTag.POR, PORTLAND_TAGS);
		aMap.put(NBATeamTag.SAC, SACRAMENTO_TAGS);
		aMap.put(NBATeamTag.SAS, SAN_ANTONIO_TAGS);
		aMap.put(NBATeamTag.TOR, TORONTO_TAGS);
		aMap.put(NBATeamTag.UTA, UTAH_TAGS);
		aMap.put(NBATeamTag.WAS, WASHINGTON_TAGS);

		aMap.put(NBATeamTag.SUMMER_LEAGUE, SUMMER_LEAGUE_TAGS);
		aMap.put(NBATeamTag.NBA, NBA_TAGS);
		teamsKeywords = Collections.unmodifiableMap(aMap);
	}
}
