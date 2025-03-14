package br.com.alura.forum.controller

import br.com.alura.forum.dto.NewTopicForm
import br.com.alura.forum.dto.TopicPerCategoryDto
import br.com.alura.forum.dto.TopicView
import br.com.alura.forum.dto.UpdateTopicForm
import br.com.alura.forum.service.TopicService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topics")
class TopicController(
    private val service: TopicService
) {

    @GetMapping
    @Cacheable("topics")
    fun list(
        @RequestParam(required = false) nameCourse: String?,
        @PageableDefault(
            size = 5,
            sort = ["createdAt"],
            direction = Sort.Direction.DESC
        ) pagination: Pageable
    ): Page<TopicView> {
        return service.list(
            nameCourse = nameCourse,
            pagination = pagination
        )
    }

    @GetMapping("/{id}")
    fun searchById(
        @PathVariable id: Long
    ): TopicView? {
        return try {
            service.searchById(id)
        }catch (e: Exception){
            null
        }
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topics"], allEntries = true)
    fun register(
        @RequestBody @Valid form: NewTopicForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicView> {
        val topicView = service.register(form)
        val uri = uriBuilder.path("/topics/${topicView.id}").build().toUri()
        return ResponseEntity.created(
            uri
        ).body(
            topicView
        )
    }

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topics"], allEntries = true)
    fun update(
        @RequestBody @Valid form: UpdateTopicForm
    ): ResponseEntity<TopicView> {
        val newTopic = service.update(form)
        return ResponseEntity.ok(newTopic)
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = ["topics"], allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable id: Long
    ) {
        service.delete(id)
    }

    @GetMapping("/report")
    fun report(): List<TopicPerCategoryDto> {
        return service.report()
    }
}