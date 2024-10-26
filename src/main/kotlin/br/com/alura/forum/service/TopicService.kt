package br.com.alura.forum.service

import br.com.alura.forum.dto.NewTopicForm
import br.com.alura.forum.dto.TopicPerCategoryDto
import br.com.alura.forum.dto.TopicView
import br.com.alura.forum.dto.UpdateTopicForm
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicFormMapper
import br.com.alura.forum.mapper.TopicViewMapper
import br.com.alura.forum.repository.TopicRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val repository: TopicRepository,
    private val topicViewMapper: TopicViewMapper,
    private val topicFormMapper: TopicFormMapper,
    private val notFoundException: String = "Topico não encontrado"
) {

    fun list(
        nameCourse: String?,
        pagination: Pageable
    ): Page<TopicView> {
        val topics = if (nameCourse == null)
            repository.findAll(pagination)
        else
            repository.findByCourseName(nameCourse, pagination)
        return topics.map { topicViewMapper.map(it) }
    }

    fun searchById(id: Long): TopicView? {
        return try {
            topicViewMapper.map(
                repository.getReferenceById(id)
            )
        } catch (_: Exception){
            throw NotFoundException(notFoundException)
        }
    }

    fun register(dto: NewTopicForm): TopicView {
        val topic = topicFormMapper.map(dto)
        repository.save(topic)
        return topicViewMapper.map(topic)
    }

    fun update(form: UpdateTopicForm): TopicView {
        try {
            val topic = repository.getReferenceById(form.id)
            topic.title = form.title
            topic.message = form.message
            return topicViewMapper.map(topic)
        } catch (e: Exception){
            throw NotFoundException(notFoundException)
        }
    }

    fun delete(id: Long) {
        try {
            repository.deleteById(id)
        } catch (e: Exception){
            throw NotFoundException(notFoundException)
        }
    }

    fun report(): List<TopicPerCategoryDto> {
        return repository.report()
    }

}