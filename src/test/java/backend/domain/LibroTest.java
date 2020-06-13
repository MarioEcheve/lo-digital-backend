package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class LibroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Libro.class);
        Libro libro1 = new Libro();
        libro1.setId(1L);
        Libro libro2 = new Libro();
        libro2.setId(libro1.getId());
        assertThat(libro1).isEqualTo(libro2);
        libro2.setId(2L);
        assertThat(libro1).isNotEqualTo(libro2);
        libro1.setId(null);
        assertThat(libro1).isNotEqualTo(libro2);
    }
}
