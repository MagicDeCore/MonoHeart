package by.tractorsheart.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class ModuleTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleTDTO.class);
        ModuleTDTO moduleTDTO1 = new ModuleTDTO();
        moduleTDTO1.setId(1L);
        ModuleTDTO moduleTDTO2 = new ModuleTDTO();
        assertThat(moduleTDTO1).isNotEqualTo(moduleTDTO2);
        moduleTDTO2.setId(moduleTDTO1.getId());
        assertThat(moduleTDTO1).isEqualTo(moduleTDTO2);
        moduleTDTO2.setId(2L);
        assertThat(moduleTDTO1).isNotEqualTo(moduleTDTO2);
        moduleTDTO1.setId(null);
        assertThat(moduleTDTO1).isNotEqualTo(moduleTDTO2);
    }
}
