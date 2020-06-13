package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class ActividadRubroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActividadRubro.class);
        ActividadRubro actividadRubro1 = new ActividadRubro();
        actividadRubro1.setId(1L);
        ActividadRubro actividadRubro2 = new ActividadRubro();
        actividadRubro2.setId(actividadRubro1.getId());
        assertThat(actividadRubro1).isEqualTo(actividadRubro2);
        actividadRubro2.setId(2L);
        assertThat(actividadRubro1).isNotEqualTo(actividadRubro2);
        actividadRubro1.setId(null);
        assertThat(actividadRubro1).isNotEqualTo(actividadRubro2);
    }
}
