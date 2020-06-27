package com.fghilmany.academy2.ui.bookmark

import androidx.lifecycle.ViewModel
import com.fghilmany.academy2.data.source.AcademyRepository
import com.fghilmany.academy2.data.source.local.entity.CourseEntity
import com.fghilmany.academy2.utils.DataDummy

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getBookmarks(): List<CourseEntity> = academyRepository.getBookmarkedCourses()
}