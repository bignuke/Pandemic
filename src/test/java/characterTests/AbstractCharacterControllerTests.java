package characterTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cards.AbstractDeckCardController;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.AbstractCharacterController;
import characters.CharacterModel;
import characters.ContingencyPlannerCharacterController;
import characters.DispatcherCharacterController;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;
import game.GameController;
import game.GameModel;

public class AbstractCharacterControllerTests {
	DiseaseController diseaseController;
	String cityName = "Chicago";
	GameController gameController;
	ContingencyPlannerCharacterController contPlanner;
	CharacterModel character;
	DispatcherCharacterController characterController;

	@Before
	public void init() {
		GameModel gameModel = new GameModel();
		DiseaseController diseaseController = new DiseaseController();
		CityController cityController = new CityController(diseaseController);
		AbstractDeckCardController playerDeckController = new PlayerDeckCardController(cityController);
		AbstractDeckCardController infectionDeckController = new InfectionDeckCardController(cityController);
		
		this.gameController = new GameController(gameModel,
											diseaseController,
											cityController,
											playerDeckController,
											infectionDeckController);

		
		String characterName = "CharacterName";
		DiseaseModel diseaseModel = new DiseaseModel();
		CityModel cityModel = new CityModel(cityName, diseaseModel);
		this.character = new CharacterModel(characterName, cityModel);
		this.characterController = new DispatcherCharacterController(this.character);
	}
	
	@Test
	public void testGetCharacterModel(){
		assertEquals(this.character, this.characterController.getCharacterModel());
	}

}
