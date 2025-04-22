package pe.edu.upeu.sysalmacen.servicio.impl;

import pe.edu.upeu.sysalmacen.excepciones.CustomResponse;
import pe.edu.upeu.sysalmacen.excepciones.ModelNotFoundException;
import pe.edu.upeu.sysalmacen.repositorio.ICrudGenericoRepository;
import pe.edu.upeu.sysalmacen.servicio.ICrudGenericoService;

import java.time.LocalDateTime;
import java.util.List;

public abstract class CrudGenericoServiceImp<T,I> implements ICrudGenericoService<T,I> {
    protected abstract ICrudGenericoRepository<T, I> getRepo();

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }
    @Override
    public T update(I id, T t) {
        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUNDa: " + id));
    }
    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }
    @Override
    public T findById(I id) {
        return getRepo()
            .findById(id)
            .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
    }
    
    @Override
    public CustomResponse delete(I id) {

        getRepo().deleteById(id);
        CustomResponse cer=new CustomResponse();
        cer.setStatusCode(200);
        cer.setDatetime(LocalDateTime.now());
        cer.setMessage("true");
        cer.setDetails("Todo Ok");
        return cer;
    }
}
