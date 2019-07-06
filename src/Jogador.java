import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class Jogador extends UnicastRemoteObject implements InterfaceJogador{
	
	private Carta cartaTopo;
    private ArrayList<Carta> playerdeck;
    private InterfaceServidor mesa;
    private String nomeJogador;
    private Scanner scan;
    
    
    public static void main(String[] args) {
	    try {
			Registry registry = LocateRegistry.getRegistry("localhost",65432); 
			InterfaceServidor mesa = (InterfaceServidor) registry.lookup("UnoServer");
			InterfaceJogador j = new Jogador(mesa);
			mesa.sentar(j);
		} catch (Exception e) {
			System.out.println("exeção na criação do cliente");
			e.printStackTrace();
		}
    }
    

	public Jogador(InterfaceServidor mesa) throws RemoteException{
		playerdeck = new ArrayList<Carta>();
		System.out.println("informe seu nome:");
	    scan = new Scanner(System.in);
		nomeJogador = scan.nextLine();
		this.mesa = mesa;
	}

	@Override
	public void suaVez(Carta atual) throws RemoteException {
		int indexEscolha = 0;
		while(indexEscolha == 0 ) {
            System.out.println(atual.toString() + " foi jogada");
		    System.out.println("Sua vez:");
		    System.out.println("0. Comprar uma carta");
		    for (int i = 0; i <= playerdeck.size()-1; i++){
		    	Carta temp = playerdeck.get(i);
		        System.out.println(Integer.toString(i+1) + ". " + temp.toString());
		    }
		    System.out.print("Escolha a carta (numero do indice):");
		    indexEscolha = scan.nextInt();

		    if (indexEscolha == 0 ) {
		        playerdeck.add(mesa.comprar());
		    }
		}
		if(indexEscolha <= playerdeck.size() ){
	    	Carta selecao = playerdeck.get(indexEscolha-1);
	    	if (playerdeck.size() == 1) {
            	mesa.Ganhei();
            	return;
            }
	    	
		    if (mesa.validar(selecao)) {
		    	mesa.jogar(selecao);
		    	playerdeck.remove(selecao);
		    } else {
		    	System.out.println("carta invalida passando a vez ... ");
		    	mesa.pular();
		    }
	    }else {
	    	System.out.println("numero invalido passando a vez ... ");
	    	mesa.pular();
	    }
	}

	@Override
	public void alguemGanhou(String ganhador) throws RemoteException {
		System.out.println("\nFIM DE JOGO\n\n o jogador " + ganhador + " Ganhou");
		
	}

	@Override
	public void setCartaTopo(Carta atual) throws RemoteException {
		this.cartaTopo = atual;
		System.out.println("carta do topo atualizada para : " + cartaTopo.toString());
	}


	@Override
	public String getNomeJogador() throws RemoteException{
		return nomeJogador;
	}
}