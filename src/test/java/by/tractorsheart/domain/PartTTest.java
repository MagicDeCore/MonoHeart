package by.tractorsheart.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class PartTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartT.class);
        PartT partT1 = new PartT();
        partT1.setId(1L);
        PartT partT2 = new PartT();
        partT2.setId(partT1.getId());
        assertThat(partT1).isEqualTo(partT2);
        partT2.setId(2L);
        assertThat(partT1).isNotEqualTo(partT2);
        partT1.setId(null);
        assertThat(partT1).isNotEqualTo(partT2);
    }
}
