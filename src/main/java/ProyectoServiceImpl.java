import java.util.*;

public class ProyectoServiceImpl implements ProyectoService{

    private HashMap<Integer,Proyecto> proyectoHashMap;

    public ProyectoServiceImpl() {
        proyectoHashMap = new HashMap<Integer, Proyecto>();
    }

    @Override
    public void addProyecto(Proyecto proyecto) {
        proyectoHashMap.put(proyecto.getId(),proyecto);
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
    public Proyecto editProyecto(Proyecto proyecto) throws ProyectoException{
        try {
            if(proyecto.getId()==0){
                throw new UsuarioException("El id del usuario que desea editar no puede ser nulo");
            }
            Proyecto proyectoAEditar=proyectoHashMap.get(proyecto.getId());
            //A continuacion comienzo a editar el proyecto
            if(proyecto.getTitulo()!=null){
                proyectoAEditar.setTitulo(proyecto.getTitulo());
            }
            if(proyecto.getPropietario()!=null){
                proyectoAEditar.setPropietario(proyecto.getPropietario());
            }
            return proyectoAEditar;

        }catch (Exception exception){
            throw new ProyectoException(exception.getMessage());
        }
    }

    @Override
    public void deleteProyecto(int id) {
        proyectoHashMap.remove(id);
    }

    @Override
    public Collection<Proyecto> getProyectosDeUs(Usuario usuario) {
        HashMap<Integer,Proyecto> proyectosDeUs=proyectoHashMap;
        Collection<Proyecto> proyectoCollection=proyectoHashMap.values();

       for (Proyecto p: proyectoCollection){
            if(p.getPropietario().equals(usuario)){
                proyectosDeUs.put(p.getId(),p);
            }
        }

        /*Iterator<Proyecto> it=getProyectos().iterator();
        while(it.hasNext()){
            if(it.next().getPropietario().equals(usuario)){
                proyectosDeUs.put(it.next().getId(),it.next());
            }
        }*/
        return proyectosDeUs.values();
    }
}
