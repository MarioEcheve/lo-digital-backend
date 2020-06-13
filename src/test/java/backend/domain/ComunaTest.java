package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class ComunaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comuna.class);
        Comuna comuna1 = new Comuna();
        comuna1.setId(1L);
        Comuna comuna2 = new Comuna();
        comuna2.setId(comuna1.getId());
        assertThat(comuna1).isEqualTo(comuna2);
        comuna2.setId(2L);
        assertThat(comuna1).isNotEqualTo(comuna2);
        comuna1.setId(null);
        assertThat(comuna1).isNotEqualTo(comuna2);
    }
}
