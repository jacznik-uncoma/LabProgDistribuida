package Lab1;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

public class ManejadorClima implements Runnable {
    private final Socket socket;
    private final Map<String, String> cache;
    private final String[] predicciones;

    public ManejadorClima(Socket socket, Map<String, String> cache, String[] predicciones) {
        this.socket = socket;
        this.cache = cache;
        this.predicciones = predicciones;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            String clima = in.readUTF();
            if (clima == null) clima = "DESCONOCIDO";
            clima = clima.trim().toUpperCase();
            
            System.out.println("[SH] Procesando consulta para: " + clima);

            // Verificar cache y generar respuesta
            String respuesta = cache.computeIfAbsent(clima, c -> 
                predicciones[new Random().nextInt(predicciones.length)]
            );

            out.writeUTF(respuesta);
            out.flush();

        } catch (IOException e) {
            System.err.println("[SH] Error con un cliente: " + e.getMessage());
        } finally {
            try {
                if (!socket.isClosed()) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}