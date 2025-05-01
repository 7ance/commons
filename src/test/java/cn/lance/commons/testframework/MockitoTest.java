package cn.lance.commons.testframework;

import cn.lance.commons.entity.Bar;
import cn.lance.commons.entity.Foo;
import cn.lance.commons.util.json.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@Slf4j
public class MockitoTest {

    @Test
    public void testMock() throws JsonProcessingException {
        Foo noArgFoo = new Foo();
        String noArgFooJson = JsonUtils.write(noArgFoo);
        log.info("Foo noArgJson: \n{}", noArgFooJson);

        Foo mockFoo = mock(Foo.class);
        String mockFooJson = JsonUtils.write(mockFoo);
        log.info("Foo mockJson: \n{}", mockFooJson);

        Bar noArgBar = new Bar();
        String noArgBarJson = JsonUtils.write(noArgBar);
        log.info("Bar noArgJson: \n{}", noArgBarJson);

        Bar mockBar = mock(Bar.class);
        String mockBarJson = JsonUtils.write(mockBar);
        log.info("Bar mockJson: \n{}", mockBarJson);
    }

    @Test
    public void testWhen() {
        Bar mock = mock(Bar.class);
        when(mock.getInteger()).thenReturn(200);
        Integer integer = mock.getInteger();
        log.info("integer: {}", integer);
        Assertions.assertEquals(200, integer);
    }

}
