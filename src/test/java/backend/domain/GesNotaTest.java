package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class GesNotaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GesNota.class);
        GesNota gesNota1 = new GesNota();
        gesNota1.setId(1L);
        GesNota gesNota2 = new GesNota();
        gesNota2.setId(gesNota1.getId());
        assertThat(gesNota1).isEqualTo(gesNota2);
        gesNota2.setId(2L);
        assertThat(gesNota1).isNotEqualTo(gesNota2);
        gesNota1.setId(null);
        assertThat(gesNota1).isNotEqualTo(gesNota2);
    }
}
