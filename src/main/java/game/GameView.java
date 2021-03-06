package game;

import static constants.Card.*;
import static constants.Character.*;
import static constants.City.*;
import static constants.Disease.*;
import static constants.Game.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cards.CardModel;
import characters.AbstractCharacterController;
import characters.CharacterFrontEndModel;
import characters.CharacterModel;
import characters.CharacterView;
import city.CityController;
import city.CityFrontEndModel;
import city.CityModel;
import city.CityView;
import diseases.DiseaseController;
import diseases.DiseaseFrontEndModel;
import diseases.DiseaseModel;
import diseases.DiseaseView;

public class GameView extends JFrame implements ActionListener {
	private JButton moveButton = new JButton(MOVE_BUTTON);
	private JButton treatButton = new JButton(TREAT_BUTTON);
	private JButton cureButton = new JButton(CURE_BUTTON);
	private JButton buildButton = new JButton(BUILD_BUTTON);
	private JButton shareButton = new JButton(SHARE_BUTTON);
	private JButton passButton = new JButton(PASS_BUTTON);
	private JButton playEventCardButton = new JButton(PLAY_EVENT_BUTTON);
	
	GameController controller;
	CityController cityController;
	DiseaseController diseaseController;
	GameModel model;
	DiseaseModel blueDisease, yellowDisease, blackDisease, redDisease;
	BorderLayout layout;
	JLabel background;
	JPanel gameInfoPanel, playerInfoPanel, playerActionPanel, mapPanel;
	List<CharacterModel> players;
	CityView cities;
	CityFrontEndModel selectedCity;
	boolean isSelectedCitySet;
	List<CardModel> eventCards;

	public GameView(GameController controller) {
		this.controller = controller;
		this.cityController = controller.getCityController();
		this.diseaseController = controller.getDiseaseController();
		this.model = controller.getGameModel();
		this.layout = new BorderLayout();
		this.background = new JLabel();
		this.gameInfoPanel = new JPanel();
		this.playerInfoPanel = new JPanel();
		this.playerActionPanel = new JPanel();
		this.mapPanel = new JPanel();
		this.players = this.model.getCharacters();
		this.isSelectedCitySet = false;
		this.cities = new CityView(this.cityController);
		this.blueDisease = this.diseaseController.getBlueDisease();
		this.yellowDisease = this.diseaseController.getYellowDisease();
		this.blackDisease = this.diseaseController.getBlackDisease();
		this.redDisease = this.diseaseController.getRedDisease();
		this.eventCards = new ArrayList<>();
		
		DiseaseModel blueDisease = new DiseaseModel();
		CityModel city = new CityModel(NO_SELECTED_CITY, blueDisease);
		this.selectedCity = new CityFrontEndModel(city);
		this.selectedCity.setColor(CUSTOM_GRAY_1);
		
		this.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
			public void mouseClicked(MouseEvent event) {}

			@Override
			public void mousePressed(MouseEvent click) {
				determineClickedCity(click.getX(), click.getY());
			}		
		});
	}
	
	public void viewGame() {
		drawGameInfo();
		drawPlayerInfo();
		drawPlayerActions();
		drawMap();
		updateFrame();
	}
	
	private void drawGameInfo() {
		DiseaseView cureMarkers = new DiseaseView();
		GameInfoView diseaseInfo = new GameInfoView();
		
		cureMarkers.drawPanel();
		diseaseInfo.drawPanel();	
		
		this.gameInfoPanel.add(cureMarkers);
		this.gameInfoPanel.add(diseaseInfo);
	}
	
	private void drawPlayerInfo() {
		BoxLayout playerInfoLayout = new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS);
		
		this.playerInfoPanel.setLayout(playerInfoLayout);
				
		for (CharacterModel character : this.players) {
			CharacterView view = new CharacterView(character);
			
			view.drawPanel();
			
			this.playerInfoPanel.add(view);
		}
	}
	
	private void drawPlayerActions() {
		FlowLayout actionLayout = new FlowLayout(FlowLayout.LEFT, OFFSET_15, OFFSET_20);
		JLabel spacer = new JLabel();
		
		spacer.setPreferredSize(SPACER);
		spacer.setBorder(BorderFactory.createLineBorder(CUSTOM_GRAY_1));
		
		this.moveButton.addActionListener(this);
		this.treatButton.addActionListener(this);
		this.cureButton.addActionListener(this);
		this.buildButton.addActionListener(this);
		this.shareButton.addActionListener(this);
		this.passButton.addActionListener(this);
		this.playEventCardButton.addActionListener(this);
		
		this.playerActionPanel.setLayout(actionLayout);
		this.playerActionPanel.add(spacer);
		this.playerActionPanel.add(this.moveButton);
		this.playerActionPanel.add(this.treatButton);
		this.playerActionPanel.add(this.cureButton);
		this.playerActionPanel.add(this.buildButton);
		this.playerActionPanel.add(this.shareButton);
		this.playerActionPanel.add(this.passButton);
		this.playerActionPanel.add(this.playEventCardButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JButton) {
			Object button = event.getSource();
			
			if (button == this.moveButton) {
				this.controller.moveCharacter(this.controller.getCurrentPlayer(), 
						this.selectedCity.getCityModel());
			} else if (button == this.treatButton) {
				treat();
			} else if (button == this.cureButton) {
				cure();
			} else if (button == this.buildButton) {
				this.controller.buildResearchStation();
			} else if (button == this.shareButton) {
				share();
			} else if (button == this.passButton) {
				this.controller.endOfTurn();
			} else if (button == this.playEventCardButton) {
				playEventCard();
			}		

			repaint();
		}
	}
	
	private void playEventCard(){
		if (eventCards.size() > 0) {
			String[] eventCardsToPlay = new String[eventCards.size()];
			
			for (int i = 0; i < this.eventCards.size();i++) {
				eventCardsToPlay[i] = eventCards.get(i).getName();
			}
			
			String cardString = (String) JOptionPane.showInputDialog(this, SELECT_EVENT_CARD, EVENT_CARD,
					JOptionPane.DEFAULT_OPTION, null, eventCardsToPlay, eventCardsToPlay[0]);
			
			if (cardString != null) {
				getEventCardFromString((String) cardString);
			}	
		} else {
			JOptionPane.showMessageDialog(this, NO_EVENT_CARDS);
		}
	}
	
	private void getEventCardFromString(String eventCardString) {
		boolean playEvent = true;
		for (int i = 0; i < this.eventCards.size(); i++) {
			if (eventCardString.equals(eventCards.get(i).getName())) {
				if (eventCardString.equals(AIRLIFT)) {
					playEvent = selectPlayerToAirlift();
				} else if (eventCardString.equals(RESILIENT_POPULATION)) {
					playEvent = selectInfectionCardToRemove();
				} else if (eventCardString.equals(GOVERNMENT_GRANT)) {
					playEvent = this.model.getSelectedCity() != null;
				}
				
				if (playEvent) {
					this.controller.playEventCard(eventCards.get(i));
				}
				
				if (eventCardString.equals(FORECAST)) {
					getTopCardsForForecast();
				}
			}
		}
	}
	
	private boolean selectInfectionCardToRemove() {
		List<CardModel> discard = this.controller.getInfectionDeckController().getDiscardedCards();
		List<String> names = new ArrayList<>();
		boolean cardSelected = false;
		
		for (CardModel card : discard) {
			names.add(card.getName());
		}
				
		String cardToRemove = (String) JOptionPane.showInputDialog(this, SELECT_INFECTION_CARD,
				RESILIENT_POPULATION, JOptionPane.DEFAULT_OPTION, null, names.toArray(),
				names.toArray()[0]);
		
		if (cardToRemove != null) {
			CityModel returnCity = this.cityController.getCityByName(cardToRemove);
			CardModel selectedCard =
					this.controller.getInfectionDeckController().getCityToCardMap().get(returnCity);
			
			this.model.setCardToRemoveFromInfectionDeck(selectedCard);
			cardSelected = true;
		}
		
		return cardSelected;
	}
	
	private void getTopCardsForForecast() {
		List<CardModel> listOfTopCards = this.model.getForecastCards();
		List<CardModel> newOrderedList = new ArrayList<>();
		boolean citySelected = true;
		
		while (citySelected) {
			if (listOfTopCards.size() <= 1) {
				newOrderedList.add(listOfTopCards.get(0));
				citySelected = false;
			} else {
				CardModel selectedCard = selectCardFromTopOfInfection(listOfTopCards);
				
				if (selectedCard != null) {
					newOrderedList.add(selectedCard);
					listOfTopCards.remove(selectedCard);
				}
			}
		}
		
		for (int i = 0; i < newOrderedList.size(); i++) {
			this.controller.forecastReturnCard(newOrderedList.get(i));
		}
	}
	
	private CardModel selectCardFromTopOfInfection(List<CardModel> listOfTopCards) {
		String[] topCardsArray = new String[listOfTopCards.size()];
		
		for(int i = 0; i < listOfTopCards.size();i++){
			topCardsArray[i] = listOfTopCards.get(i).getName();
		}
		
		String cardName = (String) JOptionPane.showInputDialog(this, FORECAST_INFO, 
				FORECAST, JOptionPane.DEFAULT_OPTION, null, topCardsArray, topCardsArray[0]);
		
		return this.controller.cardNameToCard(cardName, listOfTopCards);
	}

	private boolean selectPlayerToAirlift(){
		String[] playersToChoose = new String[this.controller.getPlayers().size()];
		boolean airliftSelected = false;
		
		for(int i = 0; i < this.controller.getPlayers().size(); i++){
			playersToChoose[i] = controller.getPlayers().get(i).getCharacterModel().getName();
		}
		
		String playerString = (String) JOptionPane.showInputDialog(this, SELECT_PLAYER_TO_MOVE, 
				AIRLIFT, JOptionPane.DEFAULT_OPTION, null, playersToChoose, playersToChoose[0]);
		
		if (playerString != null && this.model.getSelectedCity() != null) {
			getCharacterFromString(playerString);
			airliftSelected = true;
		}
		
		return airliftSelected;
	}
	
	private void getCharacterFromString(String playerString) {
		for (int i = 0; i < this.players.size(); i++) {
			if (this.controller.getPlayers().get(i).getCharacterModel().getName().equals(playerString)) {
				this.model.setSelectedCharacter(this.controller.getPlayers().get(i));
			}
		}
	}

	private void treat() {
		List<String> diseaseList = updateCurrentCityDiseaseList();
		String[] diseases = new String[diseaseList.size()];
		
		for (int i = 0; i < diseases.length; i++) {
			diseases[i] = diseaseList.get(i);
		}
		
		if (diseases.length > 1) {
			String treated = (String) JOptionPane.showInputDialog(this, SELECT_DISEASE_TO_TREAT, 
					TREAT, JOptionPane.DEFAULT_OPTION, null, diseases, diseases[0]);
			
			if (!treated.equals(null)) {
				treatDisease(treated);
			}
		} else if (diseases.length == 1) {
			treatDisease(diseases[0]);
		} else {
			JOptionPane.showMessageDialog(this, String.format("<html><center>%s</center></html",
					NO_DISEASES_TO_TREAT), TREAT, JOptionPane.INFORMATION_MESSAGE); 
		}
	}
	
	private List<String> updateCurrentCityDiseaseList() {
		List<String> diseases = new ArrayList<>();
		String name = this.controller.getCurrentPlayer().getCharactersCurrentCity().getName();
		int blue = this.cityController.getCityByName(name).getCubesByDisease(this.blueDisease);
		int yellow = this.cityController.getCityByName(name).getCubesByDisease(this.yellowDisease);
		int black = this.cityController.getCityByName(name).getCubesByDisease(this.blackDisease);
		int red = this.cityController.getCityByName(name).getCubesByDisease(this.redDisease);
		
		if (blue > 0) {
			diseases.add(BLUE);
		}
		
		if (yellow > 0) {
			diseases.add(YELLOW);
		}
		
		if (black > 0) {
			diseases.add(BLACK);
		}
		
		if (red > 0) {
			diseases.add(RED);
		}
		
		return diseases;
	}
	
	private void treatDisease(String selectedDisease) {	
		if (selectedDisease.equals(BLUE)) {
			this.controller.treatCity(this.blueDisease);
		} else if (selectedDisease.equals(YELLOW)) {
			this.controller.treatCity(this.yellowDisease);
		} else if (selectedDisease.equals(BLACK)) {
			this.controller.treatCity(this.blackDisease);
		} else if (selectedDisease.equals(RED)) {
			this.controller.treatCity(this.redDisease);
		}
	}
	
	private void share(){
		Set<CardModel> setOfCities = this.controller.getCurrentPlayer().getCharacterModel().getHandOfCards();
		ArrayList<CardModel> listOfCities = new ArrayList<CardModel>(setOfCities);
		String[] cardsArray = new String[listOfCities.size()];
		
		for(int i = 0; i < listOfCities.size(); i++){
			cardsArray[i] = listOfCities.get(i).getName();
		}
		
		if (listOfCities.size() > 1) {
			Object cardToShare = JOptionPane.showInputDialog(this, SELECT_CARD_SHARE, 
					SHARE, JOptionPane.DEFAULT_OPTION, null, cardsArray, cardsArray[0]);
			
			if (!cardToShare.equals(null)) {
				CardModel card = stringToCard((String) cardToShare);
				choosePlayerToShareWith(card);
			}
		} else {
			JOptionPane.showMessageDialog(this, NO_SHARED); 
		}
	}
	
	private CardModel stringToCard(String cardAsString){
		Set<CardModel> setOfCities = this.controller.getCurrentPlayer().getCharacterModel().getHandOfCards();
		ArrayList<CardModel> listOfCities = new ArrayList<CardModel>(setOfCities);
		
		for(int i = 0; i < listOfCities.size(); i++){
			if(listOfCities.get(i).getName().equals(cardAsString)){
				return listOfCities.get(i);
			}
		}
		
		return new CardModel("", null);
	}
	
	private void choosePlayerToShareWith(CardModel cardToShare){
		boolean cityOccupiedByOtherPlayer = false;
		if(!cardToShare.getType()
				.equals(CardModel.CardType.PLAYER)){
			JOptionPane.showMessageDialog(this, NO_SHARED);
			return;
		}
		List<AbstractCharacterController> playerList = this.controller.getPlayers();
		String[] playerArray = new String[playerList.size()];
		for(int i = 0; i < playerList.size(); i++){
			if(playerList.get(i).getCharactersCurrentCity().equals(
					this.controller.getCurrentPlayer().getCharactersCurrentCity())){
				playerArray[i] = playerList.get(i).getCharacterModel().getName();
				cityOccupiedByOtherPlayer = true;
			}
		}
		if(!cityOccupiedByOtherPlayer){
			JOptionPane.showMessageDialog(this, NO_PLAYERS_TO_SHARE_WITH);
		}
		if (playerList.size() > 1) {
			Object chosenPlayer = JOptionPane.showInputDialog(this, SELECT_CARD_SHARE, 
					SHARE, JOptionPane.DEFAULT_OPTION, null, playerArray, playerArray[0]);
			
			if (!cardToShare.equals(null)) {
				AbstractCharacterController player = stringToPlayer((String) chosenPlayer);
				if(!player.equals(this.controller.getCurrentPlayer())){
					shareCard(player, cardToShare);
				} else {
					JOptionPane.showMessageDialog(this, NO_SHARE_SELF);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, NO_SHARED); 
		}
	}
	
	private void shareCard(AbstractCharacterController player, CardModel cardToShare){
		this.controller.shareKnowledge(player, cardToShare);
	}
	
	private AbstractCharacterController stringToPlayer(String chosenPlayer){
		AbstractCharacterController player = this.controller.getCurrentPlayer();
		List<AbstractCharacterController> playerList = this.controller.getPlayers();
		for(int i = 0; i < playerList.size(); i++){
			if(playerList.get(i).getCharacterModel().getName().equals(chosenPlayer)){
				return playerList.get(i);
			}
		}
		return player;
	}
	
	private void cure() {
		Set<CardModel> playerHand = this.controller.getCurrentPlayer().getCharacterModel().getHandOfCards();
		Set<CardModel> blueCards = new HashSet<>();
		Set<CardModel> yellowCards = new HashSet<>();
		Set<CardModel> blackCards = new HashSet<>();
		Set<CardModel> redCards = new HashSet<>();
		String[] cures = new String[]{BLUE, YELLOW, BLACK, RED};
		boolean didCure = false;
		boolean alreadyCured = false;
		boolean alreadyEradicated = false;
		boolean atResearchStation = 
				this.controller.getCurrentPlayer().getCharacterModel().isAtResearchStation();
		
		for (CardModel card : playerHand) {
			if (card.getType().equals(CardModel.CardType.PLAYER)) {
				Color color = this.cities.getCityToDraw(card.getName()).getColor();
				if (color.equals(Color.BLUE)) {
					blueCards.add(card);
				} else if (color.equals(Color.YELLOW)) {
					yellowCards.add(card);
				} else if (color.equals(Color.BLACK)) {
					blackCards.add(card);
				} else {
					redCards.add(card);
				}
			}
		}
		
		Object cured = JOptionPane.showInputDialog(this, SELECT_DISEASE_TO_CURE, 
				CURE, JOptionPane.DEFAULT_OPTION, null, cures, cures[0]);
		
		if (cured.equals(BLUE)) {
			blueCards = cardsToRemove(blueCards);
			alreadyCured = this.diseaseController.getBlueDisease().isCured();
			alreadyEradicated = this.diseaseController.getBlueDisease().isEradicated();
			if (atResearchStation) {
				didCure = this.controller.cureDisease(blueCards, this.blueDisease);
			}
		} else if (cured.equals(YELLOW)) {
			yellowCards = cardsToRemove(yellowCards);
			alreadyCured = this.diseaseController.getYellowDisease().isCured();
			alreadyEradicated = this.diseaseController.getYellowDisease().isEradicated();
			if (atResearchStation) {
				didCure = this.controller.cureDisease(yellowCards, this.yellowDisease);
			}
		} else if (cured.equals(BLACK)) {
			blackCards = cardsToRemove(blackCards);
			alreadyCured = this.diseaseController.getBlackDisease().isCured();
			alreadyEradicated = this.diseaseController.getBlackDisease().isEradicated();
			if (atResearchStation) {
				didCure = this.controller.cureDisease(blackCards, this.blackDisease);
			}
		} else if (cured.equals(RED)) {
			redCards = cardsToRemove(redCards);
			alreadyCured = this.diseaseController.getRedDisease().isCured();
			alreadyEradicated = this.diseaseController.getRedDisease().isEradicated();
			if (atResearchStation) {
				didCure = this.controller.cureDisease(redCards, this.redDisease);
			}
		}
		
		if (!atResearchStation) {
			JOptionPane.showMessageDialog(this, String.format("<html><center>%s</center></html",
					NOT_AT_RESEARCH_STATION), CURE, JOptionPane.INFORMATION_MESSAGE); 
		} else if (alreadyEradicated) {
			JOptionPane.showMessageDialog(this, String.format("<html><center>%s</center></html",
					ALREADY_ERADICATED), CURE, JOptionPane.INFORMATION_MESSAGE); 
		} else if (alreadyCured) {
			JOptionPane.showMessageDialog(this, String.format("<html><center>%s</center></html",
					ALREADY_CURED), CURE, JOptionPane.INFORMATION_MESSAGE); 
		} else {
			if (!didCure) {
				JOptionPane.showMessageDialog(this, String.format("<html><center>%s</center></html",
						NOT_ENOUGH_CARDS_TO_CURE), CURE, JOptionPane.INFORMATION_MESSAGE); 
			}
		}
	}
	
	private Set<CardModel> cardsToRemove(Set<CardModel> cards) {
		Map<String, CardModel> cardMap = new HashMap<>();
		Set<CardModel> cardsToRemove = new HashSet<>();
		List<String> keepCardList = new ArrayList<>();
		List<String> removeCardList = new ArrayList<>();
		
		for (CardModel card : cards) {
			String name = card.getName();
			cardMap.put(name, card);
			removeCardList.add(name);
		}
		
		if (this.controller.getCurrentPlayer().getCharacterModel().getRole().equals(SCIENTIST)) {
			while (cardMap.size() - keepCardList.size() > 4) {
				String keep = (String) JOptionPane.showInputDialog(this, SELECT_CARD_TO_KEEP, CURE,
						JOptionPane.DEFAULT_OPTION, null, removeCardList.toArray(),
						removeCardList.toArray()[0]);
				if (keep != null) {
					keepCardList.add(keep);
					removeCardList.remove(keep);
				}	
			}
		} else {
			while (cardMap.size() - keepCardList.size() > 5) {
				String keep = (String) JOptionPane.showInputDialog(this, SELECT_CARD_TO_KEEP, CURE, 
						JOptionPane.DEFAULT_OPTION, null, removeCardList.toArray(),
						removeCardList.toArray()[0]);
				if (keep != null) {
					keepCardList.add(keep);
					removeCardList.remove(keep);
				}	
			}
		}
		
		for (String name : keepCardList) {
			cardMap.remove(name);
		}
		
		for (CardModel card : cardMap.values()) {
			cardsToRemove.add(card);
		}
		
		return cardsToRemove;
	}	
	
	private void drawMap() {
		setBackground();
		this.mapPanel.add(this.background);
	}
	
	private void setBackground() {
		ImageIcon backgroundImg = new ImageIcon();
		Image image = setImage(MAP_IMG);
		Image scaledImage = image.getScaledInstance(1730, 860, Image.SCALE_SMOOTH);
		backgroundImg = new ImageIcon(scaledImage);

		this.background.setIcon(backgroundImg);
	}
	
	private Image setImage(String filepath) {
		Image image = null;

		try {
			File file = new File(filepath);
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println(filepath);
			System.out.println(e.getMessage());
			System.exit(0);
		}

		return image;
	}
	
	private void updateFrame() {
		this.setLayout(this.layout);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(GAME_BOARD_SIZE);
		this.setResizable(false);
		this.add(this.gameInfoPanel, BorderLayout.NORTH);
		this.add(this.playerInfoPanel, BorderLayout.WEST);
		this.add(this.playerActionPanel, BorderLayout.SOUTH);
		this.add(this.mapPanel, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}
	
	private void determineClickedCity(int xloc, int yloc) {	
		Map<String, CityFrontEndModel> frontEndCities = this.cities.getCitiesToDraw();
		
		for (CityFrontEndModel city : frontEndCities.values()) {
			int cityX = city.getX();
			int cityY = city.getY();
			boolean inXBounds = (xloc > cityX) && (xloc < cityX + CITY_RADIUS);
			boolean inYBounds = (yloc > cityY) && (yloc < cityY + CITY_RADIUS);
			
			if (inXBounds && inYBounds) {
				this.selectedCity = city;
				this.model.setSelectedCity(city.getCityModel());
				this.paintSelectedCity(this.getGraphics());
			}
		}
	}
	
	@Override
	public void paint(Graphics gr) {
		super.paint(gr);
		
		gr.setColor(CUSTOM_BLUE);
		gr.fillRect(BLUE_FILLER_X, BLUE_FILLER_Y, 
				GAME_BOARD_SIZE.width - BLUE_FILLER_WIDTH, OFFSET_5);
		
		if (!this.model.isLost() && !this.model.isWon()) {
			paintBoard(gr);
		} else if (this.model.isLost()) {
			this.setVisible(false);
			
			JOptionPane.showMessageDialog(null, String.format("<html><center>%s</center></html", LOST),
					LOST_GAME, JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		} else if (this.model.isWon()) {
			this.setVisible(false);
			
			JOptionPane.showMessageDialog(null, String.format("<html><center>%s</center></html", WON),
					WON_GAME, JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
	}
	
	private void paintBoard(Graphics gr) {
		paintCities(gr);
		paintInfections(gr);
		paintPlayerLocations(gr);
		paintGameCounters(gr);
		paintPlayerHands(gr);
		paintCurrentTurn(gr);
		paintSelectedCity(gr);
	}
	
	private void paintCities(Graphics gr) {
		this.cities.paintCities(gr);
	}
	
	private void paintInfections(Graphics gr) {
		Set<CityModel> infectedCities = this.cityController.getInfectedCities();
		DiseaseFrontEndModel blueDiseaseFrontEnd = new DiseaseFrontEndModel(this.blueDisease, Color.BLUE);
		DiseaseFrontEndModel yellowDiseaseFrontEnd = new DiseaseFrontEndModel(this.yellowDisease, Color.YELLOW);
		DiseaseFrontEndModel blackDiseaseFrontEnd = new DiseaseFrontEndModel(this.blackDisease, Color.BLACK);
		DiseaseFrontEndModel redDiseaseFrontEnd = new DiseaseFrontEndModel(this.redDisease, Color.RED);
		
		for (CityModel c : infectedCities) {
			int blueCount = c.getCubesByDisease(blueDiseaseFrontEnd.getDisease());
			int yellowCount = c.getCubesByDisease(yellowDiseaseFrontEnd.getDisease());
			int blackCount = c.getCubesByDisease(blackDiseaseFrontEnd.getDisease());
			int redCount = c.getCubesByDisease(redDiseaseFrontEnd.getDisease());
			int xoffset = 0;
			
			if (blueCount > 0) {
				paintCityInfections(gr, c, blueCount, blueDiseaseFrontEnd.getColor(), xoffset);
				xoffset += DELTA;
			}
			
			if (yellowCount > 0) {
				paintCityInfections(gr, c, yellowCount, yellowDiseaseFrontEnd.getColor(), xoffset);
				xoffset += DELTA;
			}
			
			if (blackCount > 0) {
				paintCityInfections(gr, c, blackCount, blackDiseaseFrontEnd.getColor(), xoffset);
				xoffset += DELTA;
			}
			
			if (redCount > 0) {
				paintCityInfections(gr, c, redCount, redDiseaseFrontEnd.getColor(), xoffset);
				xoffset += DELTA;
			}
		}
	}
	
	private void paintCityInfections(Graphics gr, CityModel city, int count, Color color, int xoffset) {
		CityFrontEndModel cityFrontEnd = this.cities.getCityToDraw(city.getName());
		int xloc = cityFrontEnd.getX() + CITY_RADIUS + OFFSET_2 + xoffset;
		int yloc = cityFrontEnd.getY() - DELTA;
		int size = DELTA - OFFSET_2;
		
		gr.setColor(color);
		
		for (int i = 0; i < count; i++) {
			int yoffset = yloc + (i * DELTA);
			gr.fillRect(xloc, yoffset, size, size);
		}
	}
	
	private void paintPlayerLocations(Graphics gr) {
		int playerNum = 0;
		
		for (CharacterModel character : this.model.getCharacters()) {
			CityModel city = character.getCurrentCity();
			String name = city.getName();
			CityFrontEndModel cityFrontEnd = this.cities.getCityToDraw(name);
			CharacterFrontEndModel characterFrontEnd = new CharacterFrontEndModel(character);
			Color color = characterFrontEnd.getColor();
			int cityXLocation = cityFrontEnd.getX();
			int cityYLocation = cityFrontEnd.getY();
			int xcoordinate = cityXLocation - DELTA;
			int ycoordinate = cityYLocation - (DELTA - (playerNum * DELTA));

			gr.setColor(color);
			gr.fillOval(xcoordinate, ycoordinate, DELTA, DELTA);
			gr.setColor(Color.BLACK);
			gr.drawOval(xcoordinate, ycoordinate, DELTA - OFFSET_1, DELTA - OFFSET_1);
			
			playerNum++;
		}
	}

	private void paintPlayerHands(Graphics gr) {
		List<AbstractCharacterController> players = this.controller.getPlayers();
		this.eventCards = new ArrayList<>();

		for (int i = 0; i < players.size(); i++) {
			CharacterModel player = players.get(i).getCharacterModel();
			int startingY = findPlayerYCoord(i);
			int cardCount = 0;
			
			for (CardModel card : player.getHandOfCards()) {
				String name = card.getName();
				
				if (card.getType().equals(CardModel.CardType.PLAYER)) {
					CityFrontEndModel cityModel = this.cities.getCityToDraw(name);
					Color color = cityModel.getColor();
					int yloc = startingY + cardCount * OFFSET_20;

					paintCardinHand(gr, name, color, yloc);
				} else {
					int yloc = startingY + cardCount * OFFSET_20;
					
					eventCards.add(card);
					paintCardinHand(gr, name, Color.ORANGE, yloc);
				}
				
				cardCount++;
			}
		}
		
		paintEventCards(gr, eventCards);
	}
	
	public int findPlayerYCoord(int playerNum) {
		int totalPlayers = this.players.size();
		
		if (playerNum == FIRST_PLAYER_INDEX) {
			return PLAYER_ONE_Y;
		} else if (playerNum == SECOND_PLAYER_INDEX) {
			if (totalPlayers == TWO_PLAYERS) {
				return PLAYER_TWO_TWO_PLAYERS_Y;
			} else if (totalPlayers == THREE_PLAYERS) {
				return PLAYER_TWO_THREE_PLAYERS_Y;
			} else {
				return PLAYER_TWO_FOUR_PLAYERS_Y;
			}
		} else if (playerNum == THIRD_PLAYER_INDEX) {
			if (totalPlayers == THREE_PLAYERS) {
				return PLAYER_THREE_THREE_PLAYERS_Y;
			} else {
				return PLAYER_THREE_FOUR_PLAYERS_Y;
			}
		} else {
			return PLAYER_FOUR_PLAYERS_Y;
		}
	}

	private void paintCardinHand(Graphics gr, String cityName, Color color, int yloc) {
		Graphics2D gr2D = (Graphics2D) gr;
		Color handColor = getColor(color, false);

		gr.setColor(handColor);

		gr2D.setFont(FONT);
		gr2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		gr2D.fillRect(PLAYER_HAND_X, yloc, PLAYER_HAND_WIDTH, PLAYER_HAND_HEIGHT);
		gr2D.setColor(CUSTOM_GRAY_2);
		gr2D.drawString(cityName, PLAYER_HAND_X + OFFSET_5, yloc + OFFSET_15);
	}
	
	private Color getColor(Color color, boolean selectingCity) {
		if (color.equals(Color.BLUE)) {
			return PLAYER_HAND_BLUE;
		} else if (color.equals(Color.YELLOW)) {
			return PLAYER_HAND_YELLOW;
		} else if (color.equals(Color.BLACK)) {
			return PLAYER_HAND_BLACK;
		} else if (color.equals(Color.RED)){
			return PLAYER_HAND_RED;
		} else {
			if (selectingCity) {
				return CUSTOM_GRAY_1;
			} else {
				return Color.ORANGE;
			}
		}
	}
	
	private void paintEventCards(Graphics gr, List<CardModel> eventCards) {
		for (int i = 0; i < eventCards.size(); i++) {
			String name = eventCards.get(i).getName().toLowerCase();
			String imgPath = EVENT_CARD_PATH + name.replace(' ', '_') + PNG_FILE;
			Image image = this.setImage(imgPath);
			
			gr.drawImage(image, EVENT_CARD_X - (EVENT_CARD_SEPARATION * i), EVENT_CARD_Y, null);
		}
	}


	private void paintGameCounters(Graphics gr) {
		Graphics2D gr2D = (Graphics2D) gr;
		
		gr2D.setFont(FONT);
		gr2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		
		paintPlayerDeckCount(gr2D);
		paintDiseaseCounters(gr2D);
		paintDiseaseStatus(gr2D);
		paintNumResearchStation(gr2D);
		paintNumOutbreaks(gr2D);
		paintInfectionRate(gr2D);
		paintDecks(gr2D);
	}
	
	private void paintPlayerDeckCount(Graphics2D gr2D) {
		int playerDeckCount = this.controller.getPlayerDeckController().getNumberOfCardsInDeck();
		Color deckTextColor = checkLowCount(playerDeckCount);
		
		gr2D.setColor(deckTextColor);
		gr2D.drawString(playerDeckCount + "", DECK_COUNTER_X, TOP_PANEL_TEXT_Y);
	}
	
	private void paintDiseaseCounters(Graphics2D gr2D) {
		int blueDisease = this.diseaseController.getBlueDisease().getCubesLeft();
		int yellowDisease = this.diseaseController.getYellowDisease().getCubesLeft();
		int blackDisease = this.diseaseController.getBlackDisease().getCubesLeft();
		int redDisease = this.diseaseController.getRedDisease().getCubesLeft();
		Color blueTextColor = checkLowCount(blueDisease);
		Color yellowTextColor = checkLowCount(yellowDisease);
		Color blackTextColor = checkLowCount(blackDisease);
		Color redTextColor = checkLowCount(redDisease);
		
		gr2D.setColor(blueTextColor);
		gr2D.drawString(blueDisease + "", BLUE_COUNTER_X, TOP_PANEL_TEXT_Y);
		gr2D.setColor(yellowTextColor);
		gr2D.drawString(yellowDisease + "", YELLOW_COUNTER_X, TOP_PANEL_TEXT_Y);
		gr2D.setColor(blackTextColor);
		gr2D.drawString(blackDisease + "", BLACK_COUNTER_X, TOP_PANEL_TEXT_Y);
		gr2D.setColor(redTextColor);
		gr2D.drawString(redDisease + "", RED_COUNTER_X, TOP_PANEL_TEXT_Y);
	}
	
	private void paintDiseaseStatus(Graphics2D gr2D) {
		Image diseaseStatus;
		
		if (this.diseaseController.getBlueDisease().isCured()) {
			diseaseStatus = this.setImage(BLUE_CURED);
			if (this.diseaseController.getBlueDisease().isEradicated()) {
				diseaseStatus = this.setImage(BLUE_ERADICATED);
			}

			gr2D.drawImage(diseaseStatus, BLUE_DISEASE_X, DISEASE_Y, null);
		}

		if (this.diseaseController.getYellowDisease().isCured()) {
			diseaseStatus = this.setImage(YELLOW_CURED);
			if (this.diseaseController.getYellowDisease().isEradicated()) {
				diseaseStatus = this.setImage(YELLOW_ERADICATED);
			}

			gr2D.drawImage(diseaseStatus, YELLOW_DISEASE_X, DISEASE_Y, null);
		}

		if (this.diseaseController.getBlackDisease().isCured()) {
			diseaseStatus = this.setImage(BLACK_CURED);
			if (this.diseaseController.getBlackDisease().isEradicated()) {
				diseaseStatus = this.setImage(BLACK_ERADICATED);
			}

			gr2D.drawImage(diseaseStatus, BLACK_DISEASE_X, DISEASE_Y, null);
		}

		if (this.diseaseController.getRedDisease().isCured()) {
			diseaseStatus = this.setImage(RED_CURED);
			if (this.diseaseController.getRedDisease().isEradicated()) {
				diseaseStatus = this.setImage(RED_ERADICATED);
			}

			gr2D.drawImage(diseaseStatus, RED_DISEASE_X, DISEASE_Y, null);
		}
	}
	
	private void paintNumResearchStation(Graphics2D gr2D) {
		int numStations = 6 - this.cityController.getResearchStationCounter();
		
		gr2D.setColor(CUSTOM_GRAY_2);
		gr2D.drawString(numStations + "", RESEARCH_COUNT_X, TOP_PANEL_TEXT_Y);
	}
	
	private void paintNumOutbreaks(Graphics2D gr2D) {
		int numOutbreaks = this.cityController.getOutbreakCoutner();
		
		gr2D.setColor(CUSTOM_GRAY_2);
		gr2D.drawString(numOutbreaks + "", OUTBREAK_COUNT_X, TOP_PANEL_TEXT_Y);
	}
	
	private void paintInfectionRate(Graphics2D gr2D) {
		for(int i = 0; i < this.controller.getGameModel().getInfectionRates().length; i++){
			if(i == this.controller.getGameModel().getInfectionRateIndex()){
				gr2D.setColor(CUSTOM_GRAY_2);
			} else {
				gr2D.setColor(CUSTOM_GRAY_3);
			}
			gr2D.drawString(this.controller.getGameModel().getInfectionRates()[i] + "", 
					INFECTION_RATE_X + (OFFSET_10 * i), TOP_PANEL_TEXT_Y);
		}
	}
	
	private void paintDecks(Graphics2D gr2D) {
		FontMetrics metrics = gr2D.getFontMetrics(FONT);
		
		paintPlayerDeck(gr2D, metrics);
		paintInfectionDeck(gr2D, metrics);
	}
	
	private void paintPlayerDeck(Graphics2D gr2D, FontMetrics metrics) {
		List<CardModel> player = this.controller.getPlayerDeckController().getDiscardedCards();
		CardModel lastDiscarded = new CardModel("", CardModel.CardType.PLAYER);
		String discardedCityName = "";
		Image discardCardImg = null;
		Image playerCard = this.setImage(PLAYER_CARD_IMG);
		
		if (player.size() <= 0) {
			int xlocTop = PLAYER_DISCARD_X + (TOP_CARD_WIDTH - metrics.stringWidth(PLAYER_DECK)) / 2;
			int xlocBottom = PLAYER_DISCARD_X + (TOP_CARD_WIDTH - metrics.stringWidth(DISCARD_PILE)) / 2;
			int yloc = TOP_CARD_Y + ((TOP_CARD_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
			
			gr2D.setColor(CUSTOM_GRAY_2);
			gr2D.fillRect(PLAYER_DISCARD_X, TOP_CARD_Y, TOP_CARD_WIDTH, TOP_CARD_HEIGHT);
			gr2D.setColor(Color.WHITE);
			gr2D.drawString(PLAYER_DECK, xlocTop, yloc + 8);
			gr2D.drawString(DISCARD_PILE, xlocBottom, yloc - 8);
		} else {
			lastDiscarded = player.get(player.size() - 1);
			discardedCityName = lastDiscarded.getName().toLowerCase();
			discardCardImg = this.setImage(CITY_CARD_PATH + discardedCityName + BMP_FILE);
			
			gr2D.drawImage(discardCardImg, PLAYER_DISCARD_X, TOP_CARD_Y, null);
		}
		
		gr2D.drawImage(playerCard, PLAYER_CARD_X, TOP_CARD_Y, null);
	}
	
	private void paintInfectionDeck(Graphics2D gr2D, FontMetrics metrics) {
		List<CardModel> infected = this.controller.getInfectionDeckController().getDiscardedCards();
		CardModel lastInfected = new CardModel("", CardModel.CardType.INFECTION);
		String infectedCityName = "";
		Image infectedCardImg = null;
		Image infectionCard = this.setImage(INFECTION_CARD_IMG);

		if (infected.size() <= 0) {
			int xlocTop = INFECTION_DISCARD_X + (TOP_CARD_WIDTH - metrics.stringWidth(INFECTION_DECK)) / 2;
			int xlocBottom = INFECTION_DISCARD_X + (TOP_CARD_WIDTH - metrics.stringWidth(DISCARD_PILE)) / 2;
			int yloc = TOP_CARD_Y + ((TOP_CARD_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
			
			gr2D.setColor(CUSTOM_GRAY_2);
			gr2D.fillRect(INFECTION_DISCARD_X, TOP_CARD_Y, TOP_CARD_WIDTH, TOP_CARD_HEIGHT);
			gr2D.setColor(Color.WHITE);
			gr2D.drawString(INFECTION_DECK, xlocTop, yloc + OFFSET_8);
			gr2D.drawString(DISCARD_PILE, xlocBottom, yloc - OFFSET_8);
		} else {
			lastInfected = infected.get(infected.size() - 1);
			infectedCityName = lastInfected.getName().toLowerCase();
			infectedCardImg = this.setImage(CITY_CARD_PATH + infectedCityName + BMP_FILE);
			
			gr2D.drawImage(infectedCardImg, INFECTION_DISCARD_X, TOP_CARD_Y, null);
		}
		
		gr2D.drawImage(infectionCard, INFECTION_CARD_X, TOP_CARD_Y, null);
	}
	
	public Color checkLowCount(int numCards) {
		if (numCards <= 10) {
			return Color.RED;
		} else {
			return CUSTOM_GRAY_2;
		}
	}

	private void paintCurrentTurn(Graphics gr) {
		Graphics2D gr2D = (Graphics2D) gr;
		CharacterModel character = this.controller.getCurrentPlayer().getCharacterModel();
		CharacterFrontEndModel characterFrontEnd = new CharacterFrontEndModel(character);
		String name = character.getName();
		String role = character.getRole();
		String img = characterFrontEnd.getImgPath();
		int actionsLeft = this.model.getActionsLeft();
		int count = 1;
		Image image = setImage(img);
		Color color = characterFrontEnd.getColor();
		
		gr2D.setFont(FONT);
		gr2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		gr2D.setColor(color);
		gr2D.fillRect(TURN_PANEL_X, TURN_PANEL_Y, TURN_PANEL_WIDTH, TURN_PANEL_HEIGHT);
		gr2D.drawImage(image, TURN_PANEL_X + OFFSET_5, TURN_PANEL_Y + OFFSET_5, null);
		gr2D.setColor(CUSTOM_GRAY_2);
		gr2D.drawString(name, TURN_PANEL_TEXT_X, TURN_PANEL_TEXT_Y);
		gr2D.drawString(role, TURN_PANEL_TEXT_X, TURN_PANEL_TEXT_Y + OFFSET_15);
		
		while (count <= actionsLeft) {
			gr2D.fillOval(408, 882 + OFFSET_15 * count, TURN_RADIUS, TURN_RADIUS);
			count++;
		}
	}
	
	private void paintSelectedCity(Graphics gr) {
		Graphics2D gr2D = (Graphics2D) gr;
		FontMetrics metrics = gr2D.getFontMetrics(FONT);
		String name = this.selectedCity.getCityModel().getName();
		Color cityColor = this.selectedCity.getColor();
		Color color = getColor(cityColor, true);
		int xloc = SELECTED_CITY_X + (SELECTED_CITY_WIDTH - metrics.stringWidth(name)) / 2;
		int yloc = SELECTED_CITY_Y + ((SELECTED_CITY_HEIGHT - metrics.getHeight()) / 2) + 
				metrics.getAscent();

		if (!this.isSelectedCitySet) {
			color = CUSTOM_GRAY_1;
			name = NO_SELECTED_CITY;
			xloc = SELECTED_CITY_X + (SELECTED_CITY_WIDTH - metrics.stringWidth(name)) / 2;
			yloc = SELECTED_CITY_Y + ((SELECTED_CITY_HEIGHT - metrics.getHeight()) / 2) + 
					metrics.getAscent();
			
			this.isSelectedCitySet = true;
		}
		
		gr2D.setFont(FONT);
		gr2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		gr.setColor(color);
		gr.fillRect(SELECTED_CITY_X, SELECTED_CITY_Y, SELECTED_CITY_WIDTH, SELECTED_CITY_HEIGHT);
		gr.setColor(CUSTOM_GRAY_2);
		gr.drawString(name, xloc, yloc);
	}
}