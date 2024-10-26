package br.com.alura.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class NewTopicForm(
    @field:NotEmpty
    @field:Size(min = 5, max = 100, message = "Titulo deve ter entre 5 e 100 caracteres") val title: String,
    @field:NotEmpty(message = "Mensagem não pode ser vazia")
    val message: String,
    @field:NotNull
    val idCourse: Int,
    @field:NotNull
    val idUser: Int
)

