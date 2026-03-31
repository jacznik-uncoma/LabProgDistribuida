/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab1;

/**
 *
 * @author n1ko7
 */
import java.io.*;
import java.net.*;

public class Servidor {

    public static final int PUERTO = 5000;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor escuchando en puerto " + PUERTO);

            while (true) {
                //Espero a que un cliente se conecte
                System.out.println("Esperando conexión de cliente...");
                Socket socketClient = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + socketClient.getInetAddress());

                Thread hilo = new Thread(new ManejadorCliente(socketClient));
                hilo.start();
            }
            /*
            //Espero a que un cliente se conecte

            DataInputStream in = new DataInputStream(socketClient.getInputStream());
            DataOutputStream out = new DataOutputStream(socketClient.getOutputStream());

            //Recibo los datos del cliente
            System.out.println("Recibiendo datos del cliente...");
            String signo = in.readUTF(); 
            String fecha = in.readUTF();

            //Imprimo los datos recibidos y envío una respuesta al cliente
            System.out.println("Datos recibidos: " + signo + " y " + fecha);
            out.writeUTF("SC recibió: " + signo + " y " + fecha);

            serverSocket.close();
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
