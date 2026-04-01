package Lab1;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServidorHoroscopo {
    private static final Map<String, String> cacheHoroscopo = new ConcurrentHashMap<>();
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(Config.HOROSCOPE_PORT)) {
            System.out.println("ServidorHoroscopo escuchando en puerto " + Config.HOROSCOPE_PORT);
            while (true) {
                // Espero a que un cliente se conecte
                Socket socket = serverSocket.accept();
                System.out.println("[SH] Cliente conectado: " + socket.getInetAddress());
                // Manejar cada cliente en un hilo separado
                Thread manejador = new Thread(new ManejadorHoroscopo(socket, cacheHoroscopo, PREDICCIONES));
                manejador.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lista de predicciones posibles
    private static final String[] PREDICCIONES = {
        "Hoy es un gran día para programar en Java.",
        "Cuidado con los NullPointerException en tu camino.",
        "Un Socket se cerrará inesperadamente, mantén la calma.",
        "Grandes éxitos en tus sistemas distribuidos se aproximan."
    };
}
