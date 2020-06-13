package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class TipoFirmaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoFirma.class);
        TipoFirma tipoFirma1 = new TipoFirma();
        tipoFirma1.setId(1L);
        TipoFirma tipoFirma2 = new TipoFirma();
        tipoFirma2.setId(tipoFirma1.getId());
        assertThat(tipoFirma1).isEqualTo(tipoFirma2);
        tipoFirma2.setId(2L);
        assertThat(tipoFirma1).isNotEqualTo(tipoFirma2);
        tipoFirma1.setId(null);
        assertThat(tipoFirma1).isNotEqualTo(tipoFirma2);
    }
}
