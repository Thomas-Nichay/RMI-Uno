import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServidor extends Remote{
	
	public void sentar(InterfaceJogador j) throws RemoteException;
	
	public Carta comprar() throws RemoteException;
	
	public void Ganhei() throws RemoteException;
	
	public void pular() throws RemoteException;
	
	public void jogar(Carta c) throws RemoteException;
	
	public boolean validar(Carta c) throws RemoteException;
}
