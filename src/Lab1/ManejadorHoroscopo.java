package Lab1;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

public class ManejadorHoroscopo implements Runnable {
    private final Socket socket;
    private final Map<String, String> cache;
    private final String[] predicciones;
    private DataInputStream in;
    private DataOutputStream out;

    public ManejadorHoroscopo(Socket socket, Map<String, String> cache, String[] predicciones) {
        this.socket = socket;
        this.cache = cache;
        this.predicciones = predicciones;
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String signo = in.readUTF();
            if (signo == null) signo = "DESCONOCIDO";
            signo = signo.trim().toUpperCase();
            
            System.out.println("[SH] Procesando consulta para: " + signo);

            // Verificar cache y generar respuesta
            String respuesta = cache.computeIfAbsent(signo, s -> 
                predicciones[new Random().nextInt(predicciones.length)]
            );

            out.writeUTF(respuesta);
            out.flush();

        } catch (IOException e) {
            System.err.println("[SH] Error con un cliente: " + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (!socket.isClosed()) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}