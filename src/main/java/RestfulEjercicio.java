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
            response.status(201);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "El usuario fue creado"));
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
            try {
                response.type("application/json");
                Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
                Usuario usuarioEditado = usuarioService.editUsuario(usuario);
                response.status(201);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(usuarioEditado)));
            } catch (Exception exception) {
                response.status(400);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, exception.getMessage()));
            }
        });

        //Eliminar un usuario segun id
        delete("/usuarios/:id", (request, response) -> {
            try {
                response.type("application/json");
                usuarioService.deleteUsuario(Integer.parseInt(request.params(":id")), proyectoService.getProyectos(), incidenteService.getIncidentes());
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "El usuario fue borrado"));
            } catch (UsuarioException exception) {
                response.status(400);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, exception.getMessage()));
            }

        });

        //Crear un proyecto
        post("/proyectos", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            proyectoService.addProyecto(proyecto);
            response.status(201);
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
            try {
                response.type("application/json");
                Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
                Proyecto proyectoEditado = proyectoService.editProyecto(proyecto);
                response.status(201);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(proyectoEditado)));
            } catch (Exception exception) {
                response.status(400);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, exception.getMessage()));
            }
        });

        //Eliminar un proyecto segun id
        delete("/proyectos/:id", (request, response) -> {
            try {
                response.type("application/json");
                proyectoService.deleteProyecto(Integer.parseInt(request.params(":id")), incidenteService.getIncidentes());
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "El proyecto fue borrado"));
            } catch (Exception exception) {
                response.status(400);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, exception.getMessage()));
            }
        });

        //Crear un incidente
        post("/incidentes", (request, response) -> {
            response.type("application/json");
            Incidente incidente = new Gson().fromJson(request.body(), Incidente.class);
            incidenteService.addIncidente(incidente);
            response.status(201);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        //Editar un incidente segun el id
        put("/incidentes", (request, response) -> {
            try {
                response.type("application/json");
                Incidente incidente = new Gson().fromJson(request.body(), Incidente.class);
                Incidente incidenteEditado = incidenteService.editIncidente(incidente);
                response.status(201);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(incidenteEditado)));
            } catch (Exception exception) {
                response.status(400);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, exception.getMessage()));
            }
        });

        //Mostrar todos los incidentes
        get("/incidentes", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.getIncidentes())));
        });

        //Mostrar todos los proyectos de un usuario.
        get("/usuarios/:id/proyectos", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoService.
                            getProyectosDeUs(usuarioService.getUsuario(Integer.parseInt(request.params(":id")))))));
        });

        //Mostrar todos los incidentes asignados a un usuario
        get("/usuarios/:id/incidentes/asignados", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.
                            getIncidentesAsignados(usuarioService.getUsuario(Integer.parseInt(request.params(":id")))))));
        });

        //Mostrar todos los incidentes creados por un usuario
        get("/usuarios/:id/incidentes/creados", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.
                            getIncidentesCreados(usuarioService.getUsuario(Integer.parseInt(request.params(":id")))))));
        });

        //Mostrar todos incidentes los asociados a un proyecto
        get("/usuarios/proyectos/incidentes", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.
                            getIncidentesProyecto(proyecto))));
        });

        //Mostrar todos los incidentes abiertos
        get("/usuarios/proyectos/incidentes/abiertos", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.getIncidentesAbiertos())));
        });

        //Mostrar todos los incidentes resueltos
        get("/usuarios/proyectos/incidentes/resueltos", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.getIncidentesResueltos())));
        });

    }

    //Inicializar la API con 3 usuarios, 2 proyectos y 2 incidentes
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
                Estado.ASIGNADO, new Date(2019, 3, 14, 10, 30, 15),
                new Date(2019, 3, 15, 10, 30, 15), proyecto1);
        Incidente incidente2 = new Incidente(222, Clasificacion.NORMAL,
                "El incidente2 asignado al proy 2 del us 2",
                usuario3, usuario2,
                Estado.RESUELTO, new Date(2019, 3, 14, 10, 30, 15),
                new Date(2019, 3, 15, 10, 30, 15), proyecto2);
        incidenteService.addIncidente(incidente1);
        incidenteService.addIncidente(incidente2);
    }
}
