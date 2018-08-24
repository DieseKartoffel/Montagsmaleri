
import java.util.Scanner;

import spieloberfläche.Spieloberfläche_Test;

public class Testclient2 {
	public static void main(String[] args) {
		// IOberflächeSpielsteuerung verwaltung = new Spielverwaltung(null, 50);
		// verwaltung.sendMessage("Hallo, ich bin ID 50!");

		Spieloberfläche_Test testClient2 = new Spieloberfläche_Test(2);
		Scanner sc = new Scanner(System.in);
		System.out.print("Spiel beitreten - GAME ID:");
		String gameID = sc.nextLine();

		testClient2.spielverwaltung.joinMultiplayerGame(gameID);
		System.out.println("Nachricht an Spielchat senden:");
		String msg = "";
		while (!msg.equals("exit")) {
			System.out.print("\n>");
			msg = sc.nextLine();
			testClient2.spielverwaltung.sendMessage(msg);
		}
		sc.close();
	}
}
