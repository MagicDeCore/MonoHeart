package by.tractorsheart.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class PartTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartTDTO.class);
        PartTDTO partTDTO1 = new PartTDTO();
        partTDTO1.setId(1L);
        PartTDTO partTDTO2 = new PartTDTO();
        assertThat(partTDTO1).isNotEqualTo(partTDTO2);
        partTDTO2.setId(partTDTO1.getId());
        assertThat(partTDTO1).isEqualTo(partTDTO2);
        partTDTO2.setId(2L);
        assertThat(partTDTO1).isNotEqualTo(partTDTO2);
        partTDTO1.setId(null);
        assertThat(partTDTO1).isNotEqualTo(partTDTO2);
    }
}
