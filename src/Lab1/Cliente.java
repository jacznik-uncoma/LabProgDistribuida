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

public class Cliente {

    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 5000;

        try (Socket socket = new Socket(host, puerto)) {
            System.out.println("Conectado al servidor");

            //leo lo que viene del Stream del socket
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            //Leo lo que viene del teclado
            BufferedReader teclado = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            //Escribo en la salida del socket, el mensaje a enviar al servidor
            PrintWriter salida = new PrintWriter(
                    socket.getOutputStream(), //"obtiene el flujo de bytes para enviar datos por la conexión"
                    true //auto-flush: cada println se envía inmediatamente sin quedar en el buffer
            );

//Pido mensaje por teclado constantemente                        
            boolean salir = false;

            while (!salir) { //escribiendo "salir" termina la conexión
                System.out.print("Ingrese fecha con el formato DD-MM-YYYY. Ingrese \"Salir\" para terminar la conexión: ");
                String mensaje = teclado.readLine();
                
                if (mensaje.equals("Salir")) {
                    salir = true;
                    salida.println(mensaje);
                }else{
                    if(esFechaValida(mensaje)){
                        salida.println(mensaje);
                    }else{
                        System.out.println("Ingrese una entrada válida.");
                    }
                }
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean esFechaValida(String fecha) {
        return fecha.matches("\\d{2}-\\d{2}-\\d{4}");
    }

}
