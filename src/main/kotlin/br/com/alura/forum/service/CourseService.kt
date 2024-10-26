package br.com.alura.forum.service

import br.com.alura.forum.model.Course
import br.com.alura.forum.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(
    private val repository: CourseRepository
) {

    fun searchById(id: Long): Course? {
        return try {
            repository.getReferenceById(id)
        } catch (_: Exception){
            null
        }
    }

}
