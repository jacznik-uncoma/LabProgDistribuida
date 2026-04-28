package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPredecible extends Remote {
    String obtenerPrediccion(String parametro) throws RemoteException;
}