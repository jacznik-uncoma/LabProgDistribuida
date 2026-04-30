package Lab1;
import config.Config;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServidorClima {
    private static final Map<String, String> cacheClima = new ConcurrentHashMap<>();
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(Config.CLIMATE_PORT)) {
            System.out.println("ServidorClima escuchando en puerto " + Config.CLIMATE_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[SH] Cliente conectado: " + socket.getInetAddress());
                // Manejar cada cliente en un hilo separado
                Thread manejador = new Thread(new ManejadorClima(socket, cacheClima, PRONOSTICOS));
                manejador.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lista de predicciones posibles
    private static final String[] PRONOSTICOS = {
        "Despejado con una máxima de 25°C. Ideal para actividades al aire libre.",
        "Tormentas aisladas por la tarde. Se recomienda llevar paraguas.",
        "Mayormente nublado con vientos del sector sur.",
        "Ola de calor: mínimas de 22°C y máximas de 38°C.",
        "Cielo parcialmente cubierto con probabilidad de granizo.",
        "Niebla intensa por la mañana, visibilidad reducida a 500 metros.",
        "Clima ideal: 22°C, humedad baja y brisa suave del este.",
        "Vientos fuertes del oeste; ráfagas de hasta 70 km/h.",
        "Lloviznas intermitentes durante todo el día, frío moderado.",
        "Nevadas leves en zonas altas, heladas durante la madrugada.",
        "Humedad extrema del 98%: visibilidad reducida y calzada resbaladiza.",
        "Cielo despejado, noche estrellada con mínima de 12°C.",
        "Chaparrones intensos de corta duración; acumulados de 20mm.",
        "Viento Zonda: ráfagas secas y calientes con aumento de temperatura.",
        "Frente frío aproximándose: descenso brusco de temperatura hacia la noche."
    };
}
