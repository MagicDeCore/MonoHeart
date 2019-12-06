package by.tractorsheart.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class MarkTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarkT.class);
        MarkT markT1 = new MarkT();
        markT1.setId(1L);
        MarkT markT2 = new MarkT();
        markT2.setId(markT1.getId());
        assertThat(markT1).isEqualTo(markT2);
        markT2.setId(2L);
        assertThat(markT1).isNotEqualTo(markT2);
        markT1.setId(null);
        assertThat(markT1).isNotEqualTo(markT2);
    }
}
