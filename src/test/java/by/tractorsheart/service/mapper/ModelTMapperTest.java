package by.tractorsheart.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ModelTMapperTest {

    private ModelTMapper modelTMapper;

    @BeforeEach
    public void setUp() {
        modelTMapper = new ModelTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(modelTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(modelTMapper.fromId(null)).isNull();
    }
}
