package by.tractorsheart.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class ModuleTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleT.class);
        ModuleT moduleT1 = new ModuleT();
        moduleT1.setId(1L);
        ModuleT moduleT2 = new ModuleT();
        moduleT2.setId(moduleT1.getId());
        assertThat(moduleT1).isEqualTo(moduleT2);
        moduleT2.setId(2L);
        assertThat(moduleT1).isNotEqualTo(moduleT2);
        moduleT1.setId(null);
        assertThat(moduleT1).isNotEqualTo(moduleT2);
    }
}
