import java.util.*;

public class ProyectoServiceImpl implements ProyectoService {

    private HashMap<Integer, Proyecto> proyectoHashMap;

    public ProyectoServiceImpl() {
        proyectoHashMap = new HashMap<Integer, Proyecto>();
    }

    @Override
    public void addProyecto(Proyecto proyecto) {
        proyectoHashMap.put(proyecto.getId(), proyecto);
    }

    @Override
    public Collection<Proyecto> getProyectos() {
        return proyectoHashMap.values();
    }

    @Override
    public Proyecto getProyecto(int id) {

        return proyectoHashMap.get(id);
    }

    @Override
    public Proyecto editProyecto(Proyecto proyecto) throws ProyectoException {
        int contador = 0;
        for (Proyecto p : getProyectos()) {
            if (p.getId() == proyecto.getId()) {
                contador++;
            }
        }
        if (contador == 0) {
            throw new ProyectoException("El id del proyecto que desea editar no corresponde a un proyecto existente");
        }
        Proyecto proyectoAEditar = proyectoHashMap.get(proyecto.getId());
        //A continuacion comienzo a editar el proyecto
        if (proyecto.getTitulo() != null) {
            proyectoAEditar.setTitulo(proyecto.getTitulo());
        }
        if (proyecto.getPropietario() != null) {
            proyectoAEditar.setPropietario(proyecto.getPropietario());
        }
        return proyectoAEditar;
    }

    @Override
    public void deleteProyecto(int id, Collection<Incidente> incidentes) throws IncidenteException {
        int contador = 0;
        for (Incidente i : incidentes) {
            if (i.getProyecto().getId() == id) {
                contador++;
            }
        }
        if (contador > 0) {
            throw new IncidenteException("El proyecto tiene al menos un incidente reportado");
        } else {
            proyectoHashMap.remove(id);
        }

    }

    @Override
    public Collection<Proyecto> getProyectosDeUs(Usuario usuario) {
        HashMap<Integer, Proyecto> proyectosDeUs = new HashMap<>();
        for (Proyecto p : getProyectos()) {
            if (p.getPropietario().getId() == usuario.getId()) {
                proyectosDeUs.put(p.getId(), p);
            }
        }
        return proyectosDeUs.values();
    }
}
