package space.mrandika.wisnu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import space.mrandika.wisnu.config.WisnuAPIConfig
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WisnuAPIModule {

    @Provides
    @Singleton
    fun provideWisnuAPI(): WisnuAPIService = WisnuAPIConfig.getApiService()
}