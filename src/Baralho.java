import java.util.Random;

public class Baralho {

	public Carta comprar(){		
		Random rand = new Random();
        int valor = rand.nextInt(26);
        if(valor >= 13)
        	valor -= 12;
        
        if(valor<=12){
	        switch(rand.nextInt(4)){
	        case 0: return new Carta(valor,getNome(valor),"vermelho");
			case 1: return new Carta(valor,getNome(valor),"amarelo");
			case 2: return new Carta(valor,getNome(valor),"verde");
			case 3: return new Carta(valor,getNome(valor),"azul");
	        }
        }
        return new Carta(valor,getNome(valor),null);
	}
	
	private String getNome(int x) {
		if(x<=9)
			return Integer.toString(x);
		switch(x){
            case 10: return "Pular";
            case 11: return "Inverte";
            case 12: return "+2";
            case 13: return "Coringa";
            case 14: return "Coringa+4";
        }
		return "erro";
	}
	
}
