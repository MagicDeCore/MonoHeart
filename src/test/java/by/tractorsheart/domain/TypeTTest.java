package by.tractorsheart.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class TypeTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeT.class);
        TypeT typeT1 = new TypeT();
        typeT1.setId(1L);
        TypeT typeT2 = new TypeT();
        typeT2.setId(typeT1.getId());
        assertThat(typeT1).isEqualTo(typeT2);
        typeT2.setId(2L);
        assertThat(typeT1).isNotEqualTo(typeT2);
        typeT1.setId(null);
        assertThat(typeT1).isNotEqualTo(typeT2);
    }
}
