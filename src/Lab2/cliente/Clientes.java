package Lab2.cliente;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import config.Config;
import Lab2.interfaces.ICentral;

public class Clientes {
    public static void main(String[] args) {
        int cantidadClientes = 5;

        System.out.println("Iniciando simulación con " + cantidadClientes + " clientes concurrentes...\n");

        for (int i = 1; i <= cantidadClientes; i++) {
            final int idCliente = i;
            
            Thread hiloCliente = new Thread(() -> {
                try {
                    Registry registry = LocateRegistry.getRegistry(Config.HOST, Config.GATEWAY_PORT);
                    ICentral central = (ICentral) registry.lookup("Central");

                    String signo = (idCliente % 2 == 0) ? "Leo" : "Aries"; 
                    String fecha = (idCliente % 2 == 0) ? "2026-05-10": "2026-05-13";

                    System.out.println("-> Cliente " + idCliente + " iniciando consulta por: " + signo + " y " + fecha);

                    String respuesta = central.consultaGeneral(signo, fecha);

                    System.out.println("<- Cliente " + idCliente + " recibió: " + respuesta);

                } catch (Exception e) {
                    System.err.println("Error en Cliente " + idCliente + ": " + e.getMessage());
                }
            });

            hiloCliente.start();
        }
    }
}