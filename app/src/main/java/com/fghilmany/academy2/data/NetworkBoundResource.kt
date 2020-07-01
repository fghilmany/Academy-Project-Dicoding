package com.fghilmany.academy2.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.fghilmany.academy2.data.source.remote.ApiResponse
import com.fghilmany.academy2.data.source.remote.StatusResponse
import com.fghilmany.academy2.utils.AppExecutors
import com.fghilmany.academy2.vo.Resource

abstract class NetworkBoundResource <ResultType, RequestType>(private val mExecutors: AppExecutors){

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)){
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(dbSource){ newData ->
            result.value = Resource.loading(newData)
        }

        result.addSource(apiResponse){ response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            Log.e("CEK_response_status2222","${response.status}")
            when(response.status){
                StatusResponse.SUCCESS ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.body)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()){newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }

                StatusResponse.EMPTY ->
                    mExecutors.mainThread().execute {
                        result.addSource(loadFromDB()){ newData ->
                            result.value = Resource.success(newData)
                        }
                    }

                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource){ newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result

    protected open fun onFetchFailed(){}

    protected abstract fun saveCallResult(data: RequestType)

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFromDB(): LiveData<ResultType>
}