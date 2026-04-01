package Lab1;

public class Cliente {
    public static void main(String[] args) {
        String[] poolDeSignos = {"TAURO", "LEO", "SAGITARIO"};
        int cantHilos = 5; // Número de clientes concurrentes a lanzar

        for (int i = 1; i <= cantHilos; i++) {
            // Elegimos un signo al azar del array
            String signoAzar = poolDeSignos[(int) (Math.random() * poolDeSignos.length)];
            String fechaPrueba = "09-08-2026"; // Fecha fija

            // Lanzamos un hilo para cada consulta
            Thread consulta = new Thread(new ConsultaCliente(i, signoAzar, fechaPrueba));
            consulta.start();
        }
    }
}