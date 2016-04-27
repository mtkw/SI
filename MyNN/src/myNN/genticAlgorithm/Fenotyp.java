package myNN.genticAlgorithm;

import java.util.LinkedList;

/*
 * Klasa odpowiedzialna za utworzenie Fenotypu,
 * czyli zbioru wszystkich mo�liwych punkt�w w przestrzeni
 * poszukiwa� danego zadania
 */
public class Fenotyp {

	/*
	 * Domy�lny Konstruktor
	 */
	public Fenotyp() {
		super();
	}

	/*
	 * Lista tablic typu Double zawieraj�ca wszystkie mo�liwe punkty wybranego
	 * zbioru przestrzeni poszukiwa� zakodowane w postaci binarnej [0,1]
	 */
	private LinkedList<Integer[]> fenotyp = new LinkedList<>();
	private Integer[] pierwszyPunkt;

	public LinkedList<Integer[]> createFenotyp(int fenotyp_size, int bin_representation_size) {
		for (int i = 0; i < fenotyp_size; i++) {
			Integer[] punkt_przestrzeni_poszukiwan = new Integer[bin_representation_size];
			utw�rzPierwszegoOsobnika(bin_representation_size);
			punkt_przestrzeni_poszukiwan = pierwszyPunkt;

			// Zamiana ka�dego punktu z przestrzeni poszukiwa� na reprezentacje
			// binarn�
			String strBit = Integer.toBinaryString(i);
			// Zamiana reprezentacji binarnej na tablice znak�w [0,1]
			char[] charTab = strBit.toCharArray();

			if (strBit.toCharArray().length == bin_representation_size) {
				for (int j = bin_representation_size - 1; j >= 0; j--) {
					if (charTab[j] == 48) {
						punkt_przestrzeni_poszukiwan[j] = 0;
					} else {
						punkt_przestrzeni_poszukiwan[j] = 1;
					}
				}
			} else {
				for (int s = charTab.length - 1; s >= 0; s--) {
					int przesuniecie = bin_representation_size - charTab.length;
					if (charTab[s] == 48) {
						punkt_przestrzeni_poszukiwan[s + przesuniecie] = 0;
					} else {
						punkt_przestrzeni_poszukiwan[s + przesuniecie] = 1;
					}
				}
			}
			fenotyp.add(punkt_przestrzeni_poszukiwan);

		}
		return fenotyp;
	}

	/*
	 * Prywanta metod s�u��ca do utworzenia pierwszego osobnika o warto�ci 0
	 */
	private Integer[] utw�rzPierwszegoOsobnika(Integer size) {
		pierwszyPunkt = new Integer[size];

		for (int i = size - 1; i >= 0; i--) {
			pierwszyPunkt[i] = 0;
		}

		return pierwszyPunkt;
	}

	public LinkedList<Integer[]> getFenotyp() {
		return fenotyp;
	}

	public void setFenotyp(LinkedList<Integer[]> fenotyp) {
		this.fenotyp = fenotyp;
	}

}