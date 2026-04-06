package ma.fsr.eda.consultationservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092",
        "spring.kafka.consumer.group-id=test-group"
})
class ConsultationServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
