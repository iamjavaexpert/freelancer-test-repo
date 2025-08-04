package com.respiroc.project.domain.repository

import com.respiroc.project.domain.model.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository : JpaRepository<Project, Long>