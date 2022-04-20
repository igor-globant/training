package com.example.tmdbclient.data.repository.artist

import android.util.Log
import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.data.repository.artist.datasource.ArtistCacheDatasource
import com.example.tmdbclient.data.repository.artist.datasource.ArtistLocalDatasource
import com.example.tmdbclient.data.repository.artist.datasource.ArtistRemoteDatasource
import com.example.tmdbclient.domain.repository.ArtistRepository

class ArtistRepositoryImpl(
    private val artistRemoteDatasource: ArtistRemoteDatasource,
    private val artistLocalDatasource: ArtistLocalDatasource,
    private val movieCacheDatasource: ArtistCacheDatasource
) : ArtistRepository {

    override suspend fun getArtists(): List<Artist> {
        return getArtistsFromCache()
    }

    override suspend fun updateArtists(): List<Artist> {
        val newListOfArtists = getArtistsFromAPI()
        artistLocalDatasource.clearAll()
        artistLocalDatasource.saveArtistsToDB(newListOfArtists)
        movieCacheDatasource.saveArtistsToCache(newListOfArtists)
        return newListOfArtists
    }

    private suspend fun getArtistsFromAPI(): List<Artist> {
        lateinit var artistList: List<Artist>
        try {
            val response = artistRemoteDatasource.getArtists()
            val body = response.body()
            if (body != null) {
                artistList = body.artists
            }
        } catch (exception: Exception) {
            Log.i("MYTAG", exception.toString())
        }
        return artistList
    }

    private suspend fun getArtistsFromDB(): List<Artist> {
        lateinit var artistList: List<Artist>
        try {
            artistList = artistLocalDatasource.getArtistsFromDB()
        } catch (exception: Exception) {
            Log.i("MYTAG", exception.toString())
        }

        if (artistList.isEmpty()) {
            artistList = getArtistsFromAPI()
        }
        return artistList
    }

    private suspend fun getArtistsFromCache(): List<Artist> {
        lateinit var artistList: List<Artist>
        try {
            artistList = movieCacheDatasource.getArtistsFromCache()
        } catch (exception: Exception) {
            Log.i("MYTAG", exception.toString())
        }

        if (artistList.isEmpty()) {
            artistList = getArtistsFromDB()
            movieCacheDatasource.saveArtistsToCache(artistList)
        }
        return artistList
    }
}