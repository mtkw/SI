package myNN.genticAlgorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class MetodyGenetyczne {

	private LinkedList<Chromosom> paraPotomstwa;
	private LinkedList<Chromosom> potomstwo;
	private LinkedList<LinkedList<Object[]>> pary;

	private LinkedList<LinkedList<Chromosom>> pary2 = new LinkedList<>();

	public LinkedList<LinkedList<Chromosom>> podzia³NaPary(LinkedList<Chromosom> pulaRodzicielska) {
		int half = pulaRodzicielska.size() / 2;
		for (int i = 0; i < pulaRodzicielska.size() / 2; i++) {
			LinkedList<Chromosom> para = new LinkedList<>();
			para.add(pulaRodzicielska.get(i));
			para.add(pulaRodzicielska.get(i + (half)));
			pary2.add(para);
		}

		return pary2;
	}

	public LinkedList<Chromosom> metodyGenetyczne(int pk, int pr, int pm, LinkedList<LinkedList<Chromosom>> pary) {

		potomstwo = new LinkedList<>();

		for (int i = 0; i < pary.size(); i++) {
			Random r = new Random();
			int rn = r.nextInt(100);

			if (rn <= pk) {
				// krzy¿owanie
				// noweKrzyzowanieProste(pary.get(i));
				noweKrzyzowanieZlorzone(pary.get(i));
			} else if (rn > pk && rn <= pk + pr) {
				// Reprodukcja
				nowaReprodukcja(pary.get(i));
			} else if (rn > pk + pr) {
				// Mutacja
				nowaMutacja(pary.get(i), 1);
			}

		}

		return potomstwo;
	}

	private LinkedList<Chromosom> noweKrzyzowanieProste(LinkedList<Chromosom> paraRodzicielska) {

		Chromosom r1 = paraRodzicielska.getFirst();
		Chromosom r2 = paraRodzicielska.getLast();

		Random r = new Random();
		int punktKrzyzowania = r.nextInt(r1.getChromosomBin().size() - 1) + 1;

		LinkedList<Integer[]> p1 = new LinkedList<>();
		LinkedList<Integer[]> p2 = new LinkedList<>();

		for (int i = 0; i < punktKrzyzowania; i++) {
			p1.add(r1.getChromosomBin().get(i));
			p2.add(r2.getChromosomBin().get(i));
		}

		for (int j = punktKrzyzowania; j < r1.getChromosomBin().size(); j++) {
			p1.add(r2.getChromosomBin().get(j));
			p2.add(r1.getChromosomBin().get(j));
		}

		Chromosom potomek1 = new Chromosom(p1);
		Chromosom potomek2 = new Chromosom(p2);

		potomstwo.add(potomek1);
		potomstwo.add(potomek2);

		return potomstwo;
	}

	private LinkedList<Chromosom> noweKrzyzowanieZlorzone(LinkedList<Chromosom> paraRodzicielska) {

		int countWeight = paraRodzicielska.getFirst().getChromosomBin().size(); // wszystkie
																				// wagi
																				// w
																				// chromosomie
																				// zakodowane
		int binRepWeightSize = paraRodzicielska.getFirst().getChromosomBin().getFirst().length; // wielkoœæ
																								// binarna
																								// pojednycznej
																								// wagi

		Chromosom r1 = paraRodzicielska.getFirst();
		Chromosom r2 = paraRodzicielska.getLast();

		LinkedList<Integer[]> p1 = new LinkedList<>();
		LinkedList<Integer[]> p2 = new LinkedList<>();

		// Iterowanie po Liœcie Wag
		for (int i = 0; i < countWeight; i++) {

			Integer[] w1 = new Integer[binRepWeightSize];
			Integer[] w2 = new Integer[binRepWeightSize];
			Integer[] w2Clone = new Integer[binRepWeightSize];

			w1 = r1.getChromosomBin().get(i);
			w2 = r2.getChromosomBin().get(i);
			w2Clone = w2.clone();

			// Wylosowanie punktu krzyzowania
			Random r = new Random();
			int punktKrzyzowania = r.nextInt(binRepWeightSize - 1) + 1;
			int liczbaElementow = binRepWeightSize - punktKrzyzowania;

			// Krzy¿owanie w postaci kopiowania odpowiednich elementów tablic
			System.arraycopy(w1, punktKrzyzowania, w2, punktKrzyzowania, liczbaElementow);
			System.arraycopy(w2Clone, punktKrzyzowania, w1, punktKrzyzowania, liczbaElementow);

			p1.add(w1);
			p2.add(w2);
		}

		Chromosom potomek1 = new Chromosom(p1);
		Chromosom potomek2 = new Chromosom(p2);

		potomstwo.add(potomek1);
		potomstwo.add(potomek2);

		return potomstwo;
	}

	public LinkedList<Chromosom> nowaMutacja(LinkedList<Chromosom> paraRodzicielska, int wagiPodlegaj¹ceMutacji) {

		// Geny podlegaj¹ce mutacji
		Integer[] gen1 = new Integer[paraRodzicielska.getLast().getChromosomBin().size()];
		Integer[] gen2 = new Integer[paraRodzicielska.getLast().getChromosomBin().size()];

		LinkedList<Integer[]> noweWagi1 = new LinkedList<>();
		LinkedList<Integer[]> noweWagi2 = new LinkedList<>();

		if (wagiPodlegaj¹ceMutacji != 1) {

		} else {
			Random r = new Random();
			int numerWagi = r.nextInt(paraRodzicielska.getLast().getChromosomBin().size() - 1);

			gen1 = paraRodzicielska.getFirst().getChromosomBin().get(numerWagi);
			gen2 = paraRodzicielska.getLast().getChromosomBin().get(numerWagi);

			for (int i = 0; i < gen1.length; i++) {
				if (gen1[i] == 0) {
					gen1[i] = 1;
				} else {
					gen1[i] = 0;
				}
			}

			for (int i = 0; i < gen2.length; i++) {
				if (gen2[i] == 0) {
					gen2[i] = 1;
				} else {
					gen2[i] = 0;
				}
			}

			for (int i = 0; i < numerWagi; i++) {
				noweWagi1.add(paraRodzicielska.getFirst().getChromosomBin().get(i));
			}
			noweWagi1.add(gen1);

			for (int i = numerWagi + 1; i < paraRodzicielska.getFirst().getChromosomBin().size(); i++) {
				noweWagi1.add(paraRodzicielska.getFirst().getChromosomBin().get(i));
			}

			for (int i = 0; i < numerWagi; i++) {
				noweWagi2.add(paraRodzicielska.getLast().getChromosomBin().get(i));
			}
			noweWagi2.add(gen2);

			for (int i = numerWagi + 1; i < paraRodzicielska.getLast().getChromosomBin().size(); i++) {
				noweWagi2.add(paraRodzicielska.getLast().getChromosomBin().get(i));
			}

			Chromosom ch1 = new Chromosom(noweWagi1);
			Chromosom ch2 = new Chromosom(noweWagi2);

			potomstwo.add(ch1);
			potomstwo.add(ch2);

		}

		return potomstwo;
	}

	private LinkedList<Chromosom> nowaReprodukcja(LinkedList<Chromosom> paraRodzicielska) {

		Chromosom ch1 = new Chromosom(paraRodzicielska.getFirst().getChromosomBin());
		Chromosom ch2 = new Chromosom(paraRodzicielska.getLast().getChromosomBin());

		potomstwo.add(ch1);
		potomstwo.add(ch2);

		return potomstwo;
	}

	/*
	 * TO DO!!!
	 */
	// private LinkedList<Chromosom>
	// noweKrzyzowanieZlorzone(LinkedList<Chromosom> paraRodzicielska) {
	//
	// Chromosom r1 = paraRodzicielska.getFirst();
	// Chromosom r2 = paraRodzicielska.getLast();
	//
	// return paraPotomstwa;
	// }

	private LinkedList<Object[]> krzyzowanie(LinkedList<Object[]> paraRodzicielska) {
		LinkedList<Object[]> potomkowie = new LinkedList<>();

		Object[] rodzic1 = paraRodzicielska.getFirst();
		Object[] rodzic2 = paraRodzicielska.get(1);
		Object[] rodzic2Clone = rodzic2.clone();

		Random r = new Random();
		int punktKrzyzowania = r.nextInt(rodzic1.length - 1) + 1;
		int liczbaElementow = rodzic1.length - punktKrzyzowania;

		System.arraycopy(rodzic1, punktKrzyzowania, rodzic2, punktKrzyzowania, liczbaElementow);
		System.arraycopy(rodzic2Clone, punktKrzyzowania, rodzic1, punktKrzyzowania, liczbaElementow);

		potomkowie.add(rodzic1);
		potomkowie.add(rodzic2);
		return potomkowie;
	}

	private LinkedList<Object[]> mutacja(LinkedList<Object[]> paraRodzicielska) {
		LinkedList<Object[]> potomkowie = new LinkedList<>();
		for (int i = 0; i < 2; i++) {
			Random r = new Random();
			int genPodlegajacyMutacji = r.nextInt(paraRodzicielska.get(i).length - 1) + 1;
			Object[] zmutowanyOsobnik = paraRodzicielska.get(i);
			if ((int) zmutowanyOsobnik[genPodlegajacyMutacji] == 0) {
				zmutowanyOsobnik[genPodlegajacyMutacji] = 1;
			} else {
				zmutowanyOsobnik[genPodlegajacyMutacji] = 0;
			}
			potomkowie.add(zmutowanyOsobnik);
		}

		return potomkowie;
	}

	private LinkedList<Object[]> reprodukcja(LinkedList<Object[]> paraRodzicielska) {
		LinkedList<Object[]> potomkowie = new LinkedList<>();
		potomkowie.add(paraRodzicielska.getFirst());
		potomkowie.add(paraRodzicielska.get(1));

		return potomkowie;
	}

	/*
	 * Ka¿ada utworzona para ma odrazu przypisan¹ wylosowan¹ liczbê Liczba ta
	 * okreœla zgodnie z parametrami zadania jakiej meotdzie zostanie poddana
	 * dana para Krzyo¿waniu, mutacji czy reprodukcji
	 */
	public LinkedList<LinkedList<Object[]>> podzialPopulacjiNaPary(LinkedList<Object[]> pulaRodzicielska) {

		pary = new LinkedList<>();

		int halfSize = pulaRodzicielska.size() / 2;

		for (int i = 0; i < halfSize; i++) {
			LinkedList<Object[]> pojedynczaPara = new LinkedList<>();

			Random r = new Random();
			Object[] wylosowanaLiczba = { r.nextInt(100) };
			pojedynczaPara.add(pulaRodzicielska.get(i));
			pojedynczaPara.add(pulaRodzicielska.get(i + halfSize));
			pojedynczaPara.add(wylosowanaLiczba);
			pary.add(pojedynczaPara);
		}

		return pary;
	}

	// private void dodanieDoListyPotomkow(LinkedList<Object[]>
	// potomkowiePoMetodachGenetycznych) {
	// for (Object[] row : potomkowiePoMetodachGenetycznych) {
	// potomstwo.add(row);
	// }
	// }

	public LinkedList<Chromosom> getPotomstwo() {
		return potomstwo;
	}

	public void setPotomstwo(LinkedList<Chromosom> potomstwo) {
		this.potomstwo = potomstwo;
	}

	public LinkedList<LinkedList<Chromosom>> getPary2() {
		return pary2;
	}

	public void setPary2(LinkedList<LinkedList<Chromosom>> pary2) {
		this.pary2 = pary2;
	}

}
