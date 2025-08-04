package com.respiroc.webapp.controller.web

import com.respiroc.webapp.controller.BaseController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import com.respiroc.project.application.ProjectService
import com.respiroc.project.domain.model.Project

@Controller
@RequestMapping("/projects")
class ProjectController(
    private val projectService: ProjectService
) : BaseController() {
    @GetMapping("/create")
    fun showCreateForm(model: Model): String {
        addCommonAttributesForCurrentTenant(model, "add project")
        model.addAttribute("project", Project())
        return "timesheet/project-create"
    }

    @PostMapping("/create")
    fun createProject(@ModelAttribute project: Project): String {
        projectService.createProject(project)
        return "redirect:/projects/list"
    }

    @GetMapping("/list")
    fun listProjects(model: Model): String {
        addCommonAttributesForCurrentTenant(model, "project list")
        model.addAttribute("projects", projectService.getAllProjects())
        return "timesheet/project-list"
    }
}