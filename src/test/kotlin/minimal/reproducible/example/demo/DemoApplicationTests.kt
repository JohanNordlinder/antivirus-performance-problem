package minimal.reproducible.example.demo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("UNIT_TEST")
class DemoApplicationTests {

    @Autowired
    lateinit var testDataSetup: TestDataSetup

    @Test
    fun contextLoads() {
        testDataSetup.createTestDataInDatabase()
    }
}
