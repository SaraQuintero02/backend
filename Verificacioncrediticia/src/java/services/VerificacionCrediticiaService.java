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

    @GET
    @Path("/verificar/{idCliente}")
    public String verificarCliente(@PathParam("idCliente") String idCliente) {
        // Paso 1: Consultar BlackList
        if (consultarBlackList(idCliente)) {
            return "Cliente está en la BlackList - Solicitud rechazada";
        }

        if (!consultarDataCredito(idCliente)) {
            return "Cliente no aprobado por DataCredito - Solicitud rechazada";
        }

        return "Cliente aprobado - Verificación exitosa";
    }

    // Método para consumir el servicio BlackList
    private boolean consultarBlackList(String idCliente) {
        try {
            // URL del servicio BlackList (ajusta si es necesario)
            URL url = new URL("http://localhost:8080/webresources/generic/buscar/" + idCliente);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = in.readLine();
                    return response.equals("1"); // Está en la BlackList
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Asume no está en BlackList si hay un error
    }

    // Método para consumir el servicio DataCredito (simulado)
    private boolean consultarDataCredito(String idCliente) {
        try {
            // URL del servicio DataCredito (ajusta si es necesario)
            URL url = new URL("http://localhost:8080/webresources/datacredito/consulta/" + idCliente);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

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