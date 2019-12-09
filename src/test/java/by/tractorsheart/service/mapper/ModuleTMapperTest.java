package by.tractorsheart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ModuleTMapperTest {

    private ModuleTMapper moduleTMapper;

    @BeforeEach
    public void setUp() {
        moduleTMapper = new ModuleTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(moduleTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(moduleTMapper.fromId(null)).isNull();
    }
}
