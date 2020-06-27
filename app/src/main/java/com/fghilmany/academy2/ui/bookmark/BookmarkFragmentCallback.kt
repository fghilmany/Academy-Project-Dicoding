package com.fghilmany.academy2.ui.bookmark

import com.fghilmany.academy2.data.source.local.entity.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)

}
