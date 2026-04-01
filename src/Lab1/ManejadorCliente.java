package Lab1;

import java.io.*;
import java.net.*;

public class ManejadorCliente implements Runnable {
    private Socket socketCliente;

    public ManejadorCliente(Socket socket) {
        this.socketCliente = socket;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socketCliente.getInputStream());
                DataOutputStream out = new DataOutputStream(socketCliente.getOutputStream())) {
            // Leer datos del cliente
            String signo = in.readUTF();
            String fecha = in.readUTF();
            System.out.println("Consulta recibida: " + signo + " | " + fecha);

            // Consultar a los servidores correspondientes (SH y SP)
            String prediccionH = consultarHoroscopo(signo);
            String pronosticoC = consultarClima(fecha);

            // Enviar respuesta al cliente
            out.writeUTF("Horóscopo: " + prediccionH + " | Clima: " + pronosticoC);
            out.flush();

        } catch (IOException e) {
            System.err.println("Error procesando cliente: " + e.getMessage());
        } finally {
            try {
                if (socketCliente != null)
                    socketCliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Conexión al Servidor de Horóscopo (SH) y al Servidor del Clima (SP)
    private String consultarHoroscopo(String signo) {
        try (
                Socket socketSH = new Socket(Config.HOST, Config.HOROSCOPE_PORT);
                DataOutputStream outSH = new DataOutputStream(socketSH.getOutputStream());
                DataInputStream inSH = new DataInputStream(socketSH.getInputStream());) {

            outSH.writeUTF(signo);
            return inSH.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error consultando horóscopo";
        }
    }

    private String consultarClima(String fecha) {
        try (
                Socket socketSP = new Socket(Config.HOST, Config.CLIMATE_PORT);
                DataOutputStream outSP = new DataOutputStream(socketSP.getOutputStream());
                DataInputStream inSP = new DataInputStream(socketSP.getInputStream());) {

            outSP.writeUTF(fecha);
            return inSP.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error consultando clima";
        }
    }
}