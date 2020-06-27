package com.fghilmany.academy2.data.source

import com.fghilmany.academy2.data.source.local.entity.ContentEntity
import com.fghilmany.academy2.data.source.local.entity.CourseEntity
import com.fghilmany.academy2.data.source.local.entity.ModuleEntity

interface AcademyDataSource {

    fun getAllCourses(): List<CourseEntity>

    fun getBookmarkedCourses(): List<CourseEntity>

    fun getCourseWithModules(courseId: String): CourseEntity

    fun getAllModulesByCourse(courseId: String): List<ModuleEntity>

    fun getContent(courseId: String, moduleId: String): ModuleEntity
}