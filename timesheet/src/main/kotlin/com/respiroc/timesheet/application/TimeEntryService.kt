package com.respiroc.timesheet.application

import com.respiroc.employee.domain.repository.EmployeeRepository
import com.respiroc.project.domain.repository.ProjectRepository
import com.respiroc.timesheet.domain.dto.WeeklyTimeEntryForm
import com.respiroc.timesheet.domain.model.TimeEntry
import com.respiroc.timesheet.domain.repository.TimeEntryRepository
import org.springframework.stereotype.Service
import java.time.YearMonth
import kotlin.collections.forEach


@Service
class TimeEntryService(
    private val employeeRepo: EmployeeRepository,
    private val projectRepo: ProjectRepository,
    private val timeEntryRepo: TimeEntryRepository
) {

    fun getAllEmployees() = employeeRepo.findAll()

    fun getEmployeeById(id: Long) = employeeRepo.findById(id).orElse(null)

    fun getAllProjectsMap(): Map<Long, String> =
        projectRepo.findAll().associateBy({ it.id }, { it.name })

    fun saveWeeklyEntries(form: WeeklyTimeEntryForm) {
        val employee = employeeRepo.findById(form.employeeId!!).orElseThrow()
        val weekStart = form.weekStartDate!!

        form.rows.forEach { row ->
            val project = projectRepo.findById(row.projectId!!).orElseThrow()
            row.dailyHours.forEach { (day, hours) ->
                if (hours > 0) {
                    val entryDate = weekStart.plusDays(dayIndex(day))
                    val activity = row.dailyActivities[day] ?: row.activity
                    val comment = row.dailyComments[day] ?: row.comment

                    val entry = TimeEntry(
                        employee = employee,
                        project = project,
                        entryDate = entryDate,
                        hoursWorked = hours,
                        activity = activity,
                        comment = comment
                    )
                    timeEntryRepo.save(entry)
                }
            }
        }
    }

    fun getTimeEntriesForMonth(employeeId: Long, month: YearMonth): List<TimeEntry> {
        val startDate = month.atDay(1)
        val endDate = month.atEndOfMonth()
        return timeEntryRepo.findByEmployeeIdAndEntryDateBetween(employeeId, startDate, endDate)
    }

    private fun dayIndex(day: String): Long = when (day) {
        "Mon" -> 0
        "Tue" -> 1
        "Wed" -> 2
        "Thu" -> 3
        "Fri" -> 4
        "Sat" -> 5
        "Sun" -> 6
        else -> throw IllegalArgumentException("Invalid day: $day")
    }
}
