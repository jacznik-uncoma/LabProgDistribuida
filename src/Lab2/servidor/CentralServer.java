package Lab2.servidor;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import config.Config;
import Lab2.interfaces.ICentral;
import Lab2.interfaces.IPredecible;

public class CentralServer extends UnicastRemoteObject implements ICentral {

    private IPredecible oh;
    private IPredecible op;
    
    private ConcurrentHashMap<String, String> cacheHoroscopo = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> cacheClima = new ConcurrentHashMap<>();

    // Conjunto de signos válidos
    private static final Set<String> SIGNOS_VALIDOS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        "aries", "tauro", "geminis", "géminis", "cancer", "cáncer",
        "leo", "virgo", "libra", "escorpio", "sagitario",
        "capricornio", "acuario", "piscis"
    )));

    public CentralServer(IPredecible oh, IPredecible op) throws RemoteException {
        super();
        this.oh = oh;
        this.op = op;
    }

    @Override
    public String consultaGeneral(String signo, String fecha) throws RemoteException {  
        // Validar Signo
        System.out.println("[DEBUG RMI] Atendiendo a un cliente en el hilo: " + Thread.currentThread().getName());
        if (signo == null || !SIGNOS_VALIDOS.contains(signo.toLowerCase())) {
            throw new IllegalArgumentException("ERROR: El signo zodiacal '" + signo + "' no es válido.");
        }

        // Validar Fecha
        try {
            LocalDate.parse(fecha); 
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("ERROR: La fecha '" + fecha + "' no es válida. Debe existir y tener el formato YYYY-MM-DD.");
        }
        
        String resHoroscopo;
        String resClima;
        boolean horoscopoDesdeCache = false;
        boolean climaDesdeCache = false;

        String signoNormalizado = signo.toLowerCase(); 

        // Sincronizamos el acceso a la caché del horóscopo para evitar condiciones de carrera
        synchronized (cacheHoroscopo) {
            if (cacheHoroscopo.containsKey(signoNormalizado)) {
                resHoroscopo = cacheHoroscopo.get(signoNormalizado);
                horoscopoDesdeCache = true;
            } else {
                resHoroscopo = oh.obtenerPrediccion(signo);
                cacheHoroscopo.put(signoNormalizado, resHoroscopo);
            }
        }

        // Sincronizamos el acceso a la caché del clima
        synchronized (cacheClima) {
            if (cacheClima.containsKey(fecha)) {
                resClima = cacheClima.get(fecha);
                climaDesdeCache = true;
            } else {
                resClima = op.obtenerPrediccion(fecha);
                cacheClima.put(fecha, resClima);
            }
        }
        
        String etiquetaH = horoscopoDesdeCache ? "[CACHÉ] " : "";
        String etiquetaC = climaDesdeCache ? "[CACHÉ] " : "";

        return "Horóscopo (" + signo + "): " + etiquetaH + resHoroscopo + " | Clima (" + fecha + "): " + etiquetaC + resClima;
    }

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", Config.CENTRAL_HOST);
        
        try {
            Registry registry = LocateRegistry.createRegistry(Config.GATEWAY_PORT);
            System.out.println("Registry creado en puerto " + Config.GATEWAY_PORT);

            IPredecible oh = null;
            IPredecible op = null;

            while (oh == null || op == null) {
                try {
                    if (oh == null) {
                        Registry horoscopoRegistry = LocateRegistry.getRegistry(Config.HOROSCOPE_HOST, Config.HOROSCOPE_PORT);
                        oh = (IPredecible) horoscopoRegistry.lookup("Horoscopo");
                    }
                    if (op == null) {
                        Registry climaRegistry = LocateRegistry.getRegistry(Config.CLIMATE_HOST, Config.CLIMATE_PORT);
                        op = (IPredecible) climaRegistry.lookup("Clima");
                    }
                } catch (Exception e) {
                    System.out.println("Esperando que los servidores 'Horoscopo' (puerto " + Config.HOROSCOPE_PORT + ") y 'Clima' (puerto " + Config.CLIMATE_PORT + ") se registren...");
                    try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                }
            }

            CentralServer cs = new CentralServer(oh, op);
            registry.rebind("Central", cs);

            System.out.println("Servidor Central (CentralServer) registrado como 'Central' y listo.");
        } catch (Exception e) {
            System.err.println("Error arrancando CentralServer:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
