package Lab1;

public class Cliente {
    public static void main(String[] args) {
        //Completar con el resto de signos en caso de querer probar más consultas, se usan 3 para que haya repetidos
        String[] poolDeSignos = {"TAURO", "LEO", "SAGITARIO"};
        //Solo 3 fechas para que haya repetidos
        String[] poolDeFechas = {"01-01-2026", "15-05-2026", "20-12-2025"};
        int cantHilos = 10; // Cantidad de Threads

        for (int i = 1; i <= cantHilos; i++) {
            // Elegimos un signo y fecha al azar del array
            String signoAzar = poolDeSignos[(int) (Math.random() * poolDeSignos.length)];
            String fechaPrueba = poolDeFechas[(int) (Math.random() * poolDeFechas.length)];
            //String fechaPrueba = generarFechaAleatoria();
            // Lanzamos un hilo para cada consulta
            Thread consulta = new Thread(new ConsultaCliente(i, signoAzar, fechaPrueba));
            consulta.start();
        }
    }
    /*
        EN CASO DE QUERER GENERAR FECHAS ALEATORIAS,
        SE PUEDE USAR ESTE MÉTODO AUXILIAR PARA OBTENER UNA FECHA
        EN FORMATO DD-MM-AAAA ENTRE LOS AÑOS 2024 Y 2026.
        DESCOMENTAR LA LINEA 15 Y COMENTAR LA LINEA 14 PARA USARLO.
    */
   
    /*
    private static String generarFechaAleatoria() {
        int dia = (int) (Math.random() * 28) + 1; // Dias menores a 29
        int mes = (int) (Math.random() * 12) + 1; // Mes
        int anio = 2024 + (int) (Math.random() * 3); // Año entre 2024 y 2026

        // %02d asegura que si el número es 5, escriba "05"
        return String.format("%02d-%02d-%04d", dia, mes, anio);
    }
    */
}