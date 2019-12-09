package by.tractorsheart.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class ModelTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelT.class);
        ModelT modelT1 = new ModelT();
        modelT1.setId(1L);
        ModelT modelT2 = new ModelT();
        modelT2.setId(modelT1.getId());
        assertThat(modelT1).isEqualTo(modelT2);
        modelT2.setId(2L);
        assertThat(modelT1).isNotEqualTo(modelT2);
        modelT1.setId(null);
        assertThat(modelT1).isNotEqualTo(modelT2);
    }
}
