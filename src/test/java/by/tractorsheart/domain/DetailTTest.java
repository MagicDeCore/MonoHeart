package by.tractorsheart.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class DetailTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailT.class);
        DetailT detailT1 = new DetailT();
        detailT1.setId(1L);
        DetailT detailT2 = new DetailT();
        detailT2.setId(detailT1.getId());
        assertThat(detailT1).isEqualTo(detailT2);
        detailT2.setId(2L);
        assertThat(detailT1).isNotEqualTo(detailT2);
        detailT1.setId(null);
        assertThat(detailT1).isNotEqualTo(detailT2);
    }
}
