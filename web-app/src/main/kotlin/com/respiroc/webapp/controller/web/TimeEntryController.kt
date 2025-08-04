package com.respiroc.webapp.controller.web

import com.respiroc.project.domain.model.Project
import com.respiroc.timesheet.application.TimeEntryService
import com.respiroc.timesheet.domain.dto.WeeklyTimeEntryForm
import com.respiroc.timesheet.domain.dto.WeeklyTimeEntryRow
import com.respiroc.webapp.controller.BaseController
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth


@Controller
@RequestMapping("/timeentries")
class TimeEntryController(
    private val timeEntryService: TimeEntryService
) : BaseController() {

    @GetMapping("/weekly")
    fun showWeeklyForm(
        @RequestParam(required = false) employeeId: Long?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) weekStartDate: LocalDate?,
        model: Model
    ): String {
        addCommonAttributesForCurrentTenant(model, "add weekly timeentries ")
        val employees = timeEntryService.getAllEmployees()
        model.addAttribute("employees", employees)

        val monday = (weekStartDate ?: LocalDate.now()).with(DayOfWeek.MONDAY)

        val form = WeeklyTimeEntryForm().apply {
            this.employeeId = employeeId
            this.weekStartDate = monday
            if (employeeId != null) {
                val employee = timeEntryService.getEmployeeById(employeeId)
                val projects: Set<Project> = employee?.projects ?: emptySet()
                this.rows = projects.map { WeeklyTimeEntryRow(projectId = it.id) }.toMutableList()
            }
        }

        val dayHeaders = DayOfWeek.values().associate { day ->
            day.name.substring(0, 3).replaceFirstChar { it.uppercase() } to monday.with(day)
        }

        model.addAttribute("form", form)
        model.addAttribute("dayHeaders", dayHeaders)
        model.addAttribute("projectMap", timeEntryService.getAllProjectsMap())
        return "timesheet/weekly-time-entry"
    }

    @PostMapping("/saveWeekly")
    fun saveWeeklyEntries(
        @ModelAttribute form: WeeklyTimeEntryForm,
        redirectAttributes: RedirectAttributes
    ): String {
        timeEntryService.saveWeeklyEntries(form)
        redirectAttributes.addFlashAttribute("success", "Time entries saved successfully.")
        return "redirect:/timeentries/weekly?employeeId=${form.employeeId}"
    }

    @GetMapping("/monthly")
    fun showMonthlyReportForm(model: Model): String {
        addCommonAttributesForCurrentTenant(model, "monthly")
        model.addAttribute("employees", timeEntryService.getAllEmployees())
        model.addAttribute("selectedMonth", YearMonth.now())
        return "timesheet/monthly-report"
    }

    @GetMapping("/monthly/report")
    fun showMonthlyReport(
        @RequestParam employeeId: Long,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM") selectedMonth: YearMonth,
        model: Model
    ): String {
        addCommonAttributesForCurrentTenant(model, "monthly report")
        val employee = timeEntryService.getEmployeeById(employeeId)
        val employees = timeEntryService.getAllEmployees()
        val timeEntries = timeEntryService.getTimeEntriesForMonth(employeeId, selectedMonth)

        model.addAttribute("employee", employee)
        model.addAttribute("selectedMonth", selectedMonth)
        model.addAttribute("timeEntries", timeEntries)
        model.addAttribute("employees", employees)
        model.addAttribute("selectedEmployeeId", employeeId)

        return "timesheet/monthly-report"
    }
}
