package minimal.reproducible.example.demo

import jakarta.persistence.EntityManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

@Component
class TestDataSetup(val repo: TestdataEntityRepository, val em: EntityManager) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    val STRING_LENGTH = 100
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun randomString() = (1..STRING_LENGTH)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")

    fun createTestDataInDatabase(): Boolean {
        val rowsToCreate = 10000
        val start = Instant.now();

        log.info("Preparing $rowsToCreate objects of testdata...")
        val entities = (1..rowsToCreate).map { TestdataEntity(null, randomString()) }.toList()

        log.info("Starting to store $rowsToCreate objects (rows) of test data to database...")
        val countCreated = AtomicInteger(0)
        entities.parallelStream().forEach {
            repo.save(it)
            val newCountCreated = countCreated.incrementAndGet()
            if (newCountCreated % 100 == 0) {
                log.info(
                    "Finished creating $newCountCreated rows, total elapsed time: ${
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