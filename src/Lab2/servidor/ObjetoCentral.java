package rmi.servidor;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import rmi.interfaces.ICentral;
import rmi.interfaces.IPredecible;

public class ObjetoCentral extends UnicastRemoteObject implements ICentral {
    private IPredecible oh;
    private IPredecible op;
    
    private ConcurrentHashMap<String, String> cacheHoroscopo = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> cacheClima = new ConcurrentHashMap<>();

    // Conjunto de signos válidos
    private static final Set<String> SIGNOS_VALIDOS = Set.of(
        "aries", "tauro", "geminis", "géminis", "cancer", "cáncer", 
        "leo", "virgo", "libra", "escorpio", "sagitario", 
        "capricornio", "acuario", "piscis"
    );

    public ObjetoCentral(IPredecible oh, IPredecible op) throws RemoteException {
        super();
        this.oh = oh;
        this.op = op;
    }

    @Override
    public String consultaGeneral(String signo, String fecha) throws RemoteException {  
        // Validar Signo
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
}