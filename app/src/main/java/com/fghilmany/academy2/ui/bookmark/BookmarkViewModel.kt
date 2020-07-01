package com.fghilmany.academy2.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fghilmany.academy2.data.AcademyRepository
import com.fghilmany.academy2.data.source.local.entity.CourseEntity

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getBookmarks(): LiveData<List<CourseEntity>> {
        return academyRepository.getBookmarkedCourses()
    }
}