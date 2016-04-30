package myNN.genticAlgorithm;

import java.util.LinkedList;
import java.util.Random;

public class Population {

	private Fenotyp fenotyp = new Fenotyp();

	/*
	 * Populacja jest to lista zawieraj¹ca chromosomy
	 */
	private LinkedList<Chromosom> populacja = new LinkedList<>();

	/*
	 * Pojedynczy Chromosm tworz¹cy populacje
	 */
	private Chromosom chromosom;

	/*
	 * Populacja pocz¹tkowa Losow wybrane chromosomy wymagane do rozpoczecia
	 * dzia³ania algorytmu genetycznego
	 */
	private LinkedList<Chromosom> populacjaPoczatkowa = new LinkedList<>();

	/*
	 * wartoœæ funkcji przystosowania ca³ej populacji
	 */
	private Double fn = 0.0;

	public void przygotowaniePopulacjiPoczatkowej(int fenotyp_size, int chromosom_size, int pop_size,
			int weights_count) {
		fenotyp.createFenotyp(fenotyp_size, chromosom_size);
		utworzeniePopulacjiPoczatkowej(fenotyp.getFenotyp(), pop_size, weights_count);
		// noweTworzeniePopulacjiPoczatkowej(fenotyp.getFenotyp(), pop_size,
		// weights_count);

		for (Chromosom ch : getPopulacjaPoczatkowa()) {
			ch.dekodowanieChromosomu();
		}
	}

	/*
	 * utworzenie pocz¹tkowej losowej populacji
	 */
	public LinkedList<Chromosom> utworzeniePopulacjiPoczatkowej(LinkedList<Integer[]> fenotyp, int pop_size,
			int chromosom_size) {

		for (int i = 0; i < pop_size; i++) {
			chromosom = new Chromosom();
			LinkedList<Integer[]> punktPrzestrzeni = new LinkedList<>();
			for (int j = 0; j < chromosom_size; j++) {
				Random r = new Random();
				punktPrzestrzeni.add(fenotyp.get(r.nextInt(fenotyp.size() - 1)));
				chromosom.setChromosomBin(punktPrzestrzeni);
			}
			populacjaPoczatkowa.add(chromosom);
		}

//		System.out.println(
//				"POPULACJA POWINA MIEÆ " + "!!!!! " + pop_size + " !!!!! " + " A MA " + populacjaPoczatkowa.size());
		return populacjaPoczatkowa;
	}

	public LinkedList<Chromosom> noweTworzeniePopulacjiPoczatkowej(LinkedList<Integer[]> fenotyp, int pop_size,
			int chromosom_size) {

		int przedzial = fenotyp.size() / 5;
		int counter = 0;
		int powtorzenia = pop_size / 5;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < powtorzenia; j++) {
				chromosom = new Chromosom();
				LinkedList<Integer[]> punktPrzestrzeni = new LinkedList<>();
				for (int chS = 0; chS < chromosom_size; chS++) {
					Random r = new Random();
					punktPrzestrzeni.add(fenotyp.get(r.nextInt(przedzial) + (przedzial * i)));
					chromosom.setChromosomBin(punktPrzestrzeni);
				}
				populacjaPoczatkowa.add(chromosom);
			}
		}

		return populacjaPoczatkowa;
	}

	public LinkedList<Chromosom> getPopulacja() {
		return populacja;
	}

	public void setPopulacja(LinkedList<Chromosom> populacja) {
		this.populacja = populacja;
	}

	public Double getFn() {
		return fn;
	}

	public void setFn(Double fn) {
		this.fn = fn;
	}

	public LinkedList<Chromosom> getPopulacjaPoczatkowa() {
		return populacjaPoczatkowa;
	}

	public void setPopulacjaPoczatkowa(LinkedList<Chromosom> populacjaPoczatkowa) {
		this.populacjaPoczatkowa = populacjaPoczatkowa;
	}

}
