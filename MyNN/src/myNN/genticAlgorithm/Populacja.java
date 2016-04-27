package myNN.genticAlgorithm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/*
 * Klasa Odpowiedzialna za utworzenie populacji osobników
 * Populacja sk³ada siê z osobników z przedzia³u [-2.0 ; 2,0]
 * Ka¿dy osobnik musi byæ wyznaczony z dok³adnoœci¹ do 4 zer po kropce dziesiêtnej
 * Ca³a Populacja sk³ada siê z 65535 osobników 
 * Wynika z tego ¿e ka¿dy osobnik musi byæ zakodowany na 16 bitach
 */
public class Populacja {

	private FunkcjaPrzystosowania fn = new FunkcjaPrzystosowania();

	private LinkedList<Object[]> fenotyp;
	private Object[] pierwszyOsobnik;
	private LinkedList<Chromosom> populacja = new LinkedList<>();
	private LinkedList<LinkedList<Object[]>> populacjaPoOceniePrzystosowania;
	private LinkedList<LinkedList<Object[]>> populacjaPoZdekodowaniu;

	private boolean start = true;

	/*
	 * Integer pop_size -> wielkoœæ populacji (ca³a przestrzeñ przeszukiwañ)
	 * Integer size -> iloœæ bitów potrzebna do zakodwoania pojedynczego
	 * osobnika
	 */
	public LinkedList<Object[]> utworzenieFenotypu(Integer pop_size, Integer size) {
		fenotyp = new LinkedList<>();
		for (Integer i = pop_size; i >= 0; i--) {

			Object[] osobnik = new Object[size];
			utwórzPierwszegoOsobnika(size);
			osobnik = pierwszyOsobnik;

			// Zamiana ka¿dego punktu z przestrzeni poszukiwañ na reprezentacje
			// binarn¹
			String strBit = Integer.toBinaryString(i);
			// Zamiana reprezentacji binarnej na tablice znaków [0,1]
			char[] charTab = strBit.toCharArray();

			if (strBit.toCharArray().length == size) {
				for (int j = size - 1; j >= 0; j--) {
					if (charTab[j] == 48) {
						osobnik[j] = 0;
					} else {
						osobnik[j] = 1;
					}
				}
			} else {
				for (int s = charTab.length - 1; s >= 0; s--) {
					int przesuniecie = size - charTab.length;
					if (charTab[s] == 48) {
						osobnik[s + przesuniecie] = 0;
					} else {
						osobnik[s + przesuniecie] = 1;
					}
				}
			}
			fenotyp.add(osobnik);
		}
		return fenotyp;
	}

	/*
	 * Prywanta metod s³u¿¹ca do utworzenia pierwszego osobnika o wartoœci 0
	 */
	private Object[] utwórzPierwszegoOsobnika(Integer size) {
		pierwszyOsobnik = new Object[size];

		for (int i = size - 1; i >= 0; i--) {
			pierwszyOsobnik[i] = 0;
		}

		return pierwszyOsobnik;
	}

	// Nowa wersja inicjacji Populacji zwracaj¹ca chromosom sk³adaj¹cy siê z
	// dowolnej liczby zakodowanych wag
	public LinkedList<Chromosom> inicjacjaPopulacji(LinkedList<Object[]> fenotyp, int pop_size, int chromosom_size) {
		for (int i = 0; i < pop_size; i++) {
			LinkedList<Object[]> ch = new LinkedList<>();
			Chromosom chromosom = new Chromosom();
			for (int j = 0; j < chromosom_size; j++) {
				Random r = new Random();
				int pozycja = r.nextInt(fenotyp.size() - 1);
				ch.add(fenotyp.get(pozycja));
				chromosom.setChromosomBin(ch);
			}

			populacja.add(chromosom);
		}
		return populacja;
	}

	// public LinkedList<LinkedList<Object[]>>
	// dekodowaniePopulacji(LinkedList<LinkedList<Object[]>> populcja) {
	//
	// populacjaPoZdekodowaniu = new LinkedList<>();
	//
	// for (LinkedList<Object[]> row : populacja) {
	// LinkedList<Object[]> chromosomPoZdekodowaniu = new LinkedList<>();
	// for (Object[] row2 : row) {
	// Object[] wektor = new Object[17];
	// for (int j = 0; j < wektor.length - 1; j++) {
	// wektor[j] = row2[j];
	// }
	// wektor[16] = dekodowanieChromosomu(row2, -2.0, 4.0);
	// chromosomPoZdekodowaniu.add(wektor);
	// }
	// populacjaPoZdekodowaniu.add(chromosomPoZdekodowaniu);
	// }
	// return populacjaPoZdekodowaniu;
	// }

	/*
	 * Ocena przystosowania ca³ego chromosomu czyli zbioru wszystkich wag z
	 * sieci neuronowej Ocenie podlega ca³y taki zbiór i wartoœæ oceny
	 * przystosowania jest to kwadrat b³edu na wyjœciu sieci po ustawieniu
	 * takiego zbioru wag wartoœæ przystosowania jest ostatnim elementem listy
	 * wag poniewa¿ na t¹ wartoœæ maj¹ wp³yw wszystkie wag wyznaczenie wartoœci
	 * przystosowania dla ka¿dej z wag nie ma sensnu!!!
	 */

	public LinkedList<LinkedList<Object[]>> ocenaPrzystosowaniaChromosomu(
			LinkedList<LinkedList<Object[]>> populacjaPoZdekodowaniu) {

		populacjaPoOceniePrzystosowania = new LinkedList<>();

		for (LinkedList<Object[]> row : populacjaPoZdekodowaniu) {
			double sum = 0.0;
			for (Object[] row2 : row) {
				Object[] wektor = new Object[17];
				for (int j = 0; j < wektor.length - 1; j++) {
					wektor[j] = row2[j];
				}
			}
			// populacjaPoOceniePrzystosowania.add();
		}
		return populacjaPoOceniePrzystosowania;
	}

	public LinkedList<Object[]> getFenotyp() {
		return fenotyp;
	}

	public void setFenotyp(LinkedList<Object[]> fenotyp) {
		this.fenotyp = fenotyp;
	}

	public LinkedList<Chromosom> getPopulacja() {
		return populacja;
	}

	public void setPopulacja(LinkedList<Chromosom> populacja) {
		this.populacja = populacja;
	}

	public LinkedList<LinkedList<Object[]>> getPopulacjaPoOceniePrzystosowania() {
		return populacjaPoOceniePrzystosowania;
	}

	public void setPopulacjaPoOceniePrzystosowania(LinkedList<LinkedList<Object[]>> populacjaPoOceniePrzystosowania) {
		this.populacjaPoOceniePrzystosowania = populacjaPoOceniePrzystosowania;
	}

	public LinkedList<LinkedList<Object[]>> getPopulacjaPoZdekodowaniu() {
		return populacjaPoZdekodowaniu;
	}

	public void setPopulacjaPoZdekodowaniu(LinkedList<LinkedList<Object[]>> populacjaPoZdekodowaniu) {
		this.populacjaPoZdekodowaniu = populacjaPoZdekodowaniu;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

}
