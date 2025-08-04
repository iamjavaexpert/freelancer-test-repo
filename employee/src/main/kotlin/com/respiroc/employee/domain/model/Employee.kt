package com.respiroc.employee.domain.model

import jakarta.persistence.*
import com.respiroc.project.domain.model.Project

@Entity
data class Employee(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
    val name: String,
    val email: String,
    val role: String,

    @ManyToMany(fetch = FetchType.EAGER) @JoinTable(
        name = "employee_project",
        joinColumns = [JoinColumn(name = "employee_id")],
        inverseJoinColumns = [JoinColumn(name = "project_id")]
    ) val projects: MutableSet<Project> = mutableSetOf()
){
    constructor() : this(0, "", "", "", mutableSetOf())
}