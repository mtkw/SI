package myNN.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import myNN.genticAlgorithm.Chromosom;
import myNN.genticAlgorithm.FunkcjaPrzystosowania;
import myNN.genticAlgorithm.KoloRuletki;
import myNN.genticAlgorithm.MetodyGenetyczne;
import myNN.genticAlgorithm.Population;

/**
 * Implementacja sieci neuronowej list pojedynczych warst
 * 
 */
public class NeuronNetwork implements Serializable {

	private static final long serialVersionUID = -8088643370245104127L;

	private ArrayList<SingleLayer> neuronNetwork;
	private String descrption;
	private int bias;

	// Do Genetycznego Algorytmu Nauczania
	Population pop = new Population();
	FunkcjaPrzystosowania fn = new FunkcjaPrzystosowania();
	KoloRuletki k = new KoloRuletki();
	MetodyGenetyczne m = new MetodyGenetyczne();

	private Chromosom najlepszy;

	/**
	 * @param neuronNetwork
	 * @param description
	 */
	public NeuronNetwork(ArrayList<SingleLayer> neuronNetwork, String description) {
		this.descrption = description;
		this.neuronNetwork = neuronNetwork;
		this.bias = Constants.BIAS;
	}

	/**
	 * @param layersDesc
	 * @param description
	 */
	@SuppressWarnings("unchecked")
	public NeuronNetwork(int[] layersDesc, String description) {
		ArrayList<SingleLayer> listOfLayers = new ArrayList<>();

		for (int layer = 0; layer < layersDesc.length; layer++) {
			ArrayList<Double> weights = new ArrayList<>();
			ArrayList<Neuron> Layer = new ArrayList<>();
			SingleLayer SingleLayer = new SingleLayer(Layer, "init layer");
			Random rand = new Random();

			if (layer == 0) { // warstwa pierwsza ró¿ni siê od pozosta³ych

				weights.clear();
				Layer.clear();
				weights.add(1d);
				for (int j = 0; j < layersDesc[layer]; j++) {
					Layer.add(new Neuron((ArrayList<Double>) weights.clone(), "n_" + layer + "_" + j,
							new ActivationFunction(ActivFuncEnum.LINEAR)));
				}
				SingleLayer = new SingleLayer(Layer, "layer[" + layer + "]");
				listOfLayers.add(SingleLayer);

			} else { // kolejne warstwy /// TO by zosta³o

				weights.clear();
				Layer.clear();
				for (int j = 0; j < layersDesc[layer]; j++) {
					weights.clear();
					for (int i = 0; i < layersDesc[layer - 1] + 1; i++) { // jedna
																			// waga
																			// wiêcej
																			// dla
																			// biasu
						weights.add(rand.nextDouble() - 0.5); // zakres wag
																// [-0.5 ; 0.5]
					}
					Layer.add(new Neuron((ArrayList<Double>) weights.clone(), "n_" + layer + "_" + j,
							new ActivationFunction(ActivFuncEnum.SIGMOID)));
				}
				SingleLayer = new SingleLayer(Layer, "layer[" + layer + "]");
				listOfLayers.add(SingleLayer);
			}

		}
		this.descrption = description;
		this.neuronNetwork = listOfLayers;
		this.bias = Constants.BIAS;
	}

	public void test(ArrayList<TreningPattern> treningPatterns) {
		pop.przygotowaniePopulacjiPoczatkowej(65535, 16, 1, 22);
		setWeights(pop.getPopulacjaPoczatkowa().getFirst());

		for (TreningPattern p : treningPatterns) {
			this.work(p.getInputArray());
			// blad(p);
			System.out.println(this);

			int layer = this.neuronNetwork.size();
			SingleLayer sLayer = this.neuronNetwork.get(layer - 1);
			int neuronCount = sLayer.getNeurons().size();

			for (Neuron n : sLayer.getNeurons()) {
				System.out.println(n.getOut());
			}
		}

	}

	public double blad(TreningPattern treningPatterns) {
		double bladDlaWzorca = 0.0;

		int layer = this.neuronNetwork.size();
		SingleLayer sLayer = this.neuronNetwork.get(layer - 1);
		int neuronCount = sLayer.getNeurons().size();

		int counter = treningPatterns.getOutputArray().length;

		// System.out.println("WYJSCIE " +
		// sLayer.getNeurons().get(0).getOut());
		for (int i = 0; i < counter; i++) {
			bladDlaWzorca += Math.abs(treningPatterns.getOutputArray()[i] - sLayer.getNeurons().get(i).getOut());
		}
		// System.out.println(treningPattern.getOutputArray()[0] + " - " +
		// sLayer.getNeurons().get(0).getOut() + " = " + blad);

		return bladDlaWzorca / counter;
	}

	public void networkLearning(ArrayList<TreningPattern> treningPatterns) {

		// wstêpne wyznaczenie b³êdu dla sieci
		this.work(treningPatterns.get(0).getInputArray());
		this.countErrors(treningPatterns.get(0).getOutputArray());
		double meanSquareError = blad(treningPatterns.get(0));
		this.recalculateWeights(); // metoda wstecznej propagacji b³êdu

		long iterations = 0L;
		while (Math.abs(meanSquareError) > Constants.ERROR_VALUE && iterations < 100000000) {
			meanSquareError = 0d;
			// TODO: nauka powinna za ka¿dym razem korzystaæ z innej kolejnoœci
			// wzorców treningowych
			for (TreningPattern tp : treningPatterns) {
				this.work(tp.getInputArray());
				this.countErrors(tp.getOutputArray());
				// meanSquareError += this.meanSquareError(tp.getInputArray());
				meanSquareError = blad(tp);
				System.out.println("WZORZEC BLAD " + blad(tp));
				this.recalculateWeights();
			}
			iterations++;
			if (Constants.DEBUG) {
				System.out.println(meanSquareError);
			}
		}

		System.out.println(this);
		System.out.println("\r\n[meanSquareError: " + String.format("%.8f", meanSquareError) + "] after " + iterations
				+ " iterations");
		for (TreningPattern tp : treningPatterns) {
			this.work(tp.getInputArray());
			int layer = this.neuronNetwork.size();
			SingleLayer sLayer = this.neuronNetwork.get(layer - 1);
			int neuronCount = sLayer.getNeurons().size();

			for (Neuron n : sLayer.getNeurons()) {
				System.out.println("WYJŒÆIE Z SIECI " + n.getOut());
			}
		}
	}

	/*
	 * Nauczanie sici neuronowej za pomoca algorytmu genetycznego
	 */
	public void genetickNetworkLearning(ArrayList<TreningPattern> treningPatterns) {
		double error = 0.0;
		int count = 0;
		double bladOgolny = 0.0;

		// Pierwszy etap utworzenia populacji pocz¹tkowej
		if (pop.getPopulacja().isEmpty()) {
//			pop.przygotowaniePopulacjiPoczatkowej(65535, 16, 200, 22);
			pop.przygotowaniePopulacjiPoczatkowej(130000, 17, 100, 11);

			// Sprawdzenie ka¿dego chromosmu z osobna
			for (Chromosom ch : pop.getPopulacjaPoczatkowa()) {
				setWeights(ch);
				// Sprawdzenie ka¿dego wzorca
				for (TreningPattern p : treningPatterns) {
					this.work(p.getInputArray());
					error += this.blad(p);
				}
				// System.out.println("ERROR " + error);
				// System.out.println("ERROR / SIZE " + error /
				// treningPatterns.size());
				ch.setError(error / treningPatterns.size());
			}

			// Wyznaczenie wartoœci funkcji przystosowania
			for (Chromosom ch : pop.getPopulacjaPoczatkowa()) {
				fn.wyznaczenie_wartoœci_funckji_przystosowania(ch, Constants.ERROR_VALUE);
			}

			// for (Chromosom ch : pop.getPopulacjaPoczatkowa()) {
			// System.out.println("ERROR " + ch.getError() + " FN " +
			// ch.getFn());
			// }

			/*
			 * znalezienie najlepszego osobnika z populacji
			 */
			setNajlepszy(najlepszy(pop.getPopulacjaPoczatkowa()));

			// Przygotowanie Ko³a Ruletki i wylosowanie osobników do
			// dalszych
			// prac

			k.koloRuletki(pop.getPopulacjaPoczatkowa());
			// System.out.println("Kolo Ruletki " +
			// k.getKoloRuletki().size());

			// Podzia³ wylosowanych osobników na pary
			m.podzia³NaPary(k.getWylosowaneOsobniki());

			// Utworzenie populacji potomków z wylosowanych osobników
			m.metodyGenetyczne(84, 1, 15, m.getPary2());

			// System.out.println(m.getPotomstwo().size());

			// Zapisanie potomstaw jako nowej populacji do kolejnej iteracji
			// algorytmu
			pop.getPopulacja().clear();
			pop.setPopulacja(m.getPotomstwo());
			// m.getPotomstwo().clear();
			// System.out.println("POPULACJA SIZE " +
			// pop.getPopulacja().size());
		}
		while (najlepszy.getError() >= 0.01 && count <= 600) {
			// System.out.println("---------------");
			// Dekodowanie Wag na postaæ dzisiêtn¹
			for (Chromosom ch : pop.getPopulacja()) {
				ch.dekodowanieChromosomu();
			}

			// Sprawdzenie ka¿dego chromosmu z osobna
			// System.out.println("POP " + pop.getPopulacja().size());
			for (Chromosom ch : pop.getPopulacja()) {
				setWeights(ch);
				error = 0.0;
				// Sprawdzenie ka¿dego wzorca
				for (TreningPattern p : treningPatterns) {
					this.work(p.getInputArray());
					// error += this.meanSquareError(p.getInputArray());
					// wyznaczenie b³edu dla danego chromosmu i wszystkich
					// wzorców ucz¹cych
					error += this.blad(p);
				}
				// System.out.println("ERROR " + error);
				// System.out.println("ERROR / SIZE " + error /
				// treningPatterns.size());
				ch.setError(error / treningPatterns.size());

				// error = 0.0;
			}
			// System.out.println("Kolejne POKOLENIE " + (sredniblad /
			// 100));

			// Wyznaczenie wartoœci funkcji przystosowania
			for (Chromosom ch : pop.getPopulacja()) {
				fn.wyznaczenie_wartoœci_funckji_przystosowania(ch, Constants.ERROR_VALUE);
			//	System.out.println("FUNCKJA PRZYSTOSOWANIA " + ch.getFn() + "ERROR " + ch.getError());
			}

			/*
			 * znalezienie najlepszego osobnika z populacji
			 */
			setNajlepszy(najlepszy(pop.getPopulacja()));
			//System.out.println("NAJ NAJ NAJ " + najlepszy.getError());

			// System.out.println(pop.getPopulacja().size());
			// Przygotowanie Ko³a Ruletki i wylosowanie osobników do
			// dalszych
			// prac

			// Czyszczenie
			// k.getKoloRuletki().clear();
			k.getWylosowaneOsobniki().clear();
			m.getPary2().clear();

			k.koloRuletki(pop.getPopulacja());
//			System.out.println("WYLOSOWANE OSOBNIKI " + k.getWylosowaneOsobniki().size());
			// for (Chromosom ch : k.getWylosowaneOsobniki()) {
			// System.out.println(ch.getFn() + "PRAWDOPODOBIENSTWO " +
			// ch.getPrawdopodobieñstwoWyboru());
			// }
			// System.out.println("Najlepszy " + najlepszy.getError());
			// System.out.println("Kolo Ruletki " +
			// k.getKoloRuletki().size());
			// System.out.println("Wylosowane " +
			// k.getWylosowaneOsobniki().size());
			// Podzia³ wylosowanych osobników na pary
			m.podzia³NaPary(k.getWylosowaneOsobniki());

			// Utworzenie populacji potomków z wylosowanych osobników
			m.metodyGenetyczne(84, 1, 15, m.getPary2());

			// System.out.println(m.getPotomstwo().size());

			// Zapisanie potomstaw jako nowej populacji do kolejnej iteracji
			// algorytmu
			pop.getPopulacja().clear();
			pop.setPopulacja(m.getPotomstwo());
//			System.out.println("POPULACJA NA KONIEC ETAPU " + pop.getPopulacja().size());
			// System.out.println(
			// "POPULACJA SIZE " + pop.getPopulacja().size() + " Potomstwo "
			// + m.getPotomstwo().size());
			count++;
		}
		System.out.println("Liczba EPOK " + count);
		System.out.println("NAJMNIEJSZY B£AD " + najlepszy.getError());

		// }System.out.println(error);

		// System.out.println(pop.getPopulacja().size());
		// setWeights();

		// for (Chromosom ch : pop.getPopulacja()) {
		// setWeights(ch);
		// this.work(treningPatterns.get(0).getInputArray());

		setWeights(najlepszy);
		for (

		TreningPattern tp : treningPatterns)

		{
			this.work(tp.getInputArray());
			System.out.println("!!!!!!!!!!!!!!!!");
			int layer = this.neuronNetwork.size();
			SingleLayer sLayer = this.neuronNetwork.get(layer - 1);
			int neuronCount = sLayer.getNeurons().size();

			for (Neuron n : sLayer.getNeurons()) {
				System.out.println(n.getOut());
			}
		}
		for (Double d : najlepszy.getChromosomDec()) {
			System.out.println("WAGA " + d);
		}

		// System.out.println("!!!!!!!!!!!!!!!!");
	}
	// }

	public Chromosom najlepszy(LinkedList<Chromosom> populacja) {
		double best = 0.0;
		najlepszy = new Chromosom();
		for (Chromosom ch : populacja) {
			if (ch.getFn() > best) {
				best = ch.getFn();
				najlepszy.setFn(ch.getFn());
				najlepszy.setChromosomDec(ch.getChromosomDec());
				najlepszy.setError(ch.getError());
			}
		}
		setNajlepszy(najlepszy);
//		System.out.println("NAJLEPSZY Z POKOLENIA " + najlepszy.getFn());
		return najlepszy;
	}

	/*
	 * lets get started
	 */
	public void work(double[] inputVector) {
		ArrayList<Double> outs = new ArrayList<Double>();

		// warstwa 0
		for (int i = 0; i < inputVector.length; i++) {
			outs.clear();
			outs.add(inputVector[i]);
			this.neuronNetwork.get(0).getNeurons().get(i).activate(outs);
		}

		// pozosta³e warstwy
		for (int l = 1; l < this.neuronNetwork.size(); l++) {
			outs.clear();
			// tworzymy wektor wejœciowy z wyjœæ poprzedniej warstwy
			for (Neuron x : this.neuronNetwork.get(l - 1).getNeurons()) {
				outs.add(x.getOut());
			}
			outs.add(Double.parseDouble(Constants.BIAS + ""));
			// ka¿demu z neuronów podajemy wektor wejœciowy z wag poprzedniej
			// warstwy
			for (Neuron n : this.neuronNetwork.get(l).getNeurons()) {
				n.activate(outs);
			}
		}
	}

	/*
	 * obliczenie b³êdu dla ka¿dgo neurona w sieci
	 */
	public void countErrors(double[] outputVector) {

		int layer = this.neuronNetwork.size();
		double delta = 0d;
		int counter = 0;

		// ostatnia warstwa sieci neuronowej
		for (Neuron n : this.neuronNetwork.get(layer - 1).getNeurons()) {
			n.setError(n.getOut() * (1 - n.getOut()) * (outputVector[counter] - n.getOut()));
			counter++;
		}

		// pozosta³ê warstwy poza wejsciow¹
		for (int currentLayerNumber = layer; currentLayerNumber > 2; currentLayerNumber--) {
			int l = 0; // numer wagi
			for (Neuron n : this.neuronNetwork.get(currentLayerNumber - 2).getNeurons()) {
				for (Neuron neuronFromPrevLayer : this.neuronNetwork.get(currentLayerNumber - 1).getNeurons()) {
					delta += neuronFromPrevLayer.getError() * neuronFromPrevLayer.getWeights().get(l);
				}
				n.setError(delta * (n.getOut() * (1 - n.getOut())));
				l++;
			}
		}
	}

	// public void countErrors(double[] outputVector) {
	//
	// int layer = this.neuronNetwork.size();
	// double delta = 0d;
	// int counter = 0;
	//
	// for (Neuron n : this.neuronNetwork.get(layer - 1).getNeurons()) {
	// n.setError(n.getOut()*(1-n.getOut())*(outputVector[counter]-n.getOut()));
	// counter++;
	// }
	//
	// int l = 0; // numer wagi
	// for (Neuron n : this.neuronNetwork.get(layer - 2).getNeurons()) {
	// for (Neuron nlast : this.neuronNetwork.get(layer - 1).getNeurons()) {
	// delta += nlast.getError()*nlast.getWeights().get(l);
	// }
	// n.setError(delta*(n.getOut()*(1-n.getOut())));
	// l++;
	// }
	// }

	/*
	 * obliczanie b³êdu œredniokwadratowego dla podanego wektora wejœciowego i
	 * wybranego zbioru wag
	 */
	public double meanSquareError2(double[] inputVector) {

		int layer = this.neuronNetwork.size();
		SingleLayer sLayer = this.neuronNetwork.get(layer - 1);
		int neuronCount = sLayer.getNeurons().size();
		if (Constants.DEBUG) {
			System.out.println("\r\nb³¹d œredniokwadratowy:");
		}

		double delta = 0d;
		for (int i = 0; i < neuronCount; i++) {
			double out = sLayer.getNeurons().get(i).getOut();
			double sq = out - inputVector[i];
			delta += sq * sq;
			if (Constants.DEBUG) {
				System.out.println("out: " + out + " inputVector[" + i + "]: " + inputVector[i] + " delta: " + delta);
			}
		}
		if (Constants.DEBUG) {
			System.out.println("[" + delta + "/" + neuronCount + "]");
		}
		return delta;
	}

	/*
	 * obliczanie b³êdu œredniokwadratowego dla podanego wektora wejœciowego
	 */
	public double meanSquareError(double[] inputVector) {

		int layer = this.neuronNetwork.size();
		SingleLayer sLayer = this.neuronNetwork.get(layer - 1);
		int neuronCount = sLayer.getNeurons().size();
		if (Constants.DEBUG) {
			System.out.println("\r\nb³¹d œredniokwadratowy:");
		}

		double delta = 0d;
		for (int i = 0; i < neuronCount; i++) {
			double out = sLayer.getNeurons().get(i).getOut();
			double sq = out - inputVector[i];
			delta += sq * sq;
			if (Constants.DEBUG) {
				System.out.println("out: " + out + " inputVector[" + i + "]: " + inputVector[i] + " delta: " + delta);
			}
		}
		if (Constants.DEBUG) {
			System.out.println("[" + delta + "/" + neuronCount + "]");
		}
		return delta / neuronCount;
	}

	/*
	 * przeliczenie wag neuronów w sieci
	 * 
	 */
	public void recalculateWeights() {

		int numberOfLayers = this.neuronNetwork.size();
		SingleLayer layer = this.neuronNetwork.get(numberOfLayers - 1); // ostatnia
																		// warstwa
		ArrayList<Neuron> neurons = layer.getNeurons();

		SingleLayer layer_1 = this.neuronNetwork.get(numberOfLayers - 2); // œrodkowa
																			// warstwa
		ArrayList<Neuron> neurons_1 = layer_1.getNeurons();

		for (Neuron n : neurons) {
			ArrayList<Double> newWeights = new ArrayList<>();
			for (int i = 0; i < n.getWeights().size(); i++) {
				newWeights.add(n.getWeights().get(i) + (Constants.EPSILON * n.getError())
						* (i == n.getWeights().size() - 1 ? this.bias : neurons_1.get(i).getOut()));
			}
			n.getWeights().clear();
			n.setWeights(newWeights);
		}

		SingleLayer layer_2 = this.neuronNetwork.get(numberOfLayers - 3); // pierwsza
																			// warstwa
		ArrayList<Neuron> neurons_2 = layer_2.getNeurons();

		for (Neuron n : neurons_1) {
			ArrayList<Double> newWeights = new ArrayList<>();
			for (int i = 0; i < n.getWeights().size(); i++) {
				newWeights.add(n.getWeights().get(i) + (Constants.EPSILON * n.getError())
						* (i == n.getWeights().size() - 1 ? this.bias : neurons_2.get(i).getOut()));
			}
			n.getWeights().clear();
			n.setWeights(newWeights);
		}

	}

	public void setWeights(Chromosom weights) {
		int countWeights = 0;
		int numberOfLayers = this.neuronNetwork.size();
		SingleLayer layer = this.neuronNetwork.get(numberOfLayers - 1); // ostatnia
																		// warstwa
		ArrayList<Neuron> neurons = layer.getNeurons();

		SingleLayer layer_1 = this.neuronNetwork.get(numberOfLayers - 2); // œrodkowa
																			// warstwa
		ArrayList<Neuron> neurons_1 = layer_1.getNeurons();

		for (Neuron n : neurons) {
			ArrayList<Double> newWeights = new ArrayList<>();
			for (int i = 0; i < n.getWeights().size(); i++) {
				newWeights.add(weights.getChromosomDec().get(countWeights));
				// System.out.println("USTAWIONO WAGE NR: " + countWeights);
				countWeights++;
			}
			n.getWeights().clear();
			n.setWeights(newWeights);
		}

		SingleLayer layer_2 = this.neuronNetwork.get(numberOfLayers - 3); // pierwsza
																			// warstwa
		ArrayList<Neuron> neurons_2 = layer_2.getNeurons();

		for (Neuron n : neurons_1) {
			ArrayList<Double> newWeights = new ArrayList<>();
			for (int i = 0; i < n.getWeights().size(); i++) {
				newWeights.add(weights.getChromosomDec().get(countWeights));
				// System.out.println("USTAWIONO WAGE NR: " + countWeights);
				countWeights++;
			}
			n.getWeights().clear();
			n.setWeights(newWeights);
		}
		countWeights = 0;
	}

	public ArrayList<SingleLayer> getNeuronNetwork() {
		return neuronNetwork;
	}

	public void setNeuronNetwork(ArrayList<SingleLayer> neuronNetwork) {
		this.neuronNetwork = neuronNetwork;
	}

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	public int getBias() {
		return bias;
	}

	public void setBias(int bias) {
		this.bias = bias;
	}

	@Override
	public String toString() { // cala siec wraz z wagami i b³êdami neuronów
		if (this.neuronNetwork != null && this.neuronNetwork.size() > 0) {
			String out = "";
			for (SingleLayer sl : this.neuronNetwork) {
				out += sl.toStringOutString();
			}
			return descrption + out;
		}
		return descrption + " is empty!";

	}

	public String output() { // tylko wyjœcia neuronów
		if (this.neuronNetwork != null && this.neuronNetwork.size() > 0) {
			String out = "\r\n hidden layers outs:\r\n";
			for (int l = 1; l < this.neuronNetwork.size() - 1; l++) {
				ArrayList<Neuron> neurons = this.neuronNetwork.get(l).getNeurons();
				for (Neuron n : neurons) {
					out += n.toStringOutNeuronOutput();
				}
				out += "\r\n";
			}

			out += "output layer outs:\r\n";
			ArrayList<Neuron> neurons2 = this.neuronNetwork.get(this.neuronNetwork.size() - 1).getNeurons();
			for (Neuron n : neurons2) {
				// out += n.toStringOutNeuronOutputRounded();
				out += n.toStringOutNeuronOutput();
			}
			return out + "\r\n";
		}
		return descrption + " is empty!";
	}

	/**
	 * *****************************************************
	 * 
	 * main method to count output (MADALINE)
	 * 
	 *********************************************************/
	public String checkLetter(ArrayList<Integer> inputList) {
		// normalizacja
		int ones = 0;
		ArrayList<Double> normalizedInputList = new ArrayList<>();
		// policzenie jedynek w literze
		for (Integer l : inputList) {
			if (l == 1) {
				ones++;
			}
		}
		System.out.println("wektor wejœciowy: " + inputList + " jedynki:" + ones);

		// zamiana jedynek na znormalizowan¹ wartoœæ
		for (Integer l : inputList) {
			if (l == 1) {
				normalizedInputList.add(1 / Math.sqrt(ones));
			} else {
				normalizedInputList.add(0d);
			}
		}

		System.out.println("obliczanie:");

		ArrayList<Neuron> sl = this.neuronNetwork.get(1).getNeurons(); // neurony
																		// rozpoznaj¹ce
																		// litery
		ArrayList<Double> resultAfterLastLayer = new ArrayList<>();
		for (Neuron n : sl) {
			double similarity = n.activate(normalizedInputList);
			resultAfterLastLayer.add(similarity);
			if (Constants.DEBUG) {
				System.out.println("dla litery " + Character.toString((char) Integer.parseInt(n.getDescription()))
						+ " podobieñstwo wynosi: " + similarity);
			}

		}

		return resultAfterLastLayer.toString();
	}

	public Chromosom getNajlepszy() {
		return najlepszy;
	}

	public void setNajlepszy(Chromosom najlepszy) {
		this.najlepszy = najlepszy;
	}

}
