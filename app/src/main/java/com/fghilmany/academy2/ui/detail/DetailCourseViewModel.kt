package com.fghilmany.academy2.ui.detail

import androidx.lifecycle.ViewModel
import com.fghilmany.academy2.data.CourseEntity
import com.fghilmany.academy2.data.ModuleEntity
import com.fghilmany.academy2.utils.DataDummy

class DetailCourseViewModel: ViewModel() {
    private lateinit var courseId : String

    fun selectedCourse(courseId: String){
        this.courseId = courseId
    }

    fun getCourse(): CourseEntity{
        lateinit var course: CourseEntity
        val courseEntities = DataDummy.generateDummyCourses()
        for (courseEntity in courseEntities){
            if (courseEntity.courseId == courseId){
                course = courseEntity
            }
        }
        return course
    }

    fun getModules(): List<ModuleEntity> = DataDummy.generateDummyModules(courseId)
}