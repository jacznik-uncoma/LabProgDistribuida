package Lab2.servidor;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import config.Config;

public class HoroscopoServer {
    public static String[] PREDICCIONES = new String[]{
        "Gran suerte", "Cuidado con gastos", "Amor en puerta", "Suerte loco", "Cuidado con la guita", "Suerte en los estudios"
    };

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", Config.HOROSCOPE_HOST);
        
        try {
            Registry registry = LocateRegistry.createRegistry(Config.HOROSCOPE_PORT);
            
            ServidorPrediccion oh = new ServidorPrediccion(PREDICCIONES);

            registry.rebind("Horoscopo", oh);

            System.out.println("Servidor Horoscopo registrado en " + Config.HOROSCOPE_HOST + ":" + Config.HOROSCOPE_PORT + " como 'Horoscopo'");
        } catch (Exception e) {
            System.err.println("Error arrancando HoroscopoServer:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
