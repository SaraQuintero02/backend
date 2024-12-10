package services;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

//SIMULACION DE APIREST
@Path("datacredito")
public class DataCreditoService {

    // Verificación crediticia básica (0 = No aprobado, 1 = Aprobado).
    @GET
    @Path("/consulta/{idCliente}")
    public int consultaCredito(@PathParam("idCliente") String idCliente) {
        // Simula lógica de aprobación/rechazo
        if (idCliente.equals("636352")) {
            return 0; // No aprobado
        }
        return 1; // Aprobado
    }

    // Consulta de BlackList (0 = En lista negra, 1 = No en lista negra).
    @GET
    @Path("/blacklist/{idCliente}")
    public int consultaBlackList(@PathParam("idCliente") String idCliente) {
        // Simulación de consulta a una lista negra
        if (idCliente.equals("123456")) {
            return 0; // Cliente está en la lista negra
        }
        return 1; // Cliente no está en la lista negra
    }

    // Consulta a DataCredito (0 = Alto riesgo, 1 = Bajo riesgo).
    @GET
    @Path("/datacredito/{idCliente}")
    public int consultaDataCredito(@PathParam("idCliente") String idCliente) {
        // Simulación de una consulta a un sistema externo
        switch (idCliente) {
            case "789012":
                return 0; // Alto riesgo crediticio
            case "345678":
                return 1; // Bajo riesgo crediticio
            default:
                return 0; // Por defecto, se considera no aprobado
        }
    }
}
