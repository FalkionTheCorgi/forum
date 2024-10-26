package br.com.alura.forum.mapper

import br.com.alura.forum.dto.NewTopicForm
import br.com.alura.forum.model.Topic
import br.com.alura.forum.service.CourseService
import br.com.alura.forum.service.UserService
import org.springframework.stereotype.Component

@Component
class TopicFormMapper(
    private val courseService: CourseService,
    private val userService: UserService,
): Mapper<NewTopicForm, Topic> {
    override fun map(t: NewTopicForm): Topic {
        val course = courseService.searchById(t.idCourse.toLong())
        val user = userService.searchById(t.idUser.toLong())
        if (course != null && user != null) {
            return Topic(
                title = t.title,
                message = t.message,
                course = course,
                author = user
            )
        } else {
            throw IllegalArgumentException("Course or User not found.")
        }
    }
}
