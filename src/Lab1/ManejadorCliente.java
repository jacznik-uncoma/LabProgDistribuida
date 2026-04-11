package Lab1;

import java.io.*;
import java.net.*;

public class ManejadorCliente implements Runnable {
    private Socket socketCliente;
    private DataInputStream inCliente;
    private DataOutputStream outCliente;

    private Socket socketSH;
    private DataOutputStream outSH;
    private DataInputStream inSH;

    private Socket socketSP;
    private DataOutputStream outSP;
    private DataInputStream inSP;

    public ManejadorCliente(Socket socket) {
        this.socketCliente = socket;
    }

    @Override
    public void run() {
        try {
            inCliente = new DataInputStream(socketCliente.getInputStream());
            outCliente = new DataOutputStream(socketCliente.getOutputStream());
            // Leer datos del cliente
            String signo = inCliente.readUTF();
            String fecha = inCliente.readUTF();
            System.out.println("Consulta recibida: " + signo + " | " + fecha);

            // Consultar a los servidores correspondientes (SH y SP)
            String prediccionH = consultarHoroscopo(signo);
            String pronosticoC = consultarClima(fecha);

            // Enviar respuesta al cliente
            outCliente.writeUTF("Horóscopo: " + prediccionH + " | Clima: " + pronosticoC);
            outCliente.flush();

        } catch (IOException e) {
            System.err.println("Error procesando cliente: " + e.getMessage());
        } finally {
            try {
                if (inCliente != null)
                    inCliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outCliente != null)
                    outCliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        try {
            socketSH = new Socket(Config.HOST, Config.HOROSCOPE_PORT);
            outSH = new DataOutputStream(socketSH.getOutputStream());
            inSH = new DataInputStream(socketSH.getInputStream());

            outSH.writeUTF(signo);
            return inSH.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error consultando horóscopo";
        } finally {
            try {
                if (inSH != null)
                    inSH.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outSH != null)
                    outSH.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socketSH != null && !socketSH.isClosed())
                    socketSH.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String consultarClima(String fecha) {
        try {
            socketSP = new Socket(Config.HOST, Config.CLIMATE_PORT);
            outSP = new DataOutputStream(socketSP.getOutputStream());
            inSP = new DataInputStream(socketSP.getInputStream());

            outSP.writeUTF(fecha);
            return inSP.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error consultando clima";
        } finally {
            try {
                if (inSP != null)
                    inSP.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outSP != null)
                    outSP.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socketSP != null && !socketSP.isClosed())
                    socketSP.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}