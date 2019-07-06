import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Mesa implements InterfaceServidor{

	private boolean fimJogo;
	private Baralho baralho;
	private Carta ultima;
    private Boolean sentidoH;
    private List<InterfaceJogador> jogadores;
    private InterfaceJogador ativo;
    private ListIterator<InterfaceJogador> iter;
    private int numJogadores;
    
    public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(65432);
			InterfaceServidor obj = (InterfaceServidor) UnicastRemoteObject.exportObject(new Mesa(2), 0);
			
			Registry reg = LocateRegistry.getRegistry(65432);
	        reg.rebind("UnoServer", obj);
		} catch (Exception e) {
			System.out.println("exeção na criação do servidor");
			e.printStackTrace();
		}
	}
    
    public Mesa(int numJogadores) {
    	fimJogo = false;
    	baralho = new Baralho();
    	ultima = baralho.comprar();
    	sentidoH  = true;
    	jogadores = new ArrayList<InterfaceJogador>(numJogadores);
    	this.numJogadores = numJogadores;
    }
    
	@Override
	public void sentar(InterfaceJogador j) throws RemoteException {
		if(jogadores.size()<numJogadores) {
			jogadores.add(j);
			System.out.println("jogador "+ j.getNomeJogador() + " entrou");
			if(jogadores.size()==numJogadores)
				this.start();
		}else throw new IllegalStateException("A mesa não tem mais lugares");
	}
    
    public void start(){
    	iter = jogadores.listIterator(0);
        ativo = iter.next();
        try {
	    	for(InterfaceJogador j:jogadores) {
	        	j.setCartaTopo(ultima);
	        }
	    	while(!fimJogo)
	    		ativo.suaVez(ultima);
        }catch(RemoteException r) {
        	r.printStackTrace();
        }
    }
    
    private InterfaceJogador proxJogador() {
    	if(sentidoH) {
        	if(!iter.hasNext())
        		while(iter.hasPrevious())
        			iter.previous();       	
        	return iter.next();
        }else {
        	if(!iter.hasPrevious())
        		while(iter.hasNext())
        			iter.next();
        	return iter.previous();
        }
    }

	@Override
	public Carta comprar() throws RemoteException {
		return baralho.comprar();
	}

	@Override
	public void Ganhei() throws RemoteException {
		fimJogo = true;
		for(InterfaceJogador j : jogadores) {
        	j.alguemGanhou(ativo.getNomeJogador());
        }
	}

	@Override
	public void pular() throws RemoteException {
		ativo = proxJogador();
    	for(InterfaceJogador j:jogadores)
        	j.setCartaTopo(ultima);
	}

	@Override
	public void jogar(Carta c) throws RemoteException {
		ultima = c;
        
        if (ultima.getValorN() == 11) {
        	sentidoH = Boolean.logicalXor(sentidoH, true);
        	proxJogador();
        }
        if (ultima.getValorN() == 10)
        	proxJogador();
        ativo = proxJogador();
        for(InterfaceJogador j:jogadores) {
        	j.setCartaTopo(ultima);
        }
	}

	@Override
	public boolean validar(Carta c) throws RemoteException {
		if (ultima.getCor() == null) // coringas
            return true;
    	if (c.getCor() == null) // coringas
            return true;
    	if (c.getCor().equals(ultima.getCor()))
            return true;
        if (c.getValor().equals(ultima.getValor()))
            return true;
        return false;
	}
}
