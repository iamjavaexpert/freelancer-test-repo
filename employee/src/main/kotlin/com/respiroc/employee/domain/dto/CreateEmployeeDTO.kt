package com.respiroc.employee.domain.dto

data class CreateEmployeeDTO(
    val name: String = "",
    val email: String = "",
    val role: String = "",
    val projectIds: List<Long> = emptyList()
)