package com.example.retrofitdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitdemo.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        retService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)
        //getRequestWithQueryParameters()
        //getRequestWithPathParameters()
        uploadAlbum()
    }

    private fun getRequestWithQueryParameters() {
        val responseAlbumsLiveData: LiveData<Response<Albums>> = liveData {
//            val response = retService.getAlbums()
            val response = retService.getSortedAlbums(3)
            emit(response)
        }

        responseAlbumsLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    val result = " " + "Album id: ${albumsItem.title}\n" +
                            " " + "Album id: ${albumsItem.id}\n" +
                            " " + "Album id: ${albumsItem.userId}\n\n\n"
                    binding.textView.append(result)
                }
            }
        })
    }

    private fun getRequestWithPathParameters() {
        val responseAlbumLiveData: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbum(1)
            emit(response)
        }

        responseAlbumLiveData.observe(this, Observer {
            val album = it.body()
            if (album != null) {
                val result = " " + "Album id: ${album.title}\n" +
                        " " + "Album id: ${album.id}\n" +
                        " " + "Album id: ${album.userId}\n\n\n"
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun uploadAlbum() {
        val album = AlbumsItem(0, "My title", 3)
        val postResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }

        postResponse.observe(this, Observer {
            val receivedAlbumsItem = it.body()
            if (receivedAlbumsItem != null) {
                val result = " " + "Album id: ${receivedAlbumsItem.title}\n" +
                        " " + "Album id: ${receivedAlbumsItem.id}\n" +
                        " " + "Album id: ${receivedAlbumsItem.userId}\n\n\n"

                binding.textView.text = result
            }
        })
    }
}