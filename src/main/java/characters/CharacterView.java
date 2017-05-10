package characters;

import static constants.Constants.PLAYER_HEADER_SIZE;
import static constants.Constants.OFFSET_5;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.CardModel;

public class CharacterView extends JPanel {
	CharacterModel model;

	public CharacterView(CharacterModel character) {
		this.model = character;
	}

	public void drawPanel() {
		String name = this.model.getName(); // Need a way to get player names
		String character = this.model.getName();
		JPanel playerInfoPanel = new JPanel();
		JPanel characterInfo = new JPanel();
		JPanel info = new JPanel();
		JLabel playerDesc = new JLabel(name);
		JLabel characterDesc = new JLabel(character);
		JLabel characterImg = new JLabel();
		Color color = this.model.getColor();
		String imgFile = this.model.getImgPath();
		ImageIcon characterIcon = new ImageIcon();
		Image image = this.setImage(imgFile);
		FlowLayout characterLayout = new FlowLayout(FlowLayout.LEFT, OFFSET_5, OFFSET_5);
		BoxLayout infoLayout = new BoxLayout(info, BoxLayout.Y_AXIS);
		BoxLayout playerInfoLayout = new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS);
		
		characterIcon = new ImageIcon(image);
		characterImg.setIcon(characterIcon);

		info.setLayout(infoLayout);
		info.setBackground(color);
		info.setOpaque(true);
		info.add(playerDesc);
		info.add(characterDesc);
		
		characterInfo.setLayout(characterLayout);
		characterInfo.setAlignmentX(LEFT_ALIGNMENT);
		characterInfo.setBackground(color);
		characterInfo.setOpaque(true);
		characterInfo.add(characterImg);
		characterInfo.add(info);
		characterInfo.setPreferredSize(PLAYER_HEADER_SIZE);

		playerInfoPanel.setLayout(playerInfoLayout);
		playerInfoPanel.setBackground(color);
		playerInfoPanel.add(characterInfo);

		this.setBackground(color);
		this.add(playerInfoPanel);
	}
	
	private Image setImage(String filepath) {
		Image image = null;

		try {
			File file = new File(getClass().getResource(filepath).toURI());
			image = ImageIO.read(file);
		} catch (IOException | URISyntaxException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		return image;
	}
}
