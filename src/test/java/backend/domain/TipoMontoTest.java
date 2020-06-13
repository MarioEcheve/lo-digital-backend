package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class TipoMontoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoMonto.class);
        TipoMonto tipoMonto1 = new TipoMonto();
        tipoMonto1.setId(1L);
        TipoMonto tipoMonto2 = new TipoMonto();
        tipoMonto2.setId(tipoMonto1.getId());
        assertThat(tipoMonto1).isEqualTo(tipoMonto2);
        tipoMonto2.setId(2L);
        assertThat(tipoMonto1).isNotEqualTo(tipoMonto2);
        tipoMonto1.setId(null);
        assertThat(tipoMonto1).isNotEqualTo(tipoMonto2);
    }
}
