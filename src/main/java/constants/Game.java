package constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class Game {
	public final static GridLayout CARD_LAYOUT = new GridLayout(2, 4);
	
	public final static Dimension START_MENU_SIZE = new Dimension(660, 192);
	public final static Dimension GAME_BOARD_SIZE = new Dimension(1920, 1040);
	public final static Dimension PLAYER_HEADER_SIZE = new Dimension(190, 65);
	public final static Dimension SPACER = new Dimension(180, 30);

	public final static Color CUSTOM_GRAY_1 = new Color(238, 238, 238);
	public final static Color CUSTOM_GRAY_2 = new Color(51, 51, 51);
	public final static Color CUSTOM_GRAY_3 = new Color(192, 192, 192);
	public final static Color CUSTOM_BLUE = new Color(153, 217, 234);

	public final static String INITIAL_NUM_RESEARCH_STATIONS = "1";
	public final static String INITIAL_NUM_OUTBREAKS = "0";
	public final static String INFECTION_RATE_TEXT = "2 2 2 3 3 4 4";

	public final static int TEXT_SIZE = 12;

	public final static Font FONT = new Font("Dialog", Font.BOLD, TEXT_SIZE);
	public final static Font FONT2 = new Font("Dialog", Font.BOLD, 9);

	public final static int TOP_PANEL_TEXT_Y = 75;

	public final static int BLUE_FILLER_WIDTH = 208;
	public final static int BLUE_FILLER_X = 203;
	public final static int BLUE_FILLER_Y = 115;

	public final static int SELECTED_CITY_X = 15;
	public final static int SELECTED_CITY_Y = 980;
	public final static int SELECTED_CITY_WIDTH = 180;
	public final static int SELECTED_CITY_HEIGHT = 45;

	public final static int OFFSET_0 = 0;
	public final static int OFFSET_1 = 1;
	public final static int OFFSET_2 = 2;
	public final static int OFFSET_5 = 5;
	public final static int OFFSET_8 = 8;
	public final static int OFFSET_10 = 10;
	public final static int OFFSET_15 = 15;
	public final static int OFFSET_20 = 20;
	public final static int OFFSET_30 = 30;
	public final static int OFFSET_40 = 40;

	public final static int TURN_RADIUS = 14;
	public final static int TURN_PANEL_X = 213;
	public final static int TURN_PANEL_Y = 897;
	public final static int TURN_PANEL_WIDTH = 190;
	public final static int TURN_PANEL_HEIGHT = 60;
	public final static int TURN_PANEL_TEXT_X = 273;
	public final static int TURN_PANEL_TEXT_Y = 922;

	public final static int RESEARCH_COUNT_X = 1070;
	public final static int OUTBREAK_COUNT_X = 1175;
	public final static int INFECTION_RATE_X = 1280;

	public final static int FIRST_PLAYER_INDEX = 0;
	public final static int SECOND_PLAYER_INDEX = 1;
	public final static int THIRD_PLAYER_INDEX = 2;

	public final static int FORECAST_CARDS = 6;
	public final static int MAX_CARDS = 7;
	public final static int MAX_NUM_OUTBREAKS = 7;
	public final static int NUM_INFECTIONS = 4;
	public final static int ACTION_COUNT = 4;
	public final static int MAX_RESEARCH_COUNT = 6;

	public final static int TWO_PLAYERS = 2;
	public final static int THREE_PLAYERS = 3;
	public final static int FOUR_PLAYERS = 4;
	
	public final static int INTRODUCTORY_DIFFICULTY = 4;
	public final static int STANDARD_DIFFICULTY = 5;
	public final static int HEROIC_DIFFICULTY = 6;
	
	public final static int STARTING_CARD_HELPER = 6;
	public final static int HALF = 2;
	
	public final static String MAP_IMG = "images/map.png";
	public final static String BIOHAZARD_IMG = "images/biohazard.PNG";
	public final static String OUTBREAK_IMG = "images/outbreak.png";
	public final static String PANDEMIC_IMG = "images/pandemic.png";
	public final static String PANDEMIC_RULES = "files/pandemic.pdf";
	public final static String RESEARCH_STATION_IMG = "images/researchStation.png";
	public final static String BMP_FILE = ".bmp";
	public final static String PNG_FILE = ".png";
	public final static String PANDEMIC = "Pandemic";

	public static String INTRODUCTORY = "Introductory";
	public static String STANDARD = "Standard";
	public static String HEROIC = "Heroic";
	
	public static String PROCEED_WITH = "Proceed with ";
	public static String PLAYERS_AT = " players at ";
	public static String DIFFICULTY = " difficulty?";
	
	public static String SELECT_NUM_PLAYERS = "Select the number of players";
	public static String SELECT_GAME_DIFFICULTY = "Select the game difficulty";
	public static String PLAY = "Play";
	public static String RULES = "Rules";
	public static String EXIT = "Exit";
	public static String TWO_PLAYERS_TEXT = "Two Players";
	public static String THREE_PLAYERS_TEXT = "Three Players";
	public static String FOUR_PLAYERS_TEXT = "Four Players";
	public static String PROCEED = "Proceed";

	public static String INFECTION_DECK = "Infection Deck";
	public static String PLAYER_DECK = "Player Deck";
	public static String DISCARD_PILE = "Discard Pile";
	
	public static String MEDIC = "Medic";
	public static String DISPATCHER = "Dispatcher";
	public static String QUARANTINE_SPECIALIST = "Quarantine Specialist";
	public static String CONTINGENCY_PLANNER = "Contingency Planner";
	public static String RESEARCHER = "Researcher";
	public static String SCIENTIST = "Scientist";
	public static String OPERATIONS_EXPERT = "Operations Expert";
	
	public static final List<String> CHARACTERS_NAME_LIST = new ArrayList<>(Arrays.asList(MEDIC, DISPATCHER,
			QUARANTINE_SPECIALIST, CONTINGENCY_PLANNER, RESEARCHER, SCIENTIST, OPERATIONS_EXPERT));
	
	public static String MOVE_BUTTON = "Move";
	public static String TREAT_BUTTON = "Treat";
	public static String CURE_BUTTON = "Cure";
	public static String BUILD_BUTTON = "Build";
	public static String SHARE_BUTTON = "Share";
	public static String PASS_BUTTON = "Pass";
	public static String PLAY_EVENT_BUTTON = "Play Event Card";
	public static String NO_SELECTED_CITY = "No Selected City";

	public static String LOCALE_INFO = "Please select your language: ";

	public static String EVENTS = "Event";

	public static String SELECT_DISEASE = "Select a disease to treat:";
	public static String NO_DISEASES = "No diseases to treat at current location!";

	public static String BLUE = "Blue";
	public static String YELLOW = "Yellow";
	public static String BLACK = "Black";
	public static String RED = "Red";

	public static String SELECT_CARD_SHARE = "Select a card to share";
	public static String SHARE = "Share";
	public static String NO_SHARED = "This can not be shared";
	public static String NO_PLAYERS_TO_SHARE_WITH = "There are no players to share with";
	public static String NO_SHARE_SELF = "You do not want to share with yourself";
	public static String GAME_OVER = "GAME OVER";
	public static String YOU_WON = "YOU WON!";

	public static String LOST = "You Lost!";
	public static String LOST_GAME = "Lost Game!";
	public static String WON = "You Won!";
	public static String WON_GAME = "Won Game!";
	
	public static String TREAT = "Treat";
	public static String SELECT_DISEASE_TO_TREAT = "Select a diseease to treat:";
	public static String NO_DISEASES_TO_TREAT = "No diseases to treat at current location!";
	
	public static String CURE = "Cure";
	public static String SELECT_DISEASE_TO_CURE = "Select a disease to cure:";
	public static String ALREADY_ERADICATED = "Disease is already eradicated!";
	public static String ALREADY_CURED = "Disease is already cured!";
	public static String NOT_ENOUGH_CARDS_TO_CURE = "Not enough cards to cure selected disease!";
	public static String SELECT_CARD_TO_KEEP = "Select a card to keep in your hand: ";
	public static String NOT_AT_RESEARCH_STATION = "Must be at research station to cure disease!";
	
	public static String EVENT_CARD = "Choose Event";
	public static String SELECT_EVENT_CARD = "Select and event card to play:";
	public static String NO_EVENT_CARDS = "There are no event cards to play!";

	public static String FORECAST_INFO = "Selected card will be placed on top of the deck:";
	public static String SELECT_PLAYER_TO_MOVE = "Select player to move:";
	public static String SELECT_INFECTION_CARD = "Select a card to remove from the infection discard deck";

	public static String PLAYER_NAME = "Player Name";
	public static String ENTER_PLAYER_NAME = "Enter the name for Player";
	public static String SELECT_CHARACTER = "Select Character";
	public static String SELECT_CARD_FROM_INFECTION = "Select card to remove from infection deck: ";
	public static String SELECT_CARD_PLACED_ON_TOP = "Selected card will be placed on top of the deck:";

	public static void updateConstants(ResourceBundle resourceBundle) {
		INFECTION_DECK = resourceBundle.getString("INFECTION_DECK");
		PLAYER_DECK = resourceBundle.getString("PLAYER_DECK");
		DISCARD_PILE = resourceBundle.getString("DISCARD_PILE");

		MOVE_BUTTON = resourceBundle.getString("MOVE_BUTTON");
		TREAT_BUTTON = resourceBundle.getString("TREAT_BUTTON");
		CURE_BUTTON = resourceBundle.getString("CURE_BUTTON");
		BUILD_BUTTON = resourceBundle.getString("BUILD_BUTTON");
		SHARE_BUTTON = resourceBundle.getString("SHARE_BUTTON");
		PASS_BUTTON = resourceBundle.getString("PASS_BUTTON");
		PLAY_EVENT_BUTTON = resourceBundle.getString("PLAY_EVENT_BUTTON");
		NO_SELECTED_CITY = resourceBundle.getString("NO_SELECTED_CITY");

		LOCALE_INFO = resourceBundle.getString("LOCALE_INFO");

		EVENTS = resourceBundle.getString("EVENTS");
		TREAT = resourceBundle.getString("TREAT");

		SELECT_EVENT_CARD = resourceBundle.getString("SELECT_EVENT_CARD");
		NO_EVENT_CARDS = resourceBundle.getString("NO_EVENT_CARDS");
		SELECT_DISEASE = resourceBundle.getString("SELECT_DISEASE");
		NO_DISEASES = resourceBundle.getString("NO_DISEASES");
		BLUE = resourceBundle.getString("BLUE");
		YELLOW = resourceBundle.getString("YELLOW");
		BLACK = resourceBundle.getString("BLACK");
		RED = resourceBundle.getString("RED");
		SELECT_CARD_SHARE = resourceBundle.getString("SELECT_CARD_SHARE");
		SHARE = resourceBundle.getString("SHARE");
		NO_SHARED = resourceBundle.getString("NO_SHARED");
		NO_PLAYERS_TO_SHARE_WITH = resourceBundle.getString("NO_PLAYERS_TO_SHARE_WITH");
		NO_SHARE_SELF = resourceBundle.getString("NO_SHARE_SELF");
		GAME_OVER = resourceBundle.getString("GAME_OVER");
		YOU_WON = resourceBundle.getString("YOU_WON");
		
		INTRODUCTORY = resourceBundle.getString("INTRODUCTORY");
		STANDARD = resourceBundle.getString("STANDARD");
		HEROIC = resourceBundle.getString("HEROIC");
		
		PROCEED_WITH = resourceBundle.getString("PROCEED_WITH");
		PLAYERS_AT = resourceBundle.getString("PLAYERS_AT");
		DIFFICULTY = resourceBundle.getString("DIFFICULTY");
		
		SELECT_NUM_PLAYERS = resourceBundle.getString("SELECT_NUM_PLAYERS");
		SELECT_GAME_DIFFICULTY = resourceBundle.getString("SELECT_GAME_DIFFICULTY");
		PLAY = resourceBundle.getString("PLAY");
		RULES = resourceBundle.getString("RULES");
		EXIT = resourceBundle.getString("EXIT");
		TWO_PLAYERS_TEXT = resourceBundle.getString("TWO_PLAYERS_TEXT");
		THREE_PLAYERS_TEXT = resourceBundle.getString("THREE_PLAYERS_TEXT");
		FOUR_PLAYERS_TEXT = resourceBundle.getString("FOUR_PLAYERS_TEXT");
		PROCEED = resourceBundle.getString("PROCEED");
		
		SELECT_CARD_FROM_INFECTION = resourceBundle.getString("SELECT_CARD_FROM_INFECTION");
		SELECT_CARD_PLACED_ON_TOP = resourceBundle.getString("SELECT_CARD_PLACED_ON_TOP");
		SELECT_PLAYER_TO_MOVE = resourceBundle.getString("SELECT_PLAYER_TO_MOVE");
		
		LOST = resourceBundle.getString("LOST");
		LOST_GAME = resourceBundle.getString("LOST_GAME");
		WON = resourceBundle.getString("WON");
		WON_GAME = resourceBundle.getString("WON_GAME");

		SELECT_DISEASE_TO_TREAT = resourceBundle.getString("SELECT_DISEASE_TO_TREAT");
		NO_DISEASES_TO_TREAT = resourceBundle.getString("NO_DISEASES_TO_TREAT");

		CURE = resourceBundle.getString("CURE");
		SELECT_DISEASE_TO_CURE = resourceBundle.getString("SELECT_DISEASE_TO_CURE");
		ALREADY_ERADICATED = resourceBundle.getString("ALREADY_ERADICATED");
		ALREADY_CURED = resourceBundle.getString("ALREADY_CURED");
		NOT_ENOUGH_CARDS_TO_CURE = resourceBundle.getString("NOT_ENOUGH_CARDS_TO_CURE");
		SELECT_CARD_TO_KEEP = resourceBundle.getString("SELECT_CARD_TO_KEEP"); 
		NOT_AT_RESEARCH_STATION = resourceBundle.getString("NOT_AT_RESEARCH_STATION");

		EVENT_CARD = resourceBundle.getString("EVENT_CARD");

		FORECAST_INFO = resourceBundle.getString("FORECAST_INFO");
		SELECT_INFECTION_CARD = resourceBundle.getString("SELECT_INFECTION_CARD");

		PLAYER_NAME = resourceBundle.getString("PLAYER_NAME");
		ENTER_PLAYER_NAME = resourceBundle.getString("ENTER_PLAYER_NAME");
		SELECT_CHARACTER = resourceBundle.getString("SELECT_CHARACTER");
	}
}