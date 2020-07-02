package com.fghilmany.academy2.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.fghilmany.academy2.data.AcademyRepository
import com.fghilmany.academy2.data.source.local.entity.CourseEntity

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getBookmarks(): LiveData<PagedList<CourseEntity>> {
        return academyRepository.getBookmarkedCourses()
    }

    fun setBookmark(courseEntity: CourseEntity){
        val newState = !courseEntity.bookmarked
        academyRepository.setCourseBookmark(courseEntity, newState)
    }
}