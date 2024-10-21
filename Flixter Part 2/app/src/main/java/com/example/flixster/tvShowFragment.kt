package com.example.flixster
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray

//API KEY HERE
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class tvShowFragment : Fragment(), OnListFragmentInteractionListener{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tv_show_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.showTVList) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        updateAdapter(progressBar, recyclerView)
        return view
    }
    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY
        // Using the client, perform the HTTP request
        client.get("https://api.themoviedb.org/3/tv/popular?language=en-US&page=1", params, object :
            JsonHttpResponseHandler() {
            /*
             * The onSuccess function gets called when
             * HTTP response status is "200 OK"
             */
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                // The wait for a response is over
                progressBar.hide()
                //Get results out of JSON response
                val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray
                //Get shows from those results as a string
                val showsRawJSON : String = resultsJSON.toString()
                val gson = Gson()
                val arrayShowType = object : TypeToken<List<tvShow>>() {}.type
                //TODO - Parse JSON into Models
                val models : List<tvShow> =  gson.fromJson(showsRawJSON, arrayShowType)
                recyclerView.adapter = TVShowFragmentRecyclerViewAdapter(models, this@tvShowFragment)

                Log.d("tvShowFragment", showsRawJSON)
            }

            /*
             * The onFailure function gets called when
             * HTTP response status is "4XX" (eg. 401, 403, 404)
             */
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // The wait for a response is over
                progressBar.hide()

                // If the error is not null, log it!
                t?.message?.let {
                    Log.e("tvShowFragment", errorResponse)
                }
            }
        })
    }
    override fun onItemClick(item: tvShow) {
        TODO("Not yet implemented")
    }
}