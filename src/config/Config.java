package config;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase de configuración centralizada.
 * Requiere que exista un archivo `config.properties` en el directorio de ejecución.
 * Si falta el archivo o alguna propiedad, lanza RuntimeException.
 */
public final class Config {
    private Config() {}

    public static final String HOST;
    public static final int GATEWAY_PORT;
    public static final int HOROSCOPE_PORT;
    public static final int CLIMATE_PORT;

    static {
        Properties p = new Properties();

        // Cargar sólo desde el working directory (config.properties)
        try (InputStream in = new FileInputStream("config.properties")) {
            p.load(in);
        } catch (IOException e) {
            throw new RuntimeException("No se encontró 'config.properties' en el directorio de ejecución.", e);
        }

        HOST = require(p, "host");
        GATEWAY_PORT = parseInt(require(p, "gateway.port"), "gateway.port");
        HOROSCOPE_PORT = parseInt(require(p, "horoscope.port"), "horoscope.port");
        CLIMATE_PORT = parseInt(require(p, "climate.port"), "climate.port");
    }

    private static String require(Properties p, String key) {
        String v = p.getProperty(key);
        if (v == null || v.trim().isEmpty()) throw new RuntimeException("Propiedad requerida '" + key + "' ausente en config.properties");
        return v.trim();
    }

    private static int parseInt(String s, String key) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Valor inválido para propiedad '" + key + "': debe ser entero", e);
        }
    }
}