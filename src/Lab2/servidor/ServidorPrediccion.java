import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Random;

public class ServidorPrediccion extends UnicastRemoteObject implements IPredecible {
    private String[] predicciones;

    public ServidorPrediccion(String[] opciones) throws RemoteException {
        super();
        this.predicciones = opciones;
    }

    @Override
    public String obtenerPrediccion(String parametro) throws RemoteException {
        // Simulación de respuesta aleatoria
        return predicciones[new Random().nextInt(predicciones.length)];
    }
}