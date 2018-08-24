package spieloberfläche;

import java.awt.image.BufferedImage;

import spielsteuerung.Spielverwaltung;

public class Spieloberfläche_Test implements ISpielSpieloberfläche{
	
	public Spielverwaltung spielverwaltung;
	
	public Spieloberfläche_Test(int myPlayerID) {
		spielverwaltung = new Spielverwaltung(this, myPlayerID);
	}

	@Override
	public void updateScoreBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMalfläche() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateChat() {
		System.out.println("(In der GUI) " + spielverwaltung.getNextChatMessage());
	}

	@Override
	public void updateSpielStatus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
