package com.fghilmany.academy2.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fghilmany.academy2.data.source.AcademyRepository
import com.fghilmany.academy2.data.source.local.entity.CourseEntity
import com.fghilmany.academy2.data.source.local.entity.ModuleEntity
import com.fghilmany.academy2.utils.DataDummy

class DetailCourseViewModel(private val academyRepository: AcademyRepository): ViewModel() {
    private lateinit var courseId : String

    fun selectedCourse(courseId: String){
        this.courseId = courseId
    }

    fun getCourse(): LiveData<CourseEntity> = academyRepository.getCourseWithModules(courseId)

    fun getModules(): LiveData<List<ModuleEntity>> = academyRepository.getAllModulesByCourse(courseId)
}