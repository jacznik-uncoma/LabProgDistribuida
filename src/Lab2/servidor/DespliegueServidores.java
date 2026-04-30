import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DespliegueServidores {
    public static void main(String[] args) {
        try {
            // Creamos el registro RMI en el puerto 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Instanciamos los servidores finales
            ServidorPrediccion oh = new ServidorPrediccion(new String[]{"Gran suerte", "Cuidado con gastos", "Amor en puerta", "Suerte loco", "Cuidado con la guita", "Suerte en los estudios"});
            ServidorPrediccion op = new ServidorPrediccion(new String[]{"Soleado", "Tormentas eléctricas", "Nublado", "Despejado", "LLuvia", "Viento", "Granizo", "Nieve"});

            // Registramos los servidores OH y OP (opcional si el cliente no los usa directo, pero buena práctica)
            registry.rebind("Horoscopo", oh);
            registry.rebind("Clima", op);

            // Instanciamos y registramos el Objeto Central
            ObjetoCentral oc = new ObjetoCentral(oh, op);
            registry.rebind("Central", oc);

            System.out.println("Sistemas listos. El servidor central y de predicciones están en ejecución...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}