package com.example.poi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.util.ArrayList
import org.json.JSONArray
import org.json.JSONException

class ListActivity : AppCompatActivity() {

    private lateinit var mSites: ArrayList<Site>
    private lateinit var mAdapter: SiteAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        recycler = findViewById(R.id.site_list)
        setupRecyclerView()
        initDataFromFile()
//        msites = createMocksites()
    }

    /**
     * Sets up the RecyclerView: empty data set, item dividers, swipe to delete.
     */
    private fun setupRecyclerView() {
        mSites = arrayListOf()
        recycler.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        mAdapter = SitesAdapter(mSites, this) { site ->
            siteOnClick(site)
        }

        recycler.adapter = mAdapter
    }

    /* RecyclerView item is clicked. */
    private fun siteOnClick(site: Site?) {
        Log.d(TAG, "Click on: $site")
        site?.let {
            navigateToDetail(it)
        }
    }

    private fun navigateToDetail(site: Site) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(KEY_NAME, site.firstName)
            putExtra(KEY_LAST_NAME, site.lastName)
            putExtra(KEY_CONTACT, site)
        }

        startActivity(intent)
    }

    /**
     * Generates mock site data to populate the UI from a JSON file in the
     * assets directory, called from the options menu.
     */
    private fun initDataFromFile() {
        val sitesString = readSiteJsonFile()
        try {
            val sitesJson = JSONArray(sitesString)
            for (i in 0 until sitesJson.length()) {
                val siteJson = sitesJson.getJSONObject(i)
                val site = Site(
                    siteJson.getString("name"),
                    siteJson.getString("description"),
                    siteJson.getString("score"),
                    siteJson.getString("imageUrl")
                )
                Log.d(TAG, "generatesites: $site")
                msites.add(site)
            }

            mAdapter.notifyDataSetChanged()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * Reads a file from the assets directory and returns it as a string.
     *
     * @return The resulting string.
     */
    private fun readSiteJsonFile(): String? {
        var sitesString: String? = null
        try {
            val inputStream = assets.open("mock_sites.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            sitesString = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return sitesString
    }

    private fun createMocksites(): ArrayList<Site> {
        return arrayListOf(
            Site("Hector", "Cortez", "hectorc@gmail.com", ""),
            Site("Johana", "Mafla", "johanam@gmail.com", ""),
            Site("Jose", "Perez", "josep@gmail.com", ""),
            Site("Juan", "Londoño", "juanl@gmail.com", "")
        )
    }

    companion object {
        private val TAG = ListActivity::class.java.simpleName
        const val KEY_NAME = "site_extra_name"
        const val KEY_LAST_NAME = "site_extra_last_name"
        const val KEY_CONTACT = "site_extra"
    }
}