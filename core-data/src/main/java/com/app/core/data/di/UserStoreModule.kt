package com.app.core.data.di

import com.app.core.data.preference.UserDataStore
import com.app.core.data.preference.UserPreference
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserStoreModule {

    @Binds
    fun bindUserDataStore(
        userDataStore: UserDataStore,
    ): UserPreference

}