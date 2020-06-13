package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class GesAlertaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GesAlerta.class);
        GesAlerta gesAlerta1 = new GesAlerta();
        gesAlerta1.setId(1L);
        GesAlerta gesAlerta2 = new GesAlerta();
        gesAlerta2.setId(gesAlerta1.getId());
        assertThat(gesAlerta1).isEqualTo(gesAlerta2);
        gesAlerta2.setId(2L);
        assertThat(gesAlerta1).isNotEqualTo(gesAlerta2);
        gesAlerta1.setId(null);
        assertThat(gesAlerta1).isNotEqualTo(gesAlerta2);
    }
}
