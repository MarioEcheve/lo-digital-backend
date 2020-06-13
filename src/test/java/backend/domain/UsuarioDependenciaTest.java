package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class UsuarioDependenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioDependencia.class);
        UsuarioDependencia usuarioDependencia1 = new UsuarioDependencia();
        usuarioDependencia1.setId(1L);
        UsuarioDependencia usuarioDependencia2 = new UsuarioDependencia();
        usuarioDependencia2.setId(usuarioDependencia1.getId());
        assertThat(usuarioDependencia1).isEqualTo(usuarioDependencia2);
        usuarioDependencia2.setId(2L);
        assertThat(usuarioDependencia1).isNotEqualTo(usuarioDependencia2);
        usuarioDependencia1.setId(null);
        assertThat(usuarioDependencia1).isNotEqualTo(usuarioDependencia2);
    }
}
