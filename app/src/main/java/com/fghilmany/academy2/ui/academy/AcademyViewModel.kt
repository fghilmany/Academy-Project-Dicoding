package com.fghilmany.academy2.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fghilmany.academy2.data.AcademyRepository
import com.fghilmany.academy2.data.source.local.entity.CourseEntity
import com.fghilmany.academy2.vo.Resource

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel(){
    fun getCourse(): LiveData<Resource<List<CourseEntity>>> = academyRepository.getAllCourses()
}