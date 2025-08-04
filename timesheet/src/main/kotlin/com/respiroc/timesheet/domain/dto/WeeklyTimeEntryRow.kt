package com.respiroc.timesheet.domain.dto

data class WeeklyTimeEntryRow(
    var projectId: Long? = null,
    var activity: String = "",
    var comment: String? = "",
    var dailyHours: MutableMap<String, Double> = mutableMapOf(
        "Mon" to 0.0, "Tue" to 0.0, "Wed" to 0.0, "Thu" to 0.0, "Fri" to 0.0, "Sat" to 0.0, "Sun" to 0.0
    ),
    var dailyActivities: MutableMap<String, String> = mutableMapOf(
        "Mon" to "", "Tue" to "", "Wed" to "", "Thu" to "", "Fri" to "", "Sat" to "", "Sun" to ""
    ),
    var dailyComments: MutableMap<String, String> = mutableMapOf(
        "Mon" to "", "Tue" to "", "Wed" to "", "Thu" to "", "Fri" to "", "Sat" to "", "Sun" to ""
    )

)
