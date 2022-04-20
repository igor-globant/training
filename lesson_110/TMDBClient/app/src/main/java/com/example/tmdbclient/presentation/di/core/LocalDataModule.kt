package com.example.tmdbclient.presentation.di.core

import com.example.tmdbclient.data.db.ArtistDao
import com.example.tmdbclient.data.db.MovieDao
import com.example.tmdbclient.data.db.TvShowDao
import com.example.tmdbclient.data.repository.artist.datasource.ArtistLocalDatasource
import com.example.tmdbclient.data.repository.artist.datasourceimpl.ArtistLocalDatasourceImpl
import com.example.tmdbclient.data.repository.movie.datasource.MovieLocalDatasource
import com.example.tmdbclient.data.repository.movie.datasourceimpl.MovieLocalDatasourceImpl
import com.example.tmdbclient.data.repository.tvshow.datasource.TvShowLocalDatasource
import com.example.tmdbclient.data.repository.tvshow.datasourceimpl.TvShowLocalDatasourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun provideMovieLocalDataSource(movieDao: MovieDao): MovieLocalDatasource {
        return MovieLocalDatasourceImpl(movieDao)
    }

    @Singleton
    @Provides
    fun provideTvShowLocalDataSource(tvShowDao: TvShowDao): TvShowLocalDatasource {
        return TvShowLocalDatasourceImpl(tvShowDao)
    }

    @Singleton
    @Provides
    fun provideArtistLocalDataSource(artistDao: ArtistDao): ArtistLocalDatasource {
        return ArtistLocalDatasourceImpl(artistDao)
    }
}