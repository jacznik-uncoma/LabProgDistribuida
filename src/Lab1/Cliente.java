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

    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;
    public static void main(String[] args) throws IOException {

        //Ingreso de datos por teclado
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        
        //SIGNO ZODIACAL
        System.out.println("Ingrese su signo zodiacal: ");
        String signo = teclado.readLine().trim().toUpperCase();

        //FECHA
        System.out.println("Ingrese una fecha que desee saber el clima (DD/MM/YYYY): ");
        String fecha = teclado.readLine().trim();

        //Me conecto al servidor
        try {
            Socket socket = new Socket(HOST, PUERTO);
            System.out.println("Conectado al servidor");

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            //Envio los datos al servidor
            out.writeUTF(signo);
            out.writeUTF(fecha);
            System.out.println("Datos enviados al servidor");

            //Recibo la respuesta del servidor
            String respuesta = in.readUTF();
            System.out.println("Respuesta del servidor: " + respuesta);

            in.close();
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.out.println("Host desconocido: " + HOST);
        } 
    }

    public static boolean esFechaValida(String fecha) {
        return fecha.matches("\\d{2}-\\d{2}-\\d{4}");
    }

}
