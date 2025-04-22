package pe.edu.upeu.sysalmacen.servicio;

import pe.edu.upeu.sysalmacen.excepciones.CustomResponse;

import java.util.List;

public interface ICrudGenericoService<T,I> {
    T save(T t);
    T update(I id, T t);
    List<T> findAll();
    T findById(I id);
    CustomResponse delete(I id);
}
