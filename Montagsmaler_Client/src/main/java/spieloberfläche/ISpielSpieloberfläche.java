package spieloberfläche;

import java.awt.image.BufferedImage;

public interface ISpielSpieloberfläche {
	
	
	public void updateScoreBoard();
	public void updateMalfläche();
	public void updateChat();
	public void updateSpielStatus();
	public void updateClock();
	public BufferedImage getImage();
	
	
	
}
