package by.tractorsheart.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class DetailTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailTDTO.class);
        DetailTDTO detailTDTO1 = new DetailTDTO();
        detailTDTO1.setId(1L);
        DetailTDTO detailTDTO2 = new DetailTDTO();
        assertThat(detailTDTO1).isNotEqualTo(detailTDTO2);
        detailTDTO2.setId(detailTDTO1.getId());
        assertThat(detailTDTO1).isEqualTo(detailTDTO2);
        detailTDTO2.setId(2L);
        assertThat(detailTDTO1).isNotEqualTo(detailTDTO2);
        detailTDTO1.setId(null);
        assertThat(detailTDTO1).isNotEqualTo(detailTDTO2);
    }
}
