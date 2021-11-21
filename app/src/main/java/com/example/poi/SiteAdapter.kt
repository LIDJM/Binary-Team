package com.example.poi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.ArrayList


class SiteAdapter(
    private val mSites: ArrayList<Site>,
    private val context: Context,
    private val onClick: (Site?) -> Unit
) : RecyclerView.Adapter<SiteAdapter.SiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_poi, parent, false)
        return SiteViewHolder(view)
    }

    override fun onBindViewHolder(holderSite: SiteViewHolder, position: Int) {
        val site = mSites[position]
        holderSite.bind(site = site)
    }

    override fun getItemCount(): Int {
        return mSites.size
    }

    inner class SiteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nameLabel: TextView = itemView.findViewById(R.id.textview_name)
        private var emailLabel: TextView = itemView.findViewById(R.id.textview_email)
        private var imageView: ImageView = itemView.findViewById(R.id.imageview_thumb)
        private var currentSite: Site? = null

        init {
            itemView.setOnClickListener {
                Log.d(TAG, "itemView OnClick")
                onClick(currentSite)
            }
        }

        /* Bind Site name and image. */
        fun bind(site: Site) {
            currentSite = site

            val fullName = "${site.firstName} ${site.lastName}"
            nameLabel.text = fullName
            emailLabel.text = site.email

            Glide.with(context)
                .load(site.imageUrl)
                .into(imageView)
        }
    }

    companion object{
        private const val TAG = "SiteAdapter"
    }
}