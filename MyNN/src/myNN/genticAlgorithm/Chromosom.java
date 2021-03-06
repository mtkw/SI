package myNN.genticAlgorithm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/*
 * Klasa reprezentuj�ca obiekt chromosomu
 * pojedynczy chromosom to zbi�r wszystkich wag sieci neuronowej
 */
public class Chromosom {

	public Chromosom() {
		super();
	}

	/*
	 * Domy�lny konstruktor tworzy obiekt chromosomu, kt�ry wcze�niej zosta�
	 * przygotowany programowo
	 */
	public Chromosom(LinkedList<Integer[]> chromosomBin) {
		super();
		this.chromosomBin = chromosomBin;
	}

	/*
	 * Lista zawieraj�ca wagi w postaci binarnej ka�dy pojedynczy element listy
	 * to pojedyncza waga
	 */
	private LinkedList<Integer[]> chromosomBin = new LinkedList<>();

	/*
	 * Lista zawieraj�ca wagi w postaci dziesi�tnej ka�da pojedyncza warto�� to
	 * pojedyncza waga
	 */
	private LinkedList<Double> chromosomDec = new LinkedList<>();

	/*
	 * B��d dla danego chromosomu
	 */
	private Double error = 0.0;

	/*
	 * Warto�� Funkcji Przystosowania Danego Chromosomu
	 */
	private Double fn = 0.0;

	/*
	 * Prawdopodobie�stwo wybrania danego chromosomu do dalszych prac
	 */
	private Double prawdopodobie�stwoWyboru = 0.0;

	/*
	 * Zakres ko�a ruletki
	 */
	private Double start = 0.0;
	private Double end = 0.0;

	/*
	 * Dekodowanie wszystkich wag zakodowanych w chromosomie utworzenie
	 * reprezentacji dziesi�tnej danego chromosomu
	 */
	public void dekodowanieChromosomu() {
		for (Integer[] bin : this.chromosomBin) {
//			this.chromosomDec.add(dekodowanieChromosomu(bin, -2.0, 4.0));
			this.chromosomDec.add(dekodowanieChromosomu(bin, -13.0, 26.0));
		}
	}

	// Dekodowanie Zgodne ze wzrorem umieszczonym w przyk�adzie
	private Double dekodowanieChromosomu(Integer[] osobnik, Double pocz�tekZakresu, Double wielko��Zakresu) {
		Double value = 0.0;
		int xPrim = 0;
		int pot = 0;
		for (int i = osobnik.length - 1; i >= 0; i--) {
			int val = (int) osobnik[i];
			xPrim += (int) (val * Math.pow(2, pot));
			pot++;
		}
		// Ustawienie Ilo�ci miejsc po przecinku oraz d�ugo�ci chromosomu
		BigDecimal bd = new BigDecimal(pocz�tekZakresu + (wielko��Zakresu * xPrim) / (Math.pow(2.0, 18.0) - 1));
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

	public Double getPrawdopodobie�stwoWyboru() {
		return prawdopodobie�stwoWyboru;
	}

	public void setPrawdopodobie�stwoWyboru(Double prawdopodobie�stwoWyboru) {
		this.prawdopodobie�stwoWyboru = prawdopodobie�stwoWyboru;
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
