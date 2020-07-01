package com.fghilmany.academy2.di

import android.content.Context
import com.fghilmany.academy2.data.AcademyRepository
import com.fghilmany.academy2.data.source.local.LocalDataSource
import com.fghilmany.academy2.data.source.local.room.AcademyDatabase
import com.fghilmany.academy2.data.source.remote.RemoteDataSource
import com.fghilmany.academy2.utils.AppExecutors
import com.fghilmany.academy2.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository {

        val database = AcademyDatabase.getInstance(context)

        val remoteDatasource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.academyDao())
        val appExecutors = AppExecutors()

        return AcademyRepository.getInstance(remoteDatasource, localDataSource, appExecutors)
    }
}