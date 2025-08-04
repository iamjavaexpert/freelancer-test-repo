package com.respiroc.timesheet.domain.dto

import java.time.LocalDate

data class WeeklyTimeEntryForm(
    var employeeId: Long? = null,
    var weekStartDate: LocalDate? = null,
    var rows: MutableList<WeeklyTimeEntryRow> = mutableListOf()
)
