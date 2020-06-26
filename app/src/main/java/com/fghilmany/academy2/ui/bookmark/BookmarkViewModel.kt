package com.fghilmany.academy2.ui.bookmark

import androidx.lifecycle.ViewModel
import com.fghilmany.academy2.data.CourseEntity
import com.fghilmany.academy2.utils.DataDummy

class BookmarkViewModel : ViewModel() {
    fun getBookmarks(): List<CourseEntity> = DataDummy.generateDummyCourses()
}