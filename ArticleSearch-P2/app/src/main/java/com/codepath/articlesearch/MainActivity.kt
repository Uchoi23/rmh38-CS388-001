package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.preference.PreferenceManager
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val ARTICLE_SEARCH_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"

class MainActivity : AppCompatActivity() {
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val articles = mutableListOf<DisplayArticle>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var connectivityManager: ConnectivityManager
    private var searchItem: MenuItem? = null

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            runOnUiThread {
                hideOfflineUI()
                fetchData()
            }
        }

        override fun onLost(network: Network) {
            runOnUiThread {
                showOfflineUI()
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                val shouldCache = sharedPreferences.getBoolean("cache_data", true)

                if (!shouldCache) {
                    clearData()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        articlesRecyclerView = findViewById(R.id.articles)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        val articleAdapter = ArticleAdapter(this, articles)
        articlesRecyclerView.adapter = articleAdapter

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        swipeRefreshLayout.setOnRefreshListener {
            if (connectivityManager.activeNetwork != null) {
                Log.d(TAG, "Swipe refresh triggered. Search view expanded: ${searchItem?.isActionViewExpanded == true}")

                if (searchItem?.isActionViewExpanded == true) {
                    searchItem?.collapseActionView()
                    (searchItem?.actionView as? androidx.appcompat.widget.SearchView)?.apply {
                        isIconified = true
                        setQuery("", false)
                        clearFocus()
                    }
                }

                // Clear data and refresh
                Log.d(TAG, "Clearing data and re-fetching.")
                clearData()
                fetchData()
            } else {
                swipeRefreshLayout.isRefreshing = false
                showOfflineUI()
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        fetchData()

        lifecycleScope.launch {
            (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayArticle(
                        entity.headline,
                        entity.articleAbstract,
                        entity.byline,
                        entity.mediaImageUrl
                    )
                }.also { mappedList ->
                    articles.clear()
                    articles.addAll(mappedList)
                    articleAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        searchItem = menu.findItem(R.id.action_search)

        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        val searchEditText = searchView.findViewById<android.widget.EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    performSearch(it, searchView) // Pass searchView to performSearch
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterLocalData(it)
                }
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchData() {
        swipeRefreshLayout.isRefreshing = true

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val shouldCache = sharedPreferences.getBoolean("cache_data", true)

        val client = AsyncHttpClient()
        client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    parsedJson.response?.docs?.let { list ->
                        if (shouldCache) {
                            lifecycleScope.launch(IO) {
                                (application as ArticleApplication).db.articleDao().deleteAll()
                                (application as ArticleApplication).db.articleDao().insertAll(list.map {
                                    ArticleEntity(
                                        headline = it.headline?.main,
                                        articleAbstract = it.abstract,
                                        byline = it.byline?.original,
                                        mediaImageUrl = it.mediaImageUrl
                                    )
                                })
                            }
                        }

                        articles.clear()
                        list.map { doc ->
                            DisplayArticle(
                                doc.headline?.main,
                                doc.abstract,
                                doc.byline?.original,
                                doc.mediaImageUrl
                            )
                        }.let { newArticles -> articles.addAll(newArticles) }
                        articlesRecyclerView.adapter?.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }

                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun clearData() {
        articles.clear()
        articlesRecyclerView.adapter?.notifyDataSetChanged()

        lifecycleScope.launch(IO) {
            (application as ArticleApplication).db.articleDao().deleteAll()
        }
    }

    private fun showOfflineUI() {
        val offlineIndicator = findViewById<TextView>(R.id.offlineIndicator)
        offlineIndicator.text = "You are offline"
        offlineIndicator.setBackgroundColor(getColor(R.color.red))
        offlineIndicator.visibility = View.VISIBLE
    }

    private fun hideOfflineUI() {
        val offlineIndicator = findViewById<TextView>(R.id.offlineIndicator)
        offlineIndicator.text = "Online"
        offlineIndicator.setBackgroundColor(getColor(R.color.teal_700))
        offlineIndicator.visibility = View.VISIBLE

        offlineIndicator.postDelayed({
            offlineIndicator.visibility = View.GONE
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun filterLocalData(query: String) {
        val filteredList = articles.filter {
            it.headline?.contains(query, ignoreCase = true) == true ||
                    it.abstract?.contains(query, ignoreCase = true) == true
        }
        articlesRecyclerView.adapter = ArticleAdapter(this, filteredList)
        articlesRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun performSearch(query: String, searchView: androidx.appcompat.widget.SearchView) {
        val searchUrl = "$ARTICLE_SEARCH_URL&q=$query"
        val client = AsyncHttpClient()

        client.get(searchUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val parsedJson = createJson().decodeFromString(
                    SearchNewsResponse.serializer(),
                    json.jsonObject.toString()
                )
                parsedJson.response?.docs?.let { list ->
                    articles.clear()
                    articles.addAll(list.map { doc ->
                        DisplayArticle(
                            headline = doc.headline?.main,
                            abstract = doc.abstract,
                            byline = doc.byline?.original,
                            mediaImageUrl = doc.mediaImageUrl
                        )
                    })
                    articlesRecyclerView.adapter?.notifyDataSetChanged()
                }
                searchView.setQuery("", false)  // Clear the search input field
                searchView.clearFocus()         // Remove focus from the search input
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e(TAG, "Search API call failed: $statusCode")
            }
        })
    }
}
