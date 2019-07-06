import java.io.Serializable;

public class Carta implements Serializable{
    private String cor = null;
    private String valor = null;
    private int valorN;

    public Carta(int valorN, String v, String c){
    	this.valorN = valorN;
        valor = v;
        cor = c; 
    }

	public String getCor() {
		return cor;
	}

	public String getValor() {
		return valor;
	}
	
	public int getValorN() {
		return valorN;
	}
	
	public String toString() {
		return valor +" "+ cor;
	}
}

