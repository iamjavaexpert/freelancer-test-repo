package com.respiroc.project.application

import com.respiroc.project.domain.model.Project
import com.respiroc.project.domain.repository.ProjectRepository
import org.springframework.stereotype.Service


@Service
class ProjectService(
    private val projectRepository: ProjectRepository
) {

    fun createProject(project: Project): Project {
        return projectRepository.save(project)
    }

    fun getAllProjects(): List<Project> {
        return projectRepository.findAll()
    }
}