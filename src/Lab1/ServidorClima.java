package Lab1;

import java.io.*;
import java.net.*;

public class ServidorClima {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Config.CLIMATE_PORT)) {
            System.out.println("ServidorClima escuchando en puerto " + Config.CLIMATE_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[SP] Cliente conectado: " + socket.getInetAddress());
                Thread t = new Thread(() -> handleClient(socket));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (Socket s = socket;
             DataInputStream in = new DataInputStream(s.getInputStream());
             DataOutputStream out = new DataOutputStream(s.getOutputStream())) {

            String fecha = in.readUTF();
            if (fecha == null) fecha = "";
            fecha = fecha.trim();
            System.out.println("[SP] Consulta clima recibida: " + fecha);

            // Respuesta simple. En un servicio real haríamos parsing y consulta a una API.
            String respuesta = "Clima para " + fecha + ": Soleado, 25°C";

            out.writeUTF(respuesta);
            out.flush();

        } catch (IOException e) {
            System.err.println("[SP] Error procesando cliente: " + e.getMessage());
        }
    }
}
