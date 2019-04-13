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
}
