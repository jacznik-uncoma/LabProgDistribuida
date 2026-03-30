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

    public static void main(String[] args) {
        int puerto = 5000;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor escuchando en puerto " + puerto);

            Socket socket = serverSocket.accept(); // espera cliente
            System.out.println("Cliente conectado");

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter salida = new PrintWriter(
                    socket.getOutputStream(), true
            );

            String mensaje = entrada.readLine();
            System.out.println("Cliente dice: " + mensaje);
            while (!"Salir".equals(mensaje)) {
                mensaje = entrada.readLine();
                System.out.println("Cliente dice: " + mensaje);
            }

            salida.println("Hola cliente, recibí: " + mensaje);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
