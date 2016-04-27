package myNN.network;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa przechowuj¹ca wzorce treningowe (lista zawieraj¹ca wejœcia i wyjœcie)
 * 
 */
public class TreningPattern implements Serializable {
	
	/** list of double values to set for inputs of neuron */
	ArrayList<Double> inputList = new ArrayList<Double>();
	
	/** value of the output for specified input list */
	double output = 0;
	
	/** array of double - input */
	private double[] inputArray;
	
	/** array of double - output */
	private double[] outputArray;
	
	/**
	 * constructors
	 */
	public TreningPattern() {
	}
	
	public TreningPattern(ArrayList<Double> inputList, double output){
		this.inputList = inputList;
		this.output = output;
	}

	public TreningPattern(double[] inputArray, double[] outputArray) {
		this.inputArray = inputArray;
		this.outputArray = outputArray;
	}

	/**
	 *  getters and setters
	 */
	public ArrayList<Double> getInputList() {
		return inputList;
	}

	public void setInputList(ArrayList<Double> inputList) {
		this.inputList = inputList;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double[] getInputArray() {
		return inputArray;
	}

	public void setInputArray(double[] inputArray) {
		this.inputArray = inputArray;
	}

	public double[] getOutputArray() {
		return outputArray;
	}

	public void setOutputArray(double[] outputArray) {
		this.outputArray = outputArray;
	}
	

	
}
