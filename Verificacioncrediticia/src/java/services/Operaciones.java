package services;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("generic")
public class Operaciones {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Operaciones
     */
    public Operaciones() {
    }

    @GET
    @Path("/lista")
    public List<String> listar() {
        List<String> items = new ArrayList<>();
        items.add("450170");
        items.add("150891");
        items.add("131415");
        items.add("253570");
        items.add("701001");

        return items;
    }

    @GET
    @Path("buscar/{id}")
    public int buscar(@PathParam("id") String id) {
        int rta = 0;
        for (int i = 0; i < listar().size(); i++) {
            if (listar().get(i).equals(id)) {
                return 1;
            }
        }
        return rta;
    }
}
