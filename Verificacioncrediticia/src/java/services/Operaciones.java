package services;

import com.sun.org.apache.bcel.internal.generic.Select;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author uestudiantes
 */
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
    public List<String> listar(){
        List<String> items = new ArrayList<>();
        items.add("454589-AAA, Aprobado");
        items.add("636352-A, No aprobado");
        items.add("101252-A, No aprobado");
        items.add("140058-AAA, Aprobado");
        
        return items;
    }
    
    @GET
    @Path("/buscar/{id}")
    public String buscar(@PathParam("id") String id){
        String rta = "0";
        for (int i =0; i < listar().size(); i++){
            if (listar().get(i).equals(id))
                return "1";
            }
        return rta;
    }
    
}