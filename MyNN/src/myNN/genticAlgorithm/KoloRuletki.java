package myNN.genticAlgorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class KoloRuletki {

	private LinkedList<Chromosom> koloRuletki = new LinkedList<>();
	private LinkedList<Chromosom> wylosowaneOsobniki;
	private Double suma = 0.0;
	private Double sredniaWartoscPrzystosowaniaRodzicow = 0.0;

	public void koloRuletki(LinkedList<Chromosom> chromosomy) {
		prawdopodobienstwoWyboru(chromosomy);
		przydzielenieMiejscaNaKole(chromosomy);
		losowanie(chromosomy);
	}

	private void przydzielenieMiejscaNaKole(LinkedList<Chromosom> chromosomy) {
		double start = 0.0;
		double end = 0.0;
		double temp = 0.0;

		for (Chromosom ch : chromosomy) {

			start = temp;
			end = (start + (360.0 * (ch.getPrawdopodobieñstwoWyboru() / 100)));
			temp = end + 0.10;

			ch.setStart(start);
			ch.setEnd(end);
		}
	}

	/*
	 * Kolo ruletki zawiera Liste Obiektów Ka¿dy obiekt ma zakodowane 1.
	 * Chromosom w formie binarnej [11 pierwszych elementow] 2. Wartoœæ funkcji
	 * przystosowania dla danego chromosomu [12 element] 3. Prawdopodobienstwo
	 * wyboru danego chromosomu [13 element]
	 */
	private void prawdopodobienstwoWyboru(LinkedList<Chromosom> chromosomy) {

		suma = 0.0;

		sumaWartosciFunkcjiPrzystosowania(chromosomy);

		Double prawdopodobienstwo = 0.0;
		Double start = 0.0;
		for (Chromosom ch : chromosomy) {
			prawdopodobienstwo = (ch.getFn() / suma) * 100;
			ch.setPrawdopodobieñstwoWyboru(prawdopodobienstwo);
		}

	}

	// Do poprawy !!!!!
	private LinkedList<Chromosom> losowanie(LinkedList<Chromosom> chromosom) {
		wylosowaneOsobniki = new LinkedList<>();
		wylosowaneOsobniki.clear();

		LinkedList<Double> wylosowaneLiczby = new LinkedList<>();

		while (wylosowaneOsobniki.size() < 30) {
			Random r = new Random();
			double wylosowanaLiczba = r.nextInt(360);
			wylosowaneLiczby.add(wylosowanaLiczba);

			for (Chromosom ch : chromosom) {
				if (wylosowanaLiczba >= ch.getStart() && wylosowanaLiczba <= ch.getEnd()) {
					// System.out.println(ch.getStart() + "----" + ch.getEnd() +
					// "======" + wylosowanaLiczba);
					wylosowaneOsobniki.add(ch);
				}
			}
		}
		// if (wylosowaneOsobniki.size() < 10) {
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		// System.out.println(wylosowaneLiczby);
		// }
		return wylosowaneOsobniki;
	}

	// public LinkedList<Object[]>
	// losowanieOsobnikowKolaRuletki(LinkedList<Object[]> koloRuletki) {
	// wylosowaneOsobniki.clear();
	// // System.out.println("losowanieOsobnikowKolaRuletki: " +
	// // koloRuletki.size());
	// double sumaWartosciPrzystosowaniaChromosomow = 0.0;
	// for (int i = 0; i < koloRuletki.size(); i++) {
	// // System.out.println("LICZNIK: " + i);
	// // System.out.println("WYLOSOWANE OSOBNIKI SIZE: " +
	// // wylosowaneOsobniki.size());
	// Random r = new Random();
	// double wylosowanaLiczba = r.nextInt(100);
	// // System.out.println("WYLOSOWANA LICZBA TO: " + wylosowanaLiczba);
	// for (Object[] chromosom : koloRuletki) {
	// // System.out.println(Arrays.toString(chromosom));
	// if (wylosowanaLiczba >= (double) chromosom[17] && wylosowanaLiczba <=
	// (double) chromosom[18]) {
	//
	// Object[] chromosomCiagBinarny = new Object[16];
	// System.arraycopy(chromosom, 0, chromosomCiagBinarny, 0,
	// chromosomCiagBinarny.length);
	// wylosowaneOsobniki.add(chromosomCiagBinarny);
	// sumaWartosciPrzystosowaniaChromosomow += (double) chromosom[16];
	// }
	// // else {
	// // System.out.println("ELSE: " + wylosowanaLiczba);
	// // }
	// }
	// }
	// sredniaWartoscPrzystosowaniaRodzicow =
	// sumaWartosciPrzystosowaniaChromosomow / koloRuletki.size();
	// // System.out.println("losowanieOsobnikowKolaRuletki: " +
	// // koloRuletki.size());
	// // System.out.println("WYLOSOWANE OSOBNIKI: " +
	// // wylosowaneOsobniki.size());
	// return wylosowaneOsobniki;
	// }

	private Double sumaWartosciFunkcjiPrzystosowania(LinkedList<Chromosom> ocenionaPopulacja) {
		for (Chromosom wartoscFunkcjiPrzystosowania : ocenionaPopulacja) {
			suma += wartoscFunkcjiPrzystosowania.getFn();
		}
		return suma;
	}

	public LinkedList<Chromosom> getKoloRuletki() {
		return koloRuletki;
	}

	public void setKoloRuletki(LinkedList<Chromosom> koloRuletki) {
		this.koloRuletki = koloRuletki;
	}

	public LinkedList<Chromosom> getWylosowaneOsobniki() {
		return wylosowaneOsobniki;
	}

	public void setWylosowaneOsobniki(LinkedList<Chromosom> wylosowaneOsobniki) {
		this.wylosowaneOsobniki = wylosowaneOsobniki;
	}

	public Double getSredniaWartoscPrzystosowaniaRodzicow() {
		return sredniaWartoscPrzystosowaniaRodzicow;
	}

	public void setSredniaWartoscPrzystosowaniaRodzicow(Double sredniaWartoscPrzystosowaniaRodzicow) {
		this.sredniaWartoscPrzystosowaniaRodzicow = sredniaWartoscPrzystosowaniaRodzicow;
	}

	public Double getSuma() {
		return suma;
	}

	public void setSuma(Double suma) {
		this.suma = suma;
	}

}
