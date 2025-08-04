package com.respiroc.employee.domain.dto

data class EmployeeResponseDTO(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val projects: List<ProjectDTO>
)