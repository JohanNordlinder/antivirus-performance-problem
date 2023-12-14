package minimal.reproducible.example.demo

import jakarta.persistence.*

@Entity
@Table(name = "testdata", schema = "public", catalog = "postgres")
data class TestdataEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = 0L,

    @Column(name = "data", nullable = false)
    var data: String
)