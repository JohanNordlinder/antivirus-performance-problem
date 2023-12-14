package minimal.reproducible.example.demo

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import kotlin.random.Random

@Component
class TestDataSetup(val repo: TestdataEntityRepository) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun createTestDataInDatabase(): Boolean {
        val rowsToCreate = 10000
        val start = Instant.now();
        log.info("Starting to create $rowsToCreate rows of test data in database...")

        for (i in 1..rowsToCreate) {
            repo.save(TestdataEntity(null, Random.nextInt().toString()))
            if (i % 100 == 0) {
                log.info(
                    "Finished creating $i rows, total elapsed time: ${
                        Duration.between(start, Instant.now()).toSeconds()
                    }s"
                )
            }
        }

        log.info(
            "Finished creating $rowsToCreate rows of test data, total elapsed time: ${
                Duration.between(
                    start,
                    Instant.now()
                ).toSeconds()
            }s"
        )

        return true
    }
}