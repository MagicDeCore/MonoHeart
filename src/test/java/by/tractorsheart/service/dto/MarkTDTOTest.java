package by.tractorsheart.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class MarkTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarkTDTO.class);
        MarkTDTO markTDTO1 = new MarkTDTO();
        markTDTO1.setId(1L);
        MarkTDTO markTDTO2 = new MarkTDTO();
        assertThat(markTDTO1).isNotEqualTo(markTDTO2);
        markTDTO2.setId(markTDTO1.getId());
        assertThat(markTDTO1).isEqualTo(markTDTO2);
        markTDTO2.setId(2L);
        assertThat(markTDTO1).isNotEqualTo(markTDTO2);
        markTDTO1.setId(null);
        assertThat(markTDTO1).isNotEqualTo(markTDTO2);
    }
}
