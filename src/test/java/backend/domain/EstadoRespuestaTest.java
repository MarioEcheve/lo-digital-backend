package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class EstadoRespuestaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoRespuesta.class);
        EstadoRespuesta estadoRespuesta1 = new EstadoRespuesta();
        estadoRespuesta1.setId(1L);
        EstadoRespuesta estadoRespuesta2 = new EstadoRespuesta();
        estadoRespuesta2.setId(estadoRespuesta1.getId());
        assertThat(estadoRespuesta1).isEqualTo(estadoRespuesta2);
        estadoRespuesta2.setId(2L);
        assertThat(estadoRespuesta1).isNotEqualTo(estadoRespuesta2);
        estadoRespuesta1.setId(null);
        assertThat(estadoRespuesta1).isNotEqualTo(estadoRespuesta2);
    }
}
