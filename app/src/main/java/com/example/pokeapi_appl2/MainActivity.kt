package com.example.pokeapi_appl2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException
import java.io.InputStream
import java.net.URL

class MainActivity : ComponentActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var idTextView: TextView
    private lateinit var pokemonImageView: ImageView
    private var client = AsyncHttpClient()
    private val apiUrl = "https://pokeapi.co/api/v2/pokemon/"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var pokemonList: MutableList<Pokemon> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameTextView = findViewById(R.id.nameTextView)
        idTextView = findViewById(R.id.idTextView)
        pokemonImageView = findViewById(R.id.pokemonImageView)

        viewAdapter = PokemonAdapter(pokemonList)
        viewManager = LinearLayoutManager(this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        fetchPokemonData()
    }

    private fun fetchPokemonData() {
        for (i in 1..50) {
            val randomId = (1..151).random()
            client.get(apiUrl + randomId, object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON?) {
                    try {
                        val response = json?.jsonObject
                        val name = response?.getString("name")
                        val id = response?.getInt("id")
                        val imageUrl = response?.getJSONObject("sprites")?.getString("front_default")
                        val type = response?.getJSONArray("types")?.getJSONObject(0)?.getJSONObject("type")?.getString("name")

                        val pokemon = Pokemon(name, id, imageUrl, type)
                        pokemonList.add(pokemon)
                        viewAdapter.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(statusCode: Int, headers: Headers?, response: String, throwable: Throwable?) {
                    runOnUiThread {
                        nameTextView.text = "Not found"
                        idTextView.text = "Not found"
                    }
                }
            })
        }
    }

    class DownloadImageTask(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageUrl = urls[0]
            var bitmap: Bitmap? = null
            try {
                val inputStream: InputStream = URL(imageUrl).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                imageView.setImageBitmap(result)
            }
        }
    }

    data class Pokemon(
        val name: String?,
        val id: Int?,
        val imageUrl: String?,
        val type: String?
    )
}
