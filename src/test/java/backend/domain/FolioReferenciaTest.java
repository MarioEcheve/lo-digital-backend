package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class FolioReferenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FolioReferencia.class);
        FolioReferencia folioReferencia1 = new FolioReferencia();
        folioReferencia1.setId(1L);
        FolioReferencia folioReferencia2 = new FolioReferencia();
        folioReferencia2.setId(folioReferencia1.getId());
        assertThat(folioReferencia1).isEqualTo(folioReferencia2);
        folioReferencia2.setId(2L);
        assertThat(folioReferencia1).isNotEqualTo(folioReferencia2);
        folioReferencia1.setId(null);
        assertThat(folioReferencia1).isNotEqualTo(folioReferencia2);
    }
}
