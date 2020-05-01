package edu.nitt.delta.core.storage

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule() {
  @Provides
  @Singleton
  fun provideDb(app: Application): FestDb {
    return Room.databaseBuilder(app, FestDb::class.java, FestDbUtils.DATABASE_NAME).build()
  }

  @Provides
  @Singleton
  fun provideDao(db: FestDb): EventsDao = db.festDatabaseDao()

  @Provides
  @Singleton
  fun provideSharedPrefHelper(app: Application): SharedPrefHelper {
    return SharedPrefHelper(app)
  }
}
