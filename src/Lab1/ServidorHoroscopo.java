package Lab1;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ServidorHoroscopo {

    public static void main(String[] args) {
        Map<String, String> horoscopos = defaultMensajes();

        try (ServerSocket serverSocket = new ServerSocket(Config.HOROSCOPE_PORT)) {
            System.out.println("ServidorHoroscopo escuchando en puerto " + Config.HOROSCOPE_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[SH] Cliente conectado: " + socket.getInetAddress());
                Thread t = new Thread(() -> handleClient(socket, horoscopos));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket, Map<String, String> horoscopos) {
        try (Socket s = socket;
             DataInputStream in = new DataInputStream(s.getInputStream());
             DataOutputStream out = new DataOutputStream(s.getOutputStream())) {

            String signo = in.readUTF();
            if (signo == null) signo = "";
            signo = signo.trim().toUpperCase();
            System.out.println("[SH] Consulta horóscopo recibido: " + signo);

            String respuesta = horoscopos.getOrDefault(signo, "Horóscopo no disponible para el signo: " + signo);
            out.writeUTF(respuesta);
            out.flush();

        } catch (IOException e) {
            System.err.println("[SH] Error procesando cliente: " + e.getMessage());
        }
    }

    private static Map<String, String> defaultMensajes() {
        Map<String, String> m = new HashMap<>();
        m.put("ARIES", "Hoy es un buen día para tomar la iniciativa.");
        m.put("TAURO", "La paciencia te traerá recompensas.");
        m.put("GEMINIS", "Comunícate con claridad y escucharás propuestas.");
        m.put("CANCER", "Cuida tus emociones y busca equilibrio.");
        m.put("LEO", "Tu energía atrae atención positiva.");
        m.put("VIRGO", "Un detalle bien resuelto marcará la diferencia.");
        m.put("LIBRA", "Busca armonía en tus relaciones hoy.");
        m.put("ESCORPIO", "Tu intuición te guiará hacia una decisión importante.");
        m.put("SAGITARIO", "Aprovecha oportunidades de aprendizaje.");
        m.put("CAPRICORNIO", "Constancia y disciplina abrirán puertas.");
        m.put("ACUARIO", "Una idea original puede florecer.");
        m.put("PISCIS", "La creatividad y la empatía te favorecen.");
        return m;
    }
}
