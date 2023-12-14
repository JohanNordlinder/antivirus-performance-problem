package minimal.reproducible.example.demo

import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@EnableScheduling
@Profile("!UNIT_TEST")
@Component
class TestDataSetupTrigger(val testDataSetup: TestDataSetup) {

    @Scheduled(initialDelay = 1)
    fun trigger() {
        testDataSetup.createTestDataInDatabase()
    }
}