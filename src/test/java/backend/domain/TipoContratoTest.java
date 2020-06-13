package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class TipoContratoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoContrato.class);
        TipoContrato tipoContrato1 = new TipoContrato();
        tipoContrato1.setId(1L);
        TipoContrato tipoContrato2 = new TipoContrato();
        tipoContrato2.setId(tipoContrato1.getId());
        assertThat(tipoContrato1).isEqualTo(tipoContrato2);
        tipoContrato2.setId(2L);
        assertThat(tipoContrato1).isNotEqualTo(tipoContrato2);
        tipoContrato1.setId(null);
        assertThat(tipoContrato1).isNotEqualTo(tipoContrato2);
    }
}
