import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClienteRMI {
    public static void main(String[] args) {
        try {
            // Nos conectamos al registro RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ICentral central = (ICentral) registry.lookup("Central");

            // Prueba 1: Petición original
            System.out.println("Petición 1: " + central.consultaGeneral("Leo", "2026-05-10"));
            
            // Prueba 2: Debería venir de la Caché
            System.out.println("Petición 2: " + central.consultaGeneral("Leo", "2026-05-10"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}