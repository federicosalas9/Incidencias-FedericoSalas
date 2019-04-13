import com.google.gson.Gson;

import java.util.Date;

import static spark.Spark.*;

public class RestfulEjercicio {

    private static final UsuarioService usuarioService = new UsuarioServiceImpl();
    private static final ProyectoService proyectoService = new ProyectoServiceImpl();
    private static final IncidenteService incidenteService = new IncidenteServiceImpl();

    public static void main(String[] args) {
        port(8000);
        init();

        //Crear un usuario
        post("/usuarios", (request, response) -> {
            response.type("application/json");
            Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
            usuarioService.addUsuario(usuario);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        //Mostrar todos los usuarios creados
        get("/usuarios", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(usuarioService.getUsuarios())));
        });

        //Mostrar un usuario segun el id
        get("/usuarios/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJsonTree(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(usuarioService.getUsuario(Integer.parseInt(request.params(":id"))))));
        });

        //Editar un usuario segun el id
        put("/usuarios", (request, response) -> {
            response.type("application/json");
            Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
            Usuario usuarioEditado = usuarioService.editUsuario(usuario);
            if (usuarioEditado != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(usuarioEditado)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error al editar el usuario"));
            }
        });

        //Eliminar un usuario segun id
        delete("/usuarios/:id", (request, response) -> {
            response.type("application/json");
            usuarioService.deleteUsuario(Integer.parseInt(request.params(":id")));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "El usuario fue borrado"));
        });

        //Crear un proyecto
        post("/proyectos", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            proyectoService.addProyecto(proyecto);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        //Mostrar todos los proyectos creados
        get("/proyectos", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoService.getProyectos())));
        });

        //Mostrar un proyecto segun el id
        get("/proyectos/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJsonTree(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoService.getProyecto(Integer.parseInt(request.params(":id"))))));
        });

        //Editar un proyecto segun el id
        put("/proyectos", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            Proyecto proyectoEditado = proyectoService.editProyecto(proyecto);
            if (proyectoEditado != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(proyectoEditado)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error al editar el proyecto"));
            }
        });

        //Eliminar un proyecto segun id
        delete("/proyectos/:id", (request, response) -> {
            response.type("application/json");
            proyectoService.deleteProyecto(Integer.parseInt(request.params(":id")));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "El proyecto fue borrado"));
        });

        //Crear un incidente
        put("/incidentes", (request, response) -> {
            response.type("application/json");
            Incidente incidente = new Gson().fromJson(request.body(), Incidente.class);
            incidenteService.addIncidente(incidente);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        //Mostrar todos los incidentes
        get("/incidentes", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.getIncidentes())));
        });

        //Mostrar todos los proyectos de un usuario.
        get("/usuario/:id/proyectos", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoService.
                            getProyectosDeUs(usuarioService.getUsuario(Integer.parseInt(request.params(":id")))))));
        });
    }

    private static void init() {
        Usuario usuario1 = new Usuario(1, "Fede1", "Salas1");
        Usuario usuario2 = new Usuario(2, "Fede2", "Salas2");
        Usuario usuario3 = new Usuario(3, "Fede3", "Salas3");
        usuarioService.addUsuario(usuario1);
        usuarioService.addUsuario(usuario2);
        usuarioService.addUsuario(usuario3);
        Proyecto proyecto1 = new Proyecto(11, "proyecto1", usuario1);
        Proyecto proyecto2 = new Proyecto(22, "proyecto2", usuario2);
        proyectoService.addProyecto(proyecto1);
        proyectoService.addProyecto(proyecto2);
        Incidente incidente1 = new Incidente(111, Clasificacion.CRITICO,
                "El incidente1 asignado al proy 1 del us 1", usuario3, usuario1,
                Estado.ASIGNADO, new Date(), new Date(), proyecto1);
        Incidente incidente2 = new Incidente(222, Clasificacion.NORMAL,
                "El incidente2 asignado al proy 2 del us 2",
                usuario3, usuario2,
                Estado.RESUELTO, new Date(), new Date(), proyecto2);
        incidenteService.addIncidente(incidente1);
        incidenteService.addIncidente(incidente2);
    }
}
