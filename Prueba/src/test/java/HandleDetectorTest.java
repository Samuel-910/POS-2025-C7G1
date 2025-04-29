import org.example.HandleDetector;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HandleDetectorTest {

    private final HandleDetector detector = new HandleDetector();

    @Test
    public void testDetectUserHandles() {
        String text = "Hola @usuario1, conoces a @amigo_2?";
        List<String> expected = List.of("@usuario1", "@amigo_2");
        assertEquals(expected, detector.detectUserHandles(text));
    }

    @Test
    public void testDetectHashtagHandles() {
        String text = "Hoy es un gran día #feliz #sol";
        List<String> expected = List.of("#feliz", "#sol");
        assertEquals(expected, detector.detectHashtagHandles(text));
    }

    @Test
    public void testDetectWebHandles() {
        String text = "Visita https://example.com o www.ejemplo.es";
        List<String> expected = List.of("https://example.com", "www.ejemplo.es");
        assertEquals(expected, detector.detectWebHandles(text));
    }

    // 🚀 Nuevos tests para subir coverage:

    @Test
    public void testNoHandles() {
        String text = "Hola mundo normal sin arrobas ni hashtags ni webs.";
        List<String> expected = List.of();
        assertEquals(expected, detector.detectUserHandles(text));
        assertEquals(expected, detector.detectHashtagHandles(text));
        assertEquals(expected, detector.detectWebHandles(text));
    }

    @Test
    public void testEmptyText() {
        String text = "";
        List<String> expected = List.of();
        assertEquals(expected, detector.detectUserHandles(text));
        assertEquals(expected, detector.detectHashtagHandles(text));
        assertEquals(expected, detector.detectWebHandles(text));
    }


}
