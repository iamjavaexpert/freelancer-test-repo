package com.respiroc.timesheet.domain.repository

import com.respiroc.timesheet.domain.model.TimeEntry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface TimeEntryRepository : JpaRepository<TimeEntry, Long> {
    fun findByEmployeeIdAndEntryDateBetween(employeeId: Long, start: LocalDate, end: LocalDate): List<TimeEntry>

    @Query(
        """
    SELECT t FROM TimeEntry t
    WHERE t.employee.id = :employeeId
      AND t.entryDate BETWEEN :start AND :end
    """
    )
    fun findByEmployeeAndWeek(
        @Param("employeeId") employeeId: Long,
        @Param("start") start: LocalDate,
        @Param("end") end: LocalDate
    ): List<TimeEntry>

}
