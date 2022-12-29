package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.main.MainActivity
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.news.NewsResponse
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.R
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.detailnews.DetailNewsActivity
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.NewsAdapter
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_CONTENT
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_DESCRIPTION
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_IMAGE_URL
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_PUBLICATION_TIME
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_SOURCE
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_TITLE
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_URL

class BusinessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_business, container, false)
        val newsData: MutableList<NewsResponse> = MainActivity.businessNews
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = NewsAdapter(newsData)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {

            override fun onItemClick(position: Int) {
                val intent = Intent(context, DetailNewsActivity::class.java).apply {
                    putExtra(NEWS_URL, newsData[position].url)
                    putExtra(NEWS_TITLE, newsData[position].headLine)
                    putExtra(NEWS_IMAGE_URL, newsData[position].image)
                    putExtra(NEWS_DESCRIPTION, newsData[position].description)
                    putExtra(NEWS_SOURCE, newsData[position].source)
                    putExtra(NEWS_PUBLICATION_TIME, newsData[position].time)
                    putExtra(NEWS_CONTENT, newsData[position].content)
                }

                startActivity(intent)

            }
        })

        // Ignore
        adapter.setOnItemLongClickListener(object : NewsAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })

        return view
    }

}