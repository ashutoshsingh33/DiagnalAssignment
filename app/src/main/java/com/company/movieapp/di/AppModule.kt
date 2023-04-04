package com.company.movieapp.di

import android.content.Context
import com.company.movieapp.util.DataUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDataUtil(@ApplicationContext context: Context) = DataUtil(context)
}