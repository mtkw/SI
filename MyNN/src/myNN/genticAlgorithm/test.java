package myNN.genticAlgorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class test {

	public static void main(String[] args) {
		AlgorytmGenetyczny algorytm = new AlgorytmGenetyczny();

		Fenotyp fenotyp = new Fenotyp();

		fenotyp.createFenotyp(65535, 16);

		Population pop = new Population();

		pop.utworzeniePopulacjiPoczatkowej(fenotyp.getFenotyp(), 10, 22);
		for (Chromosom row : pop.getPopulacjaPoczatkowa()) {
			row.dekodowanieChromosomu();
		}
		// for(Chromosom row: pop.getPopulacjaPoczatkowa()){
		//// System.out.println(row.getChromosomBin());
		// System.out.println(row.getChromosomDec());
		// System.out.println("---------------------------");
		// }
		System.out.println(pop.getPopulacjaPoczatkowa().getFirst().getChromosomBin().size());

		// FunkcjaPrzystosowania fn = new FunkcjaPrzystosowania();

		// fn.wyznaczenie_wartoœci_funckji_przystosowania(0.25);
		// System.out.println(fn.getWartoscFunckji());
		// algorytm.algorytmGenetyczny(65535, 16, 40, 90, 9, 1, 100);

		// Populacja pop = new Populacja();
		//// // Chromosom ch = new Chromosom();
		//// //
		// pop.utworzenieFenotypu(65535, 16);
		// pop.inicjacjaPopulacji(pop.getFenotyp(), 2, 2);
		//// System.out.println(pop.getPopulacja().getFirst().getChromosomBin().size());
		//// System.out.println(Arrays.toString(pop.getPopulacja().getFirst().getChromosomBin().getFirst()));
		//// System.out.println(Arrays.toString(pop.getPopulacja().getFirst().getChromosomBin().getLast()));
		// System.out.println("---------------------------------");
		// pop.getPopulacja().getFirst().dekodowanieChromosomu();
		// System.out.println("---------------------------------");
		// System.out.println(pop.getPopulacja().getFirst().getChromosomDec());
		// pop.dekodowaniePopulacji(pop.getPopulacja());

		// for(LinkedList<Object[]> row: pop.getPopulacjaPoZdekodowaniu()){
		// for(Object[] row2: row){
		// System.out.println(Arrays.toString(row2));
		// }
		// System.out.println("ENTER");
		// }
		//
		//
		// for(Object[] row: pop.getPopulacja()){
		// System.out.println(Arrays.toString(row) + " --> " +
		// pop.dekodowanieChromosomu(row, -2.0, 4.0));
		// }
		//
		// pop.ocenaPrzystosowaniaChromosomu(pop.getPopulacja());
		//
		// for(Object[] row: pop.getPopulacjaPoOceniePrzystosowania()){
		// System.out.println(Arrays.toString(row));
		// }

		// testy
		// Object[] ch = new Object[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

		//
		// // System.out.println(pop.dekodowanieChromosomu(ch, -1.0, 2.0));
		//
		// pop.utworzenieFenotypu(65535, 16);
		//// for (Object[] row : pop.getFenotyp()) {
		//
		// System.out.println(pop.dekodowanieChromosomu(pop.getFenotyp().getFirst(),
		// 0.5, 2.0));
		// System.out.println(pop.dekodowanieChromosomu(pop.getFenotyp().getLast(),
		// 0.5, 2.0));
		// }
		// ch.start(pop.getFenotyp(), 3);
		//// System.out.println(ch.getChromosom().size());
		// for(Object[] row: ch.getChromosomBin()){
		// System.out.println(Arrays.toString(row));
		// }
		// ch.dekodowanieChromosomu(ch.getChromosomBin());
		// for(Double row: ch.getChromosomDec()){
		// System.out.println(row);
		// }
		//
		// FunkcjaPrzystosowania fn = new FunkcjaPrzystosowania();
		//
		// fn.wyznaczenie_wartoœci_funckji_przystosowania(ch.getChromosomDec());
		// System.out.println("Wartoœæ Funkcji :" + fn.getWartoscFunckji());
		//// System.out.println(pop.dekodowanieChromosomu(pop.getFenotyp().get(30000),
		// -1.0, 2.0));
		//// System.out.println(pop.dekodowanieChromosomu(pop.getFenotyp().getLast(),
		// -1.0, 2.0));

	}

}
