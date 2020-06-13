package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class FolioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Folio.class);
        Folio folio1 = new Folio();
        folio1.setId(1L);
        Folio folio2 = new Folio();
        folio2.setId(folio1.getId());
        assertThat(folio1).isEqualTo(folio2);
        folio2.setId(2L);
        assertThat(folio1).isNotEqualTo(folio2);
        folio1.setId(null);
        assertThat(folio1).isNotEqualTo(folio2);
    }
}
