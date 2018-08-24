package neuronalesNetz;

import java.awt.image.BufferedImage;

public class NeuronalesNetz implements INetzSpielsteuerung{
	
//	public static void main(String[] args) {
//		NeuronalesNetz test = new NeuronalesNetz();
//		for(int i = 0; i < 10; i++) {
//			System.out.println("" + i + test.bildErkennung(null, "apfel"));
//		}
//	}

	@Override
	public boolean bildErkennung(BufferedImage img, String begriff) {
		int r = (int) (Math.random() * 10);
		return (r == 9) ? true : false;
	}

}
