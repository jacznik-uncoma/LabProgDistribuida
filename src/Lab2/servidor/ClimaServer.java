package Lab2.servidor;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import config.Config;

public class ClimaServer {
    public static String[] PREDICCIONES = new String[]{
        "Soleado", "Tormentas eléctricas", "Nublado", "Despejado", "LLuvia", "Viento", "Granizo", "Nieve"
    };

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", Config.CLIMATE_HOST);
        
        try {
            Registry registry = LocateRegistry.createRegistry(Config.CLIMATE_PORT);
            
            ServidorPrediccion op = new ServidorPrediccion(PREDICCIONES);

            registry.rebind("Clima", op);

            System.out.println("Servidor Clima registrado en " + Config.CLIMATE_HOST + ":" + Config.CLIMATE_PORT + " como 'Clima'");
        } catch (Exception e) {
            System.err.println("Error arrancando ClimaServer:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
