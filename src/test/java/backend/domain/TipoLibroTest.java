package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class TipoLibroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoLibro.class);
        TipoLibro tipoLibro1 = new TipoLibro();
        tipoLibro1.setId(1L);
        TipoLibro tipoLibro2 = new TipoLibro();
        tipoLibro2.setId(tipoLibro1.getId());
        assertThat(tipoLibro1).isEqualTo(tipoLibro2);
        tipoLibro2.setId(2L);
        assertThat(tipoLibro1).isNotEqualTo(tipoLibro2);
        tipoLibro1.setId(null);
        assertThat(tipoLibro1).isNotEqualTo(tipoLibro2);
    }
}
