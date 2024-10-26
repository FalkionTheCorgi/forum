package br.com.alura.forum.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Answers(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val message: String,
    @ManyToOne
    val author: User,
    @ManyToOne
    val topic: Topic,
    val isSolved: Boolean,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
