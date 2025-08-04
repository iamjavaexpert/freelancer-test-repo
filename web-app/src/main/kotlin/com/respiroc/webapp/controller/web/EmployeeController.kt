package com.respiroc.webapp.controller.web

import com.respiroc.employee.application.EmployeeService
import com.respiroc.employee.domain.dto.CreateEmployeeDTO
import com.respiroc.project.application.ProjectService
import com.respiroc.webapp.controller.BaseController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/employees")
class EmployeeController(
    private val employeeService: EmployeeService,
    private val projectService: ProjectService
) : BaseController() {

    @GetMapping("/create")
    fun showCreateForm(model: Model): String {
        addCommonAttributesForCurrentTenant(model, "add employee")
        model.addAttribute("employee", CreateEmployeeDTO())
        model.addAttribute("allProjects", projectService.getAllProjects())
        return "timesheet/employee-create"
    }

    @PostMapping("/create")
    fun createEmployee(@ModelAttribute employee: CreateEmployeeDTO): String {
        employeeService.createEmployee(employee)
        return "redirect:/employees/list"
    }

    @GetMapping("/list")
    fun showEmployeeList(model: Model): String {
        addCommonAttributesForCurrentTenant(model, "list of employee")
        model.addAttribute("employees", employeeService.getAllEmployees())
        return "timesheet/employee-list"
    }

}