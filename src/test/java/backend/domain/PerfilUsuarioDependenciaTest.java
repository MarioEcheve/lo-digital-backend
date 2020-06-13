package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class PerfilUsuarioDependenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilUsuarioDependencia.class);
        PerfilUsuarioDependencia perfilUsuarioDependencia1 = new PerfilUsuarioDependencia();
        perfilUsuarioDependencia1.setId(1L);
        PerfilUsuarioDependencia perfilUsuarioDependencia2 = new PerfilUsuarioDependencia();
        perfilUsuarioDependencia2.setId(perfilUsuarioDependencia1.getId());
        assertThat(perfilUsuarioDependencia1).isEqualTo(perfilUsuarioDependencia2);
        perfilUsuarioDependencia2.setId(2L);
        assertThat(perfilUsuarioDependencia1).isNotEqualTo(perfilUsuarioDependencia2);
        perfilUsuarioDependencia1.setId(null);
        assertThat(perfilUsuarioDependencia1).isNotEqualTo(perfilUsuarioDependencia2);
    }
}
