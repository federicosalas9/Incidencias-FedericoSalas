import java.util.Collection;

public interface IncidenteService {

    public void addIncidente(Incidente incidente);
    public Collection<Incidente> getIncidentes();
    public void editIncidente (Incidente incidente) throws IncidenteException;
}
