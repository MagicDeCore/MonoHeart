package by.tractorsheart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DetailTMapperTest {

    private DetailTMapper detailTMapper;

    @BeforeEach
    public void setUp() {
        detailTMapper = new DetailTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(detailTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(detailTMapper.fromId(null)).isNull();
    }
}
