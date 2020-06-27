package com.fghilmany.academy2.di

import android.content.Context
import com.fghilmany.academy2.data.source.AcademyRepository
import com.fghilmany.academy2.data.source.remote.RemoteDataSource
import com.fghilmany.academy2.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): AcademyRepository{

        val remoteDatasource = RemoteDataSource.getInstance(JsonHelper(context))

        return AcademyRepository.getInstance(remoteDatasource)
    }
}