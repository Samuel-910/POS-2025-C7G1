package pe.edu.upeu.sysalmacen.excepciones;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ModelNotFoundException extends RuntimeException{
    private final HttpStatus status;

    public ModelNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ModelNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}