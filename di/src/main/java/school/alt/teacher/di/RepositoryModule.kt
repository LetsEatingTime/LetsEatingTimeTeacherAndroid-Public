package school.alt.teacher.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import school.alt.teacher.network.account.AccountRepository
import school.alt.teacher.network.account.AccountRepositoryImpl
import school.alt.teacher.network.open.OpenRepository
import school.alt.teacher.network.open.OpenRepositoryImpl
import school.alt.teacher.network.store.StoreRepository
import school.alt.teacher.network.store.StoreRepositoryImpl
import school.alt.teacher.network.teacher.TeacherRepository
import school.alt.teacher.network.teacher.TeacherRepositoryImpl
import school.alt.teacher.network.user.UserRepository
import school.alt.teacher.network.user.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository =
        accountRepositoryImpl

    @Provides
    @Singleton
    fun provideOpenRepository(openRepositoryImpl: OpenRepositoryImpl): OpenRepository =
        openRepositoryImpl

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl

    @Provides
    @Singleton
    fun provideStoreRepository(storeRepositoryImpl: StoreRepositoryImpl): StoreRepository =
        storeRepositoryImpl

    @Provides
    @Singleton
    fun provideTeacherRepository(teacherRepositoryImpl: TeacherRepositoryImpl): TeacherRepository =
        teacherRepositoryImpl
}