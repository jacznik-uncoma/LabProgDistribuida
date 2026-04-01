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

    // Lista de predicciones posibles (Gracias a ChatGPT por las predicciones)
    private static final String[] PREDICCIONES = {
        "Hoy es un gran día para programar en Java.",
        "Cuidado con los NullPointerException en tu camino.",
        "Un Socket se cerrará inesperadamente, mantén la calma.",
        "Grandes éxitos en tus sistemas distribuidos se aproximan.",
        "La alineación de los bits indica que tu compilación será exitosa.",
        "Un String vacío llegará a tu vida; aprendé a manejar las excepciones.",
        "Tu persistencia dará frutos: un bug difícil será resuelto antes del mediodía.",
        "Alguien te enviará un Pull Request inesperado; revisalo con amor.",
        "Evitá los bucles infinitos en tus relaciones personales.",
        "Tu CPU estará fresca y tus procesos serán ligeros hoy.",
        "El garbage collector se llevará lo que ya no necesitás; dejalo ir.",
        "Un puerto se abrirá para vos: aprovechá la conexión.",
        "La latencia en tus proyectos disminuirá notablemente esta semana.",
        "Tu stack overflow será consultado por muchos; compartí tu sabiduría.",
        "Cuidado con los cambios en producción el viernes por la tarde.",
        "Un nuevo framework aparecerá en tu horizonte, pero mantené la calma.",
        "Tu memoria RAM estará optimizada; aprovechá para aprender algo nuevo.",
        "Los astros dicen que el servidor no se caerá durante tu defensa."
};
}
