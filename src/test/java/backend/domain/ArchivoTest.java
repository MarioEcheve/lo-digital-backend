package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class ArchivoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Archivo.class);
        Archivo archivo1 = new Archivo();
        archivo1.setId(1L);
        Archivo archivo2 = new Archivo();
        archivo2.setId(archivo1.getId());
        assertThat(archivo1).isEqualTo(archivo2);
        archivo2.setId(2L);
        assertThat(archivo1).isNotEqualTo(archivo2);
        archivo1.setId(null);
        assertThat(archivo1).isNotEqualTo(archivo2);
    }
}
