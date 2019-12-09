package by.tractorsheart.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class NodeTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NodeTDTO.class);
        NodeTDTO nodeTDTO1 = new NodeTDTO();
        nodeTDTO1.setId(1L);
        NodeTDTO nodeTDTO2 = new NodeTDTO();
        assertThat(nodeTDTO1).isNotEqualTo(nodeTDTO2);
        nodeTDTO2.setId(nodeTDTO1.getId());
        assertThat(nodeTDTO1).isEqualTo(nodeTDTO2);
        nodeTDTO2.setId(2L);
        assertThat(nodeTDTO1).isNotEqualTo(nodeTDTO2);
        nodeTDTO1.setId(null);
        assertThat(nodeTDTO1).isNotEqualTo(nodeTDTO2);
    }
}
