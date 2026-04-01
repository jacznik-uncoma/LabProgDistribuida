package Lab1;

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
                Thread manejador = new Thread(new ManejadorClima(socket, cacheClima, PREDICCIONES));
                manejador.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lista de predicciones posibles
    private static final String[] PREDICCIONES = {
        "Despejado con una máxima de 25°C. Ideal para actividades al aire libre.",
        "Tormentas aisladas por la tarde. Se recomienda llevar paraguas.",
        "Mayormente nublado con vientos del sector sur.",
        "Ola de calor: mínimas de 22°C y máximas de 38°C.",
        "Cielo parcialmente cubierto con probabilidad de granizo.",
        "Niebla intensa por la mañana, visibilidad reducida a 500 metros."
    };
}
