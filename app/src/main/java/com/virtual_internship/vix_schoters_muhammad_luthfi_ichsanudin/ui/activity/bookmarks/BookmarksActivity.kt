package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.bookmarks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.R
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.NewsAdapter
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.news.NewsResponse
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.room.NewsViewModel
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.detailnews.DetailNewsActivity
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_CONTENT
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_DESCRIPTION
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_IMAGE_URL
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_PUBLICATION_TIME
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_SOURCE
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_TITLE
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_URL

class BookmarksActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: MutableList<NewsResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        newsData = mutableListOf()

        val adapter = NewsAdapter(newsData)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        viewModel.getNewsFromDB(context = applicationContext)?.observe(this) {
            newsData.clear()
            newsData.addAll(it)
            adapter.notifyDataSetChanged()
        }

        adapter.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(this@BookmarksActivity, DetailNewsActivity::class.java).apply {
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

        adapter.setOnItemLongClickListener(object : NewsAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) {
                // Delete saved news dialog
                recyclerView.findViewHolderForAdapterPosition(position)?.itemView?.setBackgroundColor(
                    getThemeColor(com.google.android.material.R.attr.colorPrimaryVariant)
                )

                val alertDialog = AlertDialog.Builder(this@BookmarksActivity).apply {
                    setMessage("Delete this News?")
                    setTitle("Alert!")
                    setCancelable(false)

                    setPositiveButton(
                        "Yes"
                    ) { _, _ ->
                        this@BookmarksActivity.let {
                            viewModel.deleteNews(
                                it,
                                news = newsData[position]
                            )
                        }
                        adapter.notifyItemRemoved(position)
                        Toast.makeText(this@BookmarksActivity, "Deleted!", Toast.LENGTH_SHORT).show()
                    }

                    setNegativeButton("No") { _, _ ->
                        recyclerView.findViewHolderForAdapterPosition(position)?.itemView?.setBackgroundColor(
                            getThemeColor(com.google.android.material.R.attr.colorPrimary)
                        )
                    }
                }.create()

                alertDialog.show()
            }
        })

        recyclerView.adapter = adapter

    }

    @ColorInt
    fun Context.getThemeColor(@AttrRes attribute: Int) = TypedValue().let {
        theme.resolveAttribute(attribute, it, true)
        it.data
    }

}