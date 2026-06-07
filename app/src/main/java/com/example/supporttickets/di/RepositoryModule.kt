package com.example.supporttickets.di

import com.example.supporttickets.core.datastore.TokenDataStore
import com.example.supporttickets.data.remote.api.*
import com.example.supporttickets.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        tokenDataStore: TokenDataStore
    ): AuthRepository = AuthRepository(authApi, tokenDataStore)

    @Provides
    @Singleton
    fun provideTicketRepository(
        ticketApi: TicketApi
    ): TicketRepository = TicketRepository(ticketApi)

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryApi: CategoryApi
    ): CategoryRepository = CategoryRepository(categoryApi)

    @Provides
    @Singleton
    fun providePriorityRepository(
        priorityApi: PriorityApi
    ): PriorityRepository = PriorityRepository(priorityApi)

    @Provides
    @Singleton
    fun provideCommentRepository(
        commentApi: CommentApi
    ): CommentRepository = CommentRepository(commentApi)

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi
    ): UserRepository = UserRepository(userApi)
}
