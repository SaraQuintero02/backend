package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

// Simulación de un servicio REST para operaciones de crédito.
@Path("datacredito")
public class DataCreditoService {

    // Verificación crediticia básica.
    @GET
    @Path("/consulta/{id}")
    public String consultaCredito(@PathParam("id") String idCliente) {
        // Simula lógica de aprobación/rechazo
        if (idCliente.equals("636352")) {
            return "rechazado";
        }
        return "aprobado";
    }

    // Consulta de BlackList
    @GET
    @Path("/blacklist/{idCliente}")
    public String consultaBlackList(@PathParam("idCliente") String idCliente) {
        // Simulación de consulta a una lista negra
        if (idCliente.equals("123456")) {
            return "El cliente está en la lista negra.";
        }
        return "El cliente no está en la lista negra.";
    }

    // Consulta a DataCredito (sistema externo)
    @GET
    @Path("/datacredito/{idCliente}")
    public String consultaDataCredito(@PathParam("idCliente") String idCliente) {
        // Simulación de una consulta a un sistema externo
        switch (idCliente) {
            case "789012":
                return "Puntaje: 450. Cliente con alto riesgo crediticio.";
            case "345678":
                return "Puntaje: 750. Cliente con bajo riesgo crediticio.";
            default:
                return "Cliente no encontrado en DataCredito.";
        }
    }
}