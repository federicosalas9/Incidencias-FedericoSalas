import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class UsuarioServiceImpl implements UsuarioService {

    private HashMap<Integer, Usuario> usuarioHashMap;

    public UsuarioServiceImpl() {
        usuarioHashMap = new HashMap<Integer, Usuario>();
    }

    @Override
    public void addUsuario(Usuario usuario) {
        usuarioHashMap.put(usuario.getId(), usuario);
    }

    @Override
    public Collection<Usuario> getUsuarios() {
        return usuarioHashMap.values();
    }

    @Override
    public Usuario getUsuario(int id) {
        return usuarioHashMap.get(id);
    }

    @Override
    public Usuario editUsuario(Usuario usuario) throws UsuarioException {
        try {
            if (usuario.getId() == 0) {
                throw new UsuarioException("El id del usuario que desea editar no puede ser nulo");
            }
            Usuario usuarioAEditar = usuarioHashMap.get(usuario.getId());
            //A continuacion comienzo a editar el usuario
            if (usuario.getNombre() != null) {
                usuarioAEditar.setNombre(usuario.getNombre());
            }
            if (usuario.getApellido() != null) {
                usuarioAEditar.setApellido(usuario.getApellido());
            }
            return usuarioAEditar;

        } catch (Exception exception) {
            throw new UsuarioException(exception.getMessage());
        }
    }

    @Override
    public void deleteUsuario(int id, Collection<Proyecto> proyectos, Collection<Incidente> incidentes) throws UsuarioException {
        int contador = 0;
        for (Proyecto p : proyectos) {
            if (p.getPropietario().getId() == id) {
                contador++;
            }
        }
        for (Incidente i : incidentes) {
            if (i.getResponsable().getId() == id || i.getReportador().getId() == id) {
                contador++;
            }
        }
        if (contador > 0) {
            throw new UsuarioException("El usuario que desea eliminar es propietario de un proyecto, responsable o reportador");
        } else {
            usuarioHashMap.remove(id);
        }
    }
}
