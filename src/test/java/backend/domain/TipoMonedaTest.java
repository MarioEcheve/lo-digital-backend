package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class TipoMonedaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoMoneda.class);
        TipoMoneda tipoMoneda1 = new TipoMoneda();
        tipoMoneda1.setId(1L);
        TipoMoneda tipoMoneda2 = new TipoMoneda();
        tipoMoneda2.setId(tipoMoneda1.getId());
        assertThat(tipoMoneda1).isEqualTo(tipoMoneda2);
        tipoMoneda2.setId(2L);
        assertThat(tipoMoneda1).isNotEqualTo(tipoMoneda2);
        tipoMoneda1.setId(null);
        assertThat(tipoMoneda1).isNotEqualTo(tipoMoneda2);
    }
}
