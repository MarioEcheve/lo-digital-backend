package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class EstadoLibroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoLibro.class);
        EstadoLibro estadoLibro1 = new EstadoLibro();
        estadoLibro1.setId(1L);
        EstadoLibro estadoLibro2 = new EstadoLibro();
        estadoLibro2.setId(estadoLibro1.getId());
        assertThat(estadoLibro1).isEqualTo(estadoLibro2);
        estadoLibro2.setId(2L);
        assertThat(estadoLibro1).isNotEqualTo(estadoLibro2);
        estadoLibro1.setId(null);
        assertThat(estadoLibro1).isNotEqualTo(estadoLibro2);
    }
}
