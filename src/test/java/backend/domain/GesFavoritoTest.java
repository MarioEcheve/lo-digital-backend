package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class GesFavoritoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GesFavorito.class);
        GesFavorito gesFavorito1 = new GesFavorito();
        gesFavorito1.setId(1L);
        GesFavorito gesFavorito2 = new GesFavorito();
        gesFavorito2.setId(gesFavorito1.getId());
        assertThat(gesFavorito1).isEqualTo(gesFavorito2);
        gesFavorito2.setId(2L);
        assertThat(gesFavorito1).isNotEqualTo(gesFavorito2);
        gesFavorito1.setId(null);
        assertThat(gesFavorito1).isNotEqualTo(gesFavorito2);
    }
}
