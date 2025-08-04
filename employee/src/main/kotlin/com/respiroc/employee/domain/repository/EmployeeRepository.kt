package com.respiroc.employee.domain.repository

import com.respiroc.employee.domain.model.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long>