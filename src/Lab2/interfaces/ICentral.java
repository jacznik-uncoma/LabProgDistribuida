import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICentral extends Remote {
    String consultaGeneral(String signo, String fecha) throws RemoteException;
}