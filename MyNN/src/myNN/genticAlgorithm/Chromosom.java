package myNN.genticAlgorithm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/*
 * Klasa reprezentuj¹ca obiekt chromosomu
 * pojedynczy chromosom to zbiór wszystkich wag sieci neuronowej
 */
public class Chromosom {

	public Chromosom() {
		super();
	}

	/*
	 * Domyœlny konstruktor tworzy obiekt chromosomu, który wczeœniej zosta³
	 * przygotowany programowo
	 */
	public Chromosom(LinkedList<Integer[]> chromosomBin) {
		super();
		this.chromosomBin = chromosomBin;
	}

	/*
	 * Lista zawieraj¹ca wagi w postaci binarnej ka¿dy pojedynczy element listy
	 * to pojedyncza waga
	 */
	private LinkedList<Integer[]> chromosomBin = new LinkedList<>();

	/*
	 * Lista zawieraj¹ca wagi w postaci dziesiêtnej ka¿da pojedyncza wartoœæ to
	 * pojedyncza waga
	 */
	private LinkedList<Double> chromosomDec = new LinkedList<>();

	/*
	 * B³¹d dla danego chromosomu
	 */
	private Double error = 0.0;

	/*
	 * Wartoœæ Funkcji Przystosowania Danego Chromosomu
	 */
	private Double fn = 0.0;

	/*
	 * Prawdopodobieñstwo wybrania danego chromosomu do dalszych prac
	 */
	private Double prawdopodobieñstwoWyboru = 0.0;

	/*
	 * Zakres ko³a ruletki
	 */
	private Double start = 0.0;
	private Double end = 0.0;

	/*
	 * Dekodowanie wszystkich wag zakodowanych w chromosomie utworzenie
	 * reprezentacji dziesiêtnej danego chromosomu
	 */
	public void dekodowanieChromosomu() {
		for (Integer[] bin : this.chromosomBin) {
//			this.chromosomDec.add(dekodowanieChromosomu(bin, -2.0, 4.0));
			this.chromosomDec.add(dekodowanieChromosomu(bin, -13.0, 26.0));
		}
	}

	// Dekodowanie Zgodne ze wzrorem umieszczonym w przyk³adzie
	private Double dekodowanieChromosomu(Integer[] osobnik, Double pocz¹tekZakresu, Double wielkoœæZakresu) {
		Double value = 0.0;
		int xPrim = 0;
		int pot = 0;
		for (int i = osobnik.length - 1; i >= 0; i--) {
			int val = (int) osobnik[i];
			xPrim += (int) (val * Math.pow(2, pot));
			pot++;
		}
		// Ustawienie Iloœci miejsc po przecinku oraz d³ugoœci chromosomu
		BigDecimal bd = new BigDecimal(pocz¹tekZakresu + (wielkoœæZakresu * xPrim) / (Math.pow(2.0, 18.0) - 1));
		BigDecimal rounded = bd.setScale(4, BigDecimal.ROUND_HALF_UP);

		return rounded.doubleValue();
	}

	public LinkedList<Integer[]> getChromosomBin() {
		return chromosomBin;
	}

	public void setChromosomBin(LinkedList<Integer[]> chromosomBin) {
		this.chromosomBin = chromosomBin;
	}

	public LinkedList<Double> getChromosomDec() {
		return chromosomDec;
	}

	public void setChromosomDec(LinkedList<Double> chromosomDec) {
		this.chromosomDec = chromosomDec;
	}

	public Double getFn() {
		return fn;
	}

	public void setFn(Double fn) {
		this.fn = fn;
	}

	public Double getPrawdopodobieñstwoWyboru() {
		return prawdopodobieñstwoWyboru;
	}

	public void setPrawdopodobieñstwoWyboru(Double prawdopodobieñstwoWyboru) {
		this.prawdopodobieñstwoWyboru = prawdopodobieñstwoWyboru;
	}

	public Double getError() {
		return error;
	}

	public void setError(Double error) {
		this.error = error;
	}

	public Double getStart() {
		return start;
	}

	public void setStart(Double start) {
		this.start = start;
	}

	public Double getEnd() {
		return end;
	}

	public void setEnd(Double end) {
		this.end = end;
	}

}
