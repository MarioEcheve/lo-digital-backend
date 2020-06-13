package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class TipoFolioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoFolio.class);
        TipoFolio tipoFolio1 = new TipoFolio();
        tipoFolio1.setId(1L);
        TipoFolio tipoFolio2 = new TipoFolio();
        tipoFolio2.setId(tipoFolio1.getId());
        assertThat(tipoFolio1).isEqualTo(tipoFolio2);
        tipoFolio2.setId(2L);
        assertThat(tipoFolio1).isNotEqualTo(tipoFolio2);
        tipoFolio1.setId(null);
        assertThat(tipoFolio1).isNotEqualTo(tipoFolio2);
    }
}
