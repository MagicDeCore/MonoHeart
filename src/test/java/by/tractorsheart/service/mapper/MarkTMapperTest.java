package by.tractorsheart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MarkTMapperTest {

    private MarkTMapper markTMapper;

    @BeforeEach
    public void setUp() {
        markTMapper = new MarkTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(markTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(markTMapper.fromId(null)).isNull();
    }
}
