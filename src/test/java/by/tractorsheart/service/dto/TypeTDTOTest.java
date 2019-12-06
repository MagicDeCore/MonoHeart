package by.tractorsheart.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class TypeTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTDTO.class);
        TypeTDTO typeTDTO1 = new TypeTDTO();
        typeTDTO1.setId(1L);
        TypeTDTO typeTDTO2 = new TypeTDTO();
        assertThat(typeTDTO1).isNotEqualTo(typeTDTO2);
        typeTDTO2.setId(typeTDTO1.getId());
        assertThat(typeTDTO1).isEqualTo(typeTDTO2);
        typeTDTO2.setId(2L);
        assertThat(typeTDTO1).isNotEqualTo(typeTDTO2);
        typeTDTO1.setId(null);
        assertThat(typeTDTO1).isNotEqualTo(typeTDTO2);
    }
}
