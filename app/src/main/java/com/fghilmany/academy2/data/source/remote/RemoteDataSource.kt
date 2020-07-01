package com.fghilmany.academy2.data.source.remote

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fghilmany.academy2.data.source.remote.response.ContentResponse
import com.fghilmany.academy2.data.source.remote.response.CourseResponse
import com.fghilmany.academy2.data.source.remote.response.ModuleResponse
import com.fghilmany.academy2.utils.JsonHelper


class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){

    private val handler = Handler()

    companion object{
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance : RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getAllCourses(): LiveData<ApiResponse<List<CourseResponse>>> {
        val resultCourse = MutableLiveData<ApiResponse<List<CourseResponse>>>()
        handler.postDelayed({
                resultCourse.value = ApiResponse.success(jsonHelper.loadCourses())
             }, SERVICE_LATENCY_IN_MILLIS)
        return resultCourse
    }

    fun getModules(courseId: String): LiveData<ApiResponse<List<ModuleResponse>>> {
        val resultModules = MutableLiveData<ApiResponse<List<ModuleResponse>>>()
        handler.postDelayed({
            resultModules.value = ApiResponse.success(jsonHelper.loadModule(courseId))
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultModules
    }

    fun getContent(moduleId: String): LiveData<ApiResponse<ContentResponse>> {
        val resultContent = MutableLiveData<ApiResponse<ContentResponse>>()
        handler.postDelayed({
            resultContent.value = ApiResponse.success(jsonHelper.loadContent(moduleId))
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultContent
    }

    interface LoadCoursesCallback {
        fun onAllCoursesReceived(courseResponses: List<CourseResponse>)
    }
    interface LoadModulesCallback {
        fun onAllModulesReceived(moduleResponses: List<ModuleResponse>)
    }
    interface LoadContentCallback {
        fun onContentReceived(contentResponse: ContentResponse)
    }
}
