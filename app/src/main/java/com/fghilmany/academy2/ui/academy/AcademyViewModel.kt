package com.fghilmany.academy2.ui.academy

import androidx.lifecycle.ViewModel
import com.fghilmany.academy2.data.CourseEntity
import com.fghilmany.academy2.utils.DataDummy

class AcademyViewModel : ViewModel(){
    fun getCourse(): List<CourseEntity> = DataDummy.generateDummyCourses()
}