package Lab1;

import java.net.*;

public class ServidorCentral {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(Config.GATEWAY_PORT)) {
            System.out.println("ServidorCentral escuchando en puerto " + Config.GATEWAY_PORT);

            while (true) {
                //Espero a que un cliente se conecte
                System.out.println("Esperando conexión de cliente...");
                Socket socketClient = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + socketClient.getInetAddress());
                //Manejo cada cliente en un hilo separado
                Thread hilo = new Thread(new ManejadorCliente(socketClient));
                hilo.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
