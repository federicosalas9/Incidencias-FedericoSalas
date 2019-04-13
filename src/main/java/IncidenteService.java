import java.util.Collection;

public interface IncidenteService {

    public void addIncidente(Incidente incidente);
    public Collection<Incidente> getIncidentes();
    public Collection<Incidente> getIncidentesAsignados(Usuario usuario);
    public Collection<Incidente> getIncidentesCreados(Usuario usuario);
    public Collection<Incidente> getIncidentesProyecto(Proyecto proyecto);
    public Collection<Incidente> getIncidentesAbiertos();
    public Collection<Incidente> getIncidentesResueltos();
    public void editIncidente (Incidente incidente) throws IncidenteException;
}
