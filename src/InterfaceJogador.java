import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceJogador extends Remote{
	
	public void suaVez(Carta atual) throws RemoteException;
	
	public void alguemGanhou(String ganhador) throws RemoteException;
	
	public void setCartaTopo(Carta atual) throws RemoteException;
	
	public String getNomeJogador() throws RemoteException;
	
}
