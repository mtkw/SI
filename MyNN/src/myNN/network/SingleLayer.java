package myNN.network;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Pojedyncza warstwa neuronów
 *
 */
public class SingleLayer implements Serializable {
	
	private ArrayList<Neuron> neurons;
	
	private String description;

	public SingleLayer(ArrayList<Neuron> neurons, String description) {
		this.description = description;
		this.neurons = neurons;		
	}
	
	public void toStringOut(){
		System.out.print(this.description + "[");
		for (Neuron n : this.neurons) {
			n.toStringOut();
		}
		System.out.print("]");
		System.out.println("");
	}
	
	public String toStringOutString(){
		String out = "\r\n\r\n";
		out += this.description + "[";
		for (Neuron n : this.neurons) {
			out += n.toStringOutString();
		}
		out += "\r\n]";
		return out;
	}

	public ArrayList<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(ArrayList<Neuron> singleLayer) {
		this.neurons = singleLayer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
