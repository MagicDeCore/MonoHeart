package by.tractorsheart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class NodeTMapperTest {

    private NodeTMapper nodeTMapper;

    @BeforeEach
    public void setUp() {
        nodeTMapper = new NodeTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(nodeTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(nodeTMapper.fromId(null)).isNull();
    }
}
