package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("verificacion")
public class VerificacionCrediticiaService {

    private static final String BLACKLIST_SERVICE_URL = "http://localhost:8080/webresources/generic/buscar/";
    private static final String DATACREDITO_SERVICE_URL = "http://localhost:8080/webresources/datacredito/consulta/";

    @GET
    @Path("/verificar/{idCliente}")
    public String verificarCliente(@PathParam("idCliente") String idCliente) {
        try {
            // Paso 1: Consultar BlackList
            if (consultarBlackList(idCliente)) {
                return "Cliente está en la BlackList - Solicitud rechazada";
            }

            // Paso 2: Consultar DataCredito
            if (!consultarDataCredito(idCliente)) {
                return "Cliente no aprobado por DataCredito - Solicitud rechazada";
            }

            return "Cliente aprobado - Verificación exitosa";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error en la verificación del cliente: " + e.getMessage();
        }
    }

    // Método para consumir el servicio BlackList
    private boolean consultarBlackList(String idCliente) {
        try {
            URL url = new URL(BLACKLIST_SERVICE_URL + idCliente);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Tiempo de espera para la conexión (5 segundos)
            conn.setReadTimeout(5000); // Tiempo de espera para la lectura (5 segundos)

            if (conn.getResponseCode() == 200) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = in.readLine();
                    return response.equals("1"); // Está en la BlackList
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Asume que no está en BlackList si hay error
    }

    // Método para consumir el servicio DataCredito
    private boolean consultarDataCredito(String idCliente) {
        try {
            URL url = new URL(DATACREDITO_SERVICE_URL + idCliente);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Tiempo de espera para la conexión (5 segundos)
            conn.setReadTimeout(5000); // Tiempo de espera para la lectura (5 segundos)

            if (conn.getResponseCode() == 200) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = in.readLine();
                    return response.equals("aprobado"); // DataCredito aprueba
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Asume rechazo si hay error
    }
}
