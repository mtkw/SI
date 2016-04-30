import java.util.ArrayList;

import myNN.network.NeuronNetwork;
import myNN.network.TreningPattern;

/*
 * klasa testowa do sprawdzenia poprawnoœci dzia³anai Sieci i Algorytmu Genetycznego
 */
public class Run {

	public static void main(String[] args) {

		ArrayList<TreningPattern> treningPatterns = new ArrayList<>();
//		treningPatterns.add(new TreningPattern(new double[] { 1d, 0d, 0d, 0d }, new double[] { 1d, 0d, 0d, 0d }));
//		treningPatterns.add(new TreningPattern(new double[] { 0d, 1d, 0d, 0d }, new double[] { 0d, 1d, 0d, 0d }));
//		treningPatterns.add(new TreningPattern(new double[] { 0d, 0d, 1d, 0d }, new double[] { 0d, 0d, 1d, 0d }));
//		treningPatterns.add(new TreningPattern(new double[] { 0d, 0d, 0d, 1d }, new double[] { 0d, 0d, 0d, 1d }));

		treningPatterns.add(new TreningPattern(new double[] { .33d, 1.20d, .80d }, new double[] { 1d }));
		treningPatterns.add(new TreningPattern(new double[] { .40d, 1.90d, 1.0d }, new double[] { 0d }));
		
//		System.out.println(treningPatterns.get(0).getOutputArray()[0]);
		treningPatterns.add(new TreningPattern(new double[] { .50d, 0.20d, 1.80d }, new double[] { 1d }));
//		treningPatterns.add(new TreningPattern(new double[] { 1.33d, 0.20d, 2.80d }, new double[] { 0d }));
		/*
		 * Pierwsza wersja schematu nauczania sieci za pomoc¹ algorytmu
		 * genetycznego
		 */

		// Testy stróktury
//		NeuronNetwork nn = new NeuronNetwork(new int[] { 3, 2, 3, 1 }, "Test");
		NeuronNetwork nn = new NeuronNetwork(new int[] { 3, 2, 1 }, "Test");

//		NeuronNetwork nn = new NeuronNetwork(new int[] { 4, 2, 4 }, "Test");
//		nn.test(treningPatterns);
		 nn.genetickNetworkLearning(treningPatterns);
		// nn.networkLearning(treningPatterns);

		// for(Chromosom ch: pop.getPopulacjaPoczatkowa()){
		// System.out.println(ch.getFn() + "----------- B£¥D " + ch.getError() +
		// "------ Prawdopodobienstow " + ch.getPrawdopodobieñstwoWyboru() + "
		// Start " + ch.getStart() + " End " + ch.getEnd());
		// }

		// System.out.println(k.getKoloRuletki().size());
	}

}
