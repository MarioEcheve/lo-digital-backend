package backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import backend.web.rest.TestUtil;

public class UsuarioLibroPerfilTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioLibroPerfil.class);
        UsuarioLibroPerfil usuarioLibroPerfil1 = new UsuarioLibroPerfil();
        usuarioLibroPerfil1.setId(1L);
        UsuarioLibroPerfil usuarioLibroPerfil2 = new UsuarioLibroPerfil();
        usuarioLibroPerfil2.setId(usuarioLibroPerfil1.getId());
        assertThat(usuarioLibroPerfil1).isEqualTo(usuarioLibroPerfil2);
        usuarioLibroPerfil2.setId(2L);
        assertThat(usuarioLibroPerfil1).isNotEqualTo(usuarioLibroPerfil2);
        usuarioLibroPerfil1.setId(null);
        assertThat(usuarioLibroPerfil1).isNotEqualTo(usuarioLibroPerfil2);
    }
}
