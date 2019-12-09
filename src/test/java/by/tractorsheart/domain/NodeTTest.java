package by.tractorsheart.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import by.tractorsheart.web.rest.TestUtil;

public class NodeTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NodeT.class);
        NodeT nodeT1 = new NodeT();
        nodeT1.setId(1L);
        NodeT nodeT2 = new NodeT();
        nodeT2.setId(nodeT1.getId());
        assertThat(nodeT1).isEqualTo(nodeT2);
        nodeT2.setId(2L);
        assertThat(nodeT1).isNotEqualTo(nodeT2);
        nodeT1.setId(null);
        assertThat(nodeT1).isNotEqualTo(nodeT2);
    }
}
