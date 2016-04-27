package myNN.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/*
 * Klasa reprezentuj¹ca pojedynczy neuron
 * wagi
 * b³ad
 * funkcje aktywacji
 */

public class Neuron implements Serializable {

	// Lista wag danego neuronu
	private ArrayList<Double> weights = new ArrayList<>();

	// Funckja aktywacji
	private ActivationFunction aFunction = new ActivationFunction();

	// Opis neuronu
	private String description = "...";

	// B³¹ danego neuronu
	private double error = 0d;

	// Wyjœcie z neuronu
	private double out = 0d;

	// Suma wag
	private double weightedSum = 0d;

	/**
	 * Konstruktory
	 */
	public Neuron() {
		this.weights = new ArrayList<>();
		this.aFunction = new ActivationFunction();
	}

	public Neuron(ArrayList<Double> weights_list) {
		this.weights = weights_list;
	}

	public Neuron(ArrayList<Double> weights_list, String description) {
		this.weights = weights_list;
		this.description = description;
	}

	public Neuron(ArrayList<Double> weights_list, String description, ActivationFunction aFunction) {
		this.weights = weights_list;
		this.description = description;
		this.aFunction = aFunction;
	}

	/**
	 * learned neuron
	 * 
	 * @param inputs
	 * @return
	 */
	public double activate(ArrayList<Double> inputs) {
		double z = 0;
		for (int i = 0; i < inputs.size(); i++) {
			z += inputs.get(i) * this.weights.get(i);
		}
		this.weightedSum = z;
		this.out = aFunction.activate(z);
		return this.out;
	}

	/**
	 *  ustawienie losowych wag na pocz¹tek procesu uczenia
	 */
	public void setRandomWeights(TreningPattern tp) {
		setRandomWeights(tp.getInputList(), tp.getOutput());
	}

	private void setRandomWeights(ArrayList<Double> inputData, double output) {
		double weightsSum = 0d;
		int count = inputData.size() - 1;
		Random r = new Random();
		for (int i = 0; i < count; i++) {
			this.weights.add(r.nextDouble());
			if (Constants.DEBUG) {
				System.out.println("weight " + i + ": " + this.weights.get(i));
			}
		}
		for (int i = 0; i < count; i++) {
			weightsSum += inputData.get(i) * this.weights.get(i);
		}
		this.weights.add((output - weightsSum) / inputData.get(count));
	}

	/**
	 * wsteczna propagacja b³edu
	 * 
	 * @param tp
	 * @param epsilon
	 * @return
	 */
	public double backwardErrorPropagation(TreningPattern tp, double epsilon) {
		return backwardErrorPropagation(tp.getInputList(), tp.getOutput(), epsilon);
	}

	private double backwardErrorPropagation(ArrayList<Double> inputs, double output, double epsilon) {
		double errorValue = output - activate(inputs);
		ArrayList<Double> newWeights = new ArrayList<>();
		for (int i = 0; i < this.weights.size(); i++) {
			newWeights.add(this.weights.get(i) + (epsilon * errorValue) * inputs.get(i));
		}
		this.weights.clear();
		this.weights = newWeights;
		if (Constants.DEBUG) {
			System.out.println("errorValue " + Math.abs(errorValue));
		}
		return errorValue;
	}

	public void toStringOut() {
		String weights = "[weights: ";
		for (Double w : this.weights) {
			weights += String.format("%.8f", w);
			weights += ",";
		}
		weights += "] ";
		System.out.print(this.description + " " + weights + " out = " + String.format("%.8f", this.out) + " / err = "
				+ String.format("%.8f", this.error));
	}

	public String toStringOutString() {
		String weights = "[weights: ";
		for (Double w : this.weights) {
			weights += String.format("%.8f", w);
			weights += ", ";
		}
		weights += "] ";
		// return "\r\n " + this.description + " " + weights + " out = " +
		// String.format("%.8f",this.out) + " / err = " +
		// String.format("%.8f",this.error);
		return "\r\n " + this.description + " " + weights;
	}

	public String toStringOutNeuronOutputRounded() {
		double t = this.out < Constants.DISPLAY_AS_ZERO ? 0 : this.out > Constants.DISPLAY_AS_ONE ? 1 : this.out;
		return " " + t;
	}

	public String toStringOutNeuronOutput() {
		return " " + String.format("%.8f", this.out);
	}

	/**
	 * getters and setters
	 */
	public ArrayList<Double> getWeights() {
		return weights;
	}

	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}

	public ActivationFunction getaFunction() {
		return aFunction;
	}

	public void setaFunction(ActivationFunction aFunction) {
		this.aFunction = aFunction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error < -999999999 ? -999999999 : (error > 999999999 ? 999999999 : error);
	}

	public double getOut() {
		return out;
	}

	public void setOut(double out) {
		this.out = out;
	}

	public double getWeightedSum() {
		return weightedSum;
	}

	public void setWeightedSum(double weightedSum) {
		this.weightedSum = weightedSum;
	}

}
