package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class ContratoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contrato.class);
        Contrato contrato1 = new Contrato();
        contrato1.setId(1L);
        Contrato contrato2 = new Contrato();
        contrato2.setId(contrato1.getId());
        assertThat(contrato1).isEqualTo(contrato2);
        contrato2.setId(2L);
        assertThat(contrato1).isNotEqualTo(contrato2);
        contrato1.setId(null);
        assertThat(contrato1).isNotEqualTo(contrato2);
    }
}
