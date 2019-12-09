package by.tractorsheart.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class ModelTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelTDTO.class);
        ModelTDTO modelTDTO1 = new ModelTDTO();
        modelTDTO1.setId(1L);
        ModelTDTO modelTDTO2 = new ModelTDTO();
        assertThat(modelTDTO1).isNotEqualTo(modelTDTO2);
        modelTDTO2.setId(modelTDTO1.getId());
        assertThat(modelTDTO1).isEqualTo(modelTDTO2);
        modelTDTO2.setId(2L);
        assertThat(modelTDTO1).isNotEqualTo(modelTDTO2);
        modelTDTO1.setId(null);
        assertThat(modelTDTO1).isNotEqualTo(modelTDTO2);
    }
}
