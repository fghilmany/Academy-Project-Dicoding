package com.fghilmany.academy2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.fghilmany.academy2.data.AcademyRepository
import com.fghilmany.academy2.data.source.local.entity.CourseWithModule
import com.fghilmany.academy2.vo.Resource

class DetailCourseViewModel(private val academyRepository: AcademyRepository): ViewModel() {
    val courseId = MutableLiveData<String>()

    fun selectedCourse(courseId: String){
        this.courseId.value = courseId
    }

    var courseModule: LiveData<Resource<CourseWithModule>> = Transformations.switchMap(courseId) { mCourseId ->
        academyRepository.getCourseWithModules(mCourseId)
    }

    fun setBookmark() {
        val moduleResource = courseModule.value
        if (moduleResource != null) {
            val courseWithModule = moduleResource.data
            if (courseWithModule != null) {
                val courseEntity = courseWithModule.mCourse
                val newState = !courseEntity.bookmarked
                academyRepository.setCourseBookmark(courseEntity, newState)
            }
        }
    }
}