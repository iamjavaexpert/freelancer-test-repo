package com.respiroc.timesheet.domain.model

import com.respiroc.employee.domain.model.Employee
import com.respiroc.project.domain.model.Project
import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class TimeEntry(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,

    @ManyToOne(fetch = FetchType.EAGER) val employee: Employee,

    @ManyToOne(fetch = FetchType.EAGER) val project: Project,

    val activity: String,
    val comment: String? = null,
    val entryDate: LocalDate,
    val hoursWorked: Double
){
    constructor() : this(0, Employee(), Project(), "", null, LocalDate.now(), 0.0)
}