package br.com.alura.forum.repository

import br.com.alura.forum.dto.TopicPerCategoryDto
import br.com.alura.forum.model.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TopicRepository: JpaRepository<Topic, Long> {
    fun findByCourseName(
        courseName: String,
        pagination: Pageable
    ): Page<Topic>

    @Query("SELECT new br.com.alura.forum.dto.TopicPerCategoryDto(course.category, count(t)) FROM Topic t LEFT JOIN t.course course GROUP BY course.category")
    fun report(): List<TopicPerCategoryDto>
}