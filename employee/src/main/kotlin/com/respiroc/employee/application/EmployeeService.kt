package com.respiroc.employee.application

import com.respiroc.employee.domain.dto.CreateEmployeeDTO
import com.respiroc.employee.domain.dto.EmployeeResponseDTO
import com.respiroc.employee.domain.dto.ProjectDTO
import com.respiroc.employee.domain.model.Employee
import com.respiroc.employee.domain.repository.EmployeeRepository
import com.respiroc.project.domain.repository.ProjectRepository
import org.springframework.stereotype.Service


@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val projectRepository: ProjectRepository
) {

    fun createEmployee(dto: CreateEmployeeDTO): Employee {
        val selectedProjects = projectRepository.findAllById(dto.projectIds).toSet()
        val employee = Employee(
            name = dto.name,
            email = dto.email,
            role = dto.role,
            projects = selectedProjects.toMutableSet()
        )
        return employeeRepository.save(employee)
    }

    fun getAllEmployees(): List<EmployeeResponseDTO> {
        return employeeRepository.findAll().map { emp ->
            EmployeeResponseDTO(
                id = emp.id,
                name = emp.name,
                email = emp.email,
                role = emp.role,
                projects = emp.projects.map { ProjectDTO(it.id, it.name) }
            )
        }
    }
}
