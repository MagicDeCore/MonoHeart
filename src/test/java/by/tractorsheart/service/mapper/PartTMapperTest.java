package by.tractorsheart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PartTMapperTest {

    private PartTMapper partTMapper;

    @BeforeEach
    public void setUp() {
        partTMapper = new PartTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(partTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partTMapper.fromId(null)).isNull();
    }
}
