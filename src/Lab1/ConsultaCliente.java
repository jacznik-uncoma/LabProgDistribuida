package Lab1;

import java.io.*;
import java.net.*;

public class ConsultaCliente implements Runnable {
    private final int id;
    private final String signo;
    private final String fecha;

    public ConsultaCliente(int id, String signo, String fecha) {
        this.id = id;
        this.signo = signo;
        this.fecha = fecha;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(Config.HOST, Config.GATEWAY_PORT);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream())) {

            System.out.println("[Cliente " + id + "] Conectado. Enviando: " + signo + " para la fecha " + fecha);

            //Enviamos los datos al Servidor Central
            out.writeUTF(signo);
            out.writeUTF(fecha);
            out.flush();

            //Esperamos la respuesta
            String respuesta = in.readUTF();
            
            System.out.println("[Cliente " + id + "] RESPUESTA RECIBIDA: " + respuesta);

        } catch (UnknownHostException e) {
            System.err.println("[Cliente " + id + "] Error: Host no encontrado (" + Config.HOST + ")");
        } catch (IOException e) {
            System.err.println("[Cliente " + id + "] Error de E/S: " + e.getMessage());
        }
    }
}