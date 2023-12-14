package minimal.reproducible.example.demo

import org.springframework.data.repository.CrudRepository

interface TestdataEntityRepository : CrudRepository<TestdataEntity, Long>