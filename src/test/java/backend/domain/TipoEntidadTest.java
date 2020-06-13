package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class TipoEntidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEntidad.class);
        TipoEntidad tipoEntidad1 = new TipoEntidad();
        tipoEntidad1.setId(1L);
        TipoEntidad tipoEntidad2 = new TipoEntidad();
        tipoEntidad2.setId(tipoEntidad1.getId());
        assertThat(tipoEntidad1).isEqualTo(tipoEntidad2);
        tipoEntidad2.setId(2L);
        assertThat(tipoEntidad1).isNotEqualTo(tipoEntidad2);
        tipoEntidad1.setId(null);
        assertThat(tipoEntidad1).isNotEqualTo(tipoEntidad2);
    }
}
