package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class DependenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dependencia.class);
        Dependencia dependencia1 = new Dependencia();
        dependencia1.setId(1L);
        Dependencia dependencia2 = new Dependencia();
        dependencia2.setId(dependencia1.getId());
        assertThat(dependencia1).isEqualTo(dependencia2);
        dependencia2.setId(2L);
        assertThat(dependencia1).isNotEqualTo(dependencia2);
        dependencia1.setId(null);
        assertThat(dependencia1).isNotEqualTo(dependencia2);
    }
}
