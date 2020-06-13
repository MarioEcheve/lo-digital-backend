package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class TipoUsuarioDependenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoUsuarioDependencia.class);
        TipoUsuarioDependencia tipoUsuarioDependencia1 = new TipoUsuarioDependencia();
        tipoUsuarioDependencia1.setId(1L);
        TipoUsuarioDependencia tipoUsuarioDependencia2 = new TipoUsuarioDependencia();
        tipoUsuarioDependencia2.setId(tipoUsuarioDependencia1.getId());
        assertThat(tipoUsuarioDependencia1).isEqualTo(tipoUsuarioDependencia2);
        tipoUsuarioDependencia2.setId(2L);
        assertThat(tipoUsuarioDependencia1).isNotEqualTo(tipoUsuarioDependencia2);
        tipoUsuarioDependencia1.setId(null);
        assertThat(tipoUsuarioDependencia1).isNotEqualTo(tipoUsuarioDependencia2);
    }
}
