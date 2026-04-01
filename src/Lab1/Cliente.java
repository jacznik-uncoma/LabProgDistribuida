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
import java.util.Arrays;

public class Cliente {

    public static void main(String[] args) throws IOException {

        //Ingreso de datos por teclado
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        
        //SIGNO ZODIACAL - pedir hasta que sea válido
        String[] signos = new String[]{"ARIES","TAURO","GEMINIS","CANCER","LEO","VIRGO","LIBRA","ESCORPIO","SAGITARIO","CAPRICORNIO","ACUARIO","PISCIS"};
        String signo;
        while (true) {
            System.out.println("Ingrese su signo zodiacal: ");
            signo = teclado.readLine().trim().toUpperCase();
            if (Arrays.asList(signos).contains(signo)) {
                break;
            } else {
                System.out.println("Signo inválido. Signos válidos: " + String.join(", ", signos));
            }
        }

        //FECHA - pedir hasta que sea válida usando esFechaValida
        String fecha;
        while (true) {
            System.out.println("Ingrese una fecha que desee saber el clima (DD-MM-YYYY): ");
            fecha = teclado.readLine().trim();
            if (esFechaValida(fecha)) {
                break;
            } else {
                System.out.println("Fecha inválida. Formato esperado: DD-MM-YYYY");
            }
        }

        //Me conecto al servidor
        try {
            Socket socket = new Socket(Config.HOST, Config.GATEWAY_PORT);
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
            System.out.println("Host desconocido: " + Config.HOST);
        } 
    }

    public static boolean esFechaValida(String fecha) {
        return fecha.matches("\\d{2}-\\d{2}-\\d{4}");
    }

}
