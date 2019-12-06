package by.tractorsheart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TypeTMapperTest {

    private TypeTMapper typeTMapper;

    @BeforeEach
    public void setUp() {
        typeTMapper = new TypeTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(typeTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeTMapper.fromId(null)).isNull();
    }
}
