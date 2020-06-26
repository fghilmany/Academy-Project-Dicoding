package com.fghilmany.academy2.ui.reader

interface CourseReaderCallback {
    fun moveTo(position: Int, moduleId: String)
}