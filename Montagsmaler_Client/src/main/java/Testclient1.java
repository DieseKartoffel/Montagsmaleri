

import java.util.Scanner;

import spieloberfläche.Spieloberfläche_Test;
import spielsteuerung.IOberflächeSpielsteuerung;


public class Testclient1 {
	public static void main(String[] args) {
//		IOberflächeSpielsteuerung verwaltung = new Spielverwaltung(null, 50);
//		verwaltung.sendMessage("Hallo, ich bin ID 50!");
		
		Spieloberfläche_Test testClient1 = new Spieloberfläche_Test(1);
		String gameID = ((IOberflächeSpielsteuerung) testClient1.spielverwaltung).createMultiplayerGame(); 
		System.out.println("Spiel erstellt mit ID: "+gameID);
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Drücke ENTER um das Spiel zu starten");
		sc.nextLine();
		
		((IOberflächeSpielsteuerung) testClient1.spielverwaltung).startMultiplayerGame("standard", 5, 60);
		
		String msg = "";
		while (!msg.equals("exit")) {
			System.out.print("\n>");
			msg = sc.nextLine();
			testClient1.spielverwaltung.sendMessage(msg);
		}
		sc.close();
		
		
		
	}

}
