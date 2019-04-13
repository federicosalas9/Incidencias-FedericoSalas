import java.util.Collection;
import java.util.HashMap;

public class IncidenteServiceImpl implements IncidenteService{

    private HashMap<Integer,Incidente> incidenteHashMap;

    public IncidenteServiceImpl() {
        incidenteHashMap = new HashMap<Integer, Incidente>();
    }

    @Override
    public void addIncidente(Incidente incidente) {
        incidenteHashMap.put(incidente.getId(),incidente);
    }

    @Override
    public void editIncidente(Incidente incidente) throws IncidenteException {

    }

    @Override
    public Collection<Incidente> getIncidentes() {
        return incidenteHashMap.values();
    }

    @Override
    public Collection<Incidente> getIncidentesAsignados(Usuario usuario) {
        HashMap<Integer, Incidente> incidentesDeUs = new HashMap<>();
        for (Incidente i : getIncidentes()) {
            if (i.getResponsable().getId()==usuario.getId()) {
                incidentesDeUs.put(i.getId(),i);
            }
        }
        return incidentesDeUs.values();
    }

    @Override
    public Collection<Incidente> getIncidentesCreados(Usuario usuario) {
        HashMap<Integer, Incidente> incidentesDeUs = new HashMap<>();
        for (Incidente i : getIncidentes()) {
            if (i.getReportador().getId()==usuario.getId()) {
                incidentesDeUs.put(i.getId(),i);
            }
        }
        return incidentesDeUs.values();
    }

    @Override
    public Collection<Incidente> getIncidentesProyecto(Proyecto proyecto) {
        HashMap<Integer, Incidente> incidentesDeProy = new HashMap<>();
        for (Incidente i : getIncidentes()) {
            if (i.getProyecto().getId()==proyecto.getId()) {
                incidentesDeProy.put(i.getId(),i);
            }
        }
        return incidentesDeProy.values();
    }

    @Override
    public Collection<Incidente> getIncidentesAbiertos() {
        HashMap<Integer, Incidente> incidentesAbiertos = new HashMap<>();
        for (Incidente i : getIncidentes()) {
            if (i.getEstado()==Estado.ASIGNADO) {
                incidentesAbiertos.put(i.getId(),i);
            }
        }
        return incidentesAbiertos.values();
    }

    @Override
    public Collection<Incidente> getIncidentesResueltos() {
        HashMap<Integer, Incidente> incidentesResueltos = new HashMap<>();
        for (Incidente i : getIncidentes()) {
            if (i.getEstado()==Estado.RESUELTO) {
                incidentesResueltos.put(i.getId(),i);
            }
        }
        return incidentesResueltos.values();
    }
}
