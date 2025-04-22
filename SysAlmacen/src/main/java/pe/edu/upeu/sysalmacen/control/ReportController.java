package pe.edu.upeu.sysalmacen.control;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.sysalmacen.dtos.report.ProdMasVendidosDTO;
import pe.edu.upeu.sysalmacen.modelo.MediaFile;
import pe.edu.upeu.sysalmacen.servicio.IMediaFileService;
import pe.edu.upeu.sysalmacen.servicio.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reporte")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final IProductoService productoService;
    private final IMediaFileService mfService;
    private final Cloudinary cloudinary;
    @GetMapping("/pmvendidos")
    public List<ProdMasVendidosDTO> getProductosMasVendidos() {
        return productoService.obtenerProductosMasVendidos();
    }

    @GetMapping(value = "/generateReport")
    public ResponseEntity<byte[]> generateReport() {
        try {
            byte[] data = productoService.generateReport();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new ReportGenerationException("Error generando el reporte: " + e.getMessage());
        }
    }
    
    public class ReportGenerationException extends RuntimeException {
        public ReportGenerationException(String message) {
            super(message);
        }
    }

    

    @GetMapping(value = "/readFile/{idFile}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> readFile(@PathVariable("idFile") Long idFile) {
        try {
            byte[] data = mfService.findById(idFile).getContent();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new FileReadException("Error al leer el archivo con ID: " + idFile, e);
        }
    }
    public static class FileReadException extends RuntimeException {
        public FileReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class FileSaveException extends RuntimeException {
        public FileSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    @PostMapping(value = "/saveFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            MediaFile mf = new MediaFile();
            mf.setContent(multipartFile.getBytes());
            mf.setFileName(multipartFile.getOriginalFilename());
            mf.setFileType(multipartFile.getContentType());
            mfService.save(mf);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new FileSaveException("Error al guardar el archivo en la base de datos", e);
        }
    }

    @PostMapping(value = "/saveFileCloud", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFileCloud(@RequestParam("file") MultipartFile multipartFile) {
        try {
            File f = this.convertToFile(multipartFile);
            Map<String, Object> response = cloudinary.uploader().upload(f, ObjectUtils.asMap("resource_type", "auto"));
            JSONObject json = new JSONObject(response);
            String url = json.getString("url");
            logger.info(url);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new FileSaveException("Error al guardar el archivo en Cloudinary", e);
        }
    }


    

    private File convertToFile(MultipartFile multipartFile) throws FileConversionException {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new FileConversionException("Error al escribir el archivo", e); // Usamos la nueva excepción
        }
        return file;
    }
    
    public class FileConversionException extends RuntimeException {
        public FileConversionException(String message) {
            super(message);
        }
        
        public FileConversionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    


}