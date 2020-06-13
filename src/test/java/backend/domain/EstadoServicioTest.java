package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class EstadoServicioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoServicio.class);
        EstadoServicio estadoServicio1 = new EstadoServicio();
        estadoServicio1.setId(1L);
        EstadoServicio estadoServicio2 = new EstadoServicio();
        estadoServicio2.setId(estadoServicio1.getId());
        assertThat(estadoServicio1).isEqualTo(estadoServicio2);
        estadoServicio2.setId(2L);
        assertThat(estadoServicio1).isNotEqualTo(estadoServicio2);
        estadoServicio1.setId(null);
        assertThat(estadoServicio1).isNotEqualTo(estadoServicio2);
    }
}
