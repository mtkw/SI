package myNN.genticAlgorithm;

import java.util.LinkedList;

public class AlgorytmGenetyczny {

	private Populacja populacja = new Populacja();
	private FunkcjaPrzystosowania fn = new FunkcjaPrzystosowania();
	private KoloRuletki ruletka = new KoloRuletki();
	private MetodyGenetyczne metody = new MetodyGenetyczne();

	/*
	 * Ten obiekt zawiera 4 elementy -> 1. Wartoœæ dziesiêtn¹ najlepszego
	 * chromosomu z pokolenia 2. Wartoœæ Funkcji Przystosowania danego
	 * chromosomu 3. Numer Pokolenia w którym zosta³ znaleziony 4. Œrednia
	 * Wartoœæ przystosowania ca³ego pokolenia
	 */
	private LinkedList<LinkedList<Double>> najlepszy = new LinkedList<>();

	private int licznik_epok = 0;

	/*
	 * fenotyp_size -> wielkoœæ przesztrzeni przeszukiwania chromosom_size ->
	 * wielkoœæ pojedynczego osobnika iloœæ bitów go opisuj¹ca pop_size ->
	 * wielkoœæ populacji dla pojedynczej epoki pk -> prawdopodobienstow
	 * wyst¹pienia krzyzowania pr -> prawdopodobienstwo wyst¹pienia reprodukcji
	 * pm -> prawdopodobienstwo wystapienia mutacji liczba_epok -> liczba epok
	 * algorytmu genetycznego
	 * 
	 */
	public void algorytmGenetyczny(int fenotyp_size, int chromosom_size, int pop_size, int pk, int pr, int pm,
			int liczba_epok) {

		// Utworzenie zbioru przeszukiwañ danego problemu i okreslenie wielkoœci
		// chromosomu
		populacja.utworzenieFenotypu(fenotyp_size, chromosom_size);

		// G³ówna pêtla algorytmu warunkiem stopu jest osi¹gniêcie okreœlonej
		// liczby epok
		while (liczba_epok >= licznik_epok) {

			// Podczas pierwszej iteracji zostanie zainicjowana nowa populacja
			// Za ka¿dym kolejnym razem bedzie pobierana populacaja potomków
			// powsta³a po wykonaniu metod genetycznych
			if (populacja.getPopulacja().isEmpty()) {
				populacja.inicjacjaPopulacji(populacja.getFenotyp(), pop_size);
				// System.out.println(populacja.getPopulacja().isEmpty());

			} else {
				// System.out.println("Ustawienie Potomstwa");
				populacja.setPopulacja(metody.getPotomstwo());
			}
			// System.out.println("---------------------------------------------"+populacja.getPopulacja().size());

			// Ocena przystosowania osobników
			populacja.ocenaPrzystosowaniaChromosomu(populacja.getPopulacja());

			// Selekcja osobników do dalszych procesów i przydzielenie wycinków
			// ko³a dla kazdego z nich
			ruletka.prawdopodobienstwoWyboru(populacja.getPopulacjaPoOceniePrzystosowania());
			// System.out.println("WIELKOŒÆ POPULACJI W ALGORYTMIE: " +
			// populacja.getPopulacja().size());
			ruletka.losowanieOsobnikowKolaRuletki(ruletka.getKoloRuletki());
			// System.out.println(ruletka.getWylosowaneOsobniki().size());
			// Zastosowanie operatorów genetycznych
			metody.ustawienieParametrow(pk, pr, pm, ruletka.getWylosowaneOsobniki());

			// System.out.println(metody.getPotomstwo().size());

			// Ustawienie nowej populacji poczatkowej jako powsta³e potomstwo

			// populacja.setPopulacja(metody.getPotomstwo());
			najlepszyWEpoce(metody.getPotomstwo(), licznik_epok);
			System.out.println("Œrednia " + ruletka.getSredniaWartoscPrzystosowaniaRodzicow());
			licznik_epok++;

			// for(Object[] row: metody.getPotomstwo()){
			// System.out.println(row.length);
			// }
			// System.out.println("Œrednia " +
			// ruletka.getSredniaWartoscPrzystosowaniaRodzicow());
			// System.out.println("--->");
		}
		
		//Sprawdzanie Danych Koñcowych
		for (LinkedList<Double> row : najlepszy) {
			System.out.println((double)row.get(0));
		}
//		System.out.println("------------");
//		for (LinkedList<Double> row : najlepszy) {
//			System.out.println((double)row.get(1));
//		}
		System.out.println("------------");
//		for (LinkedList<Double> row : najlepszy) {
//			System.out.println(row.get(2));
//		}
		System.out.println("------------");
	}

	private LinkedList<LinkedList<Double>> najlepszyWEpoce(LinkedList<Object[]> potomstwo, int numerEpoki) {
		Double temp = 0.0;
		Double best = 0.0;
		Double bestVal = 0.0;
		LinkedList<Double> najlepszyWEpoce = new LinkedList<>();

		for (Object[] row : potomstwo) {
			Double val = populacja.dekodowanieChromosomu(row, 0.5, 2.0);
			temp = fn.wyznaczenie_wartoœci_funckji_przystosowania(val);

			if (temp > best) {
				best = temp;
				bestVal = populacja.dekodowanieChromosomu(row, 0.5, 2.0);
			}
		}
		najlepszyWEpoce.add(bestVal);
		najlepszyWEpoce.add(best);
		najlepszyWEpoce.add((double) numerEpoki);
		najlepszy.add(najlepszyWEpoce);

		return najlepszy;
	}
}
