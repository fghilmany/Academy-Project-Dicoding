package com.fghilmany.academy2.data

import androidx.lifecycle.LiveData
import com.fghilmany.academy2.data.source.local.LocalDataSource
import com.fghilmany.academy2.data.source.local.entity.CourseEntity
import com.fghilmany.academy2.data.source.local.entity.CourseWithModule
import com.fghilmany.academy2.data.source.local.entity.ModuleEntity
import com.fghilmany.academy2.data.source.remote.ApiResponse
import com.fghilmany.academy2.data.source.remote.RemoteDataSource
import com.fghilmany.academy2.data.source.remote.response.ContentResponse
import com.fghilmany.academy2.data.source.remote.response.CourseResponse
import com.fghilmany.academy2.data.source.remote.response.ModuleResponse
import com.fghilmany.academy2.utils.AppExecutors
import com.fghilmany.academy2.vo.Resource

class AcademyRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors):
    AcademyDataSource {

    companion object{
        @Volatile
        private var instance: AcademyRepository? = null

        fun getInstance(remoteDatasource: RemoteDataSource, localDataSource: LocalDataSource, appExecutors: AppExecutors): AcademyRepository =
            instance ?: synchronized(this) {
                instance
                    ?: AcademyRepository(
                        remoteDatasource, localDataSource, appExecutors
                    )
            }
    }

    override fun getAllCourses(): LiveData<Resource<List<CourseEntity>>>{
        return object : NetworkBoundResource<List<CourseEntity>, List<CourseResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<List<CourseEntity>> =
                localDataSource.getAllCourse()


            override fun saveCallResult(data: List<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for (response in data) {
                    val course = CourseEntity(response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath)
                    courseList.add(course)
                }

                localDataSource.insertCourse(courseList)
            }

            override fun createCall(): LiveData<ApiResponse<List<CourseResponse>>> =
                remoteDataSource.getAllCourses()


            override fun shouldFetch(data: List<CourseEntity>?): Boolean =
                data == null || data.isEmpty()
        }.asLiveData()
    }

    override fun getBookmarkedCourses(): LiveData<List<CourseEntity>> =
        localDataSource.getBookmarked()

    override fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>> {
        return object : NetworkBoundResource<CourseWithModule, List<ModuleResponse>>(appExecutors) {
            override fun saveCallResult(data: List<ModuleResponse>) {
                val moduleList = ArrayList<ModuleEntity>()
                for (response in data) {
                    val course = ModuleEntity(response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false)

                    moduleList.add(course)
                }

                localDataSource.insertModules(moduleList)
            }

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>> =
                remoteDataSource.getModules(courseId)

            override fun shouldFetch(data: CourseWithModule?): Boolean =
                data?.mModule == null || data.mModule.isEmpty()

            override fun loadFromDB(): LiveData<CourseWithModule> =
                localDataSource.getCourseWithModules(courseId)
        }.asLiveData()

    }

    override fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>> {
        return object : NetworkBoundResource<List<ModuleEntity>, List<ModuleResponse>>(appExecutors){
            override fun saveCallResult(data: List<ModuleResponse>) {
                val moduleList = ArrayList<ModuleEntity>()
                for(response in data) {
                    val course = ModuleEntity(response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false)
                    moduleList.add(course)
                }

                localDataSource.insertModules(moduleList)
            }

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>> =
                remoteDataSource.getModules(courseId)

            override fun shouldFetch(data: List<ModuleEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun loadFromDB(): LiveData<List<ModuleEntity>> =
                localDataSource.getAllModulesByCourse(courseId)

        }.asLiveData()
    }


    override fun getContent( moduleId: String): LiveData<Resource<ModuleEntity>> {
        return object : NetworkBoundResource<ModuleEntity, ContentResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<ModuleEntity> =
                localDataSource.getModuleWithContent(moduleId)
            override fun shouldFetch(data: ModuleEntity?): Boolean =
                data?.contentEntity == null
            override fun createCall(): LiveData<ApiResponse<ContentResponse>> =
                remoteDataSource.getContent(moduleId)
            override fun saveCallResult(data: ContentResponse) =
                localDataSource.updateContent(data.content, moduleId)
        }.asLiveData()
    }

    override fun setCourseBookmark(course: CourseEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setCourseBookmark(course, state) }
    }

    override fun setReadModule(module: ModuleEntity) {
        appExecutors.diskIO().execute { localDataSource.setReadModule(module) }
    }
}