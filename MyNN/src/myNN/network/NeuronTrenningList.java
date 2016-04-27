package myNN.network;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa generuje i przechowuje liste wzorców treningowych
 * 
 */
public class NeuronTrenningList {
	
	/** Lista wzorców treningowych */
	ArrayList<TreningPattern> treningList = new ArrayList<TreningPattern>();
	
	/** 
	 * Konstruktor
	 */
	public NeuronTrenningList() {
		for (int i=0; i<Constants.HOW_MANY_TRENING_PATTERNS; i++) {
			this.treningList.add(new TreningPattern(getRandomList(), getRandomDouble()));
		}
		showTreningList();
	}
	
	private double getRandomDouble(){
		Random r = new Random();
		return r.nextDouble();
	}
	
	private ArrayList<Double> getRandomList(){
		ArrayList<Double> al = new ArrayList<>();
		for (int i=0; i<Constants.HOW_MANY_PATTERN_ELEMENTS; i++) {
			al.add(getRandomDouble());
		}
		return al;
	}
	
	/**
	 * wyœwietlenie wzorców treningowych
	 */
	public void showTreningList(){
		for (TreningPattern tp : this.treningList) {
			System.out.println(tp.getInputList().toString() + " " + tp.getOutput());
		}
	}

	// getters and setters
	public ArrayList<TreningPattern> getTreningList() {
		return treningList;
	}

	public void setTreningList(ArrayList<TreningPattern> treningList) {
		this.treningList = treningList;
	}

}
