package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.detailnews

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.R
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.NewsResponse
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.room.NewsViewModel
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_CONTENT
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_DESCRIPTION
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_IMAGE_URL
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_PUBLICATION_TIME
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_SOURCE
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_TITLE
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.NEWS_URL
import java.util.*

class DetailNewsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var newsWebView: WebView
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: ArrayList<NewsResponse>
    private lateinit var tts: TextToSpeech

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        newsWebView = findViewById(R.id.news_webview)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        newsData = ArrayList(1)
        val newsUrl = intent.getStringExtra(NEWS_URL)
        val newsContent =
            intent.getStringExtra(NEWS_CONTENT) + ". get paid version to hear full news. "
        newsData.add(
            NewsResponse(
                intent.getStringExtra(NEWS_TITLE)!!,
                intent.getStringExtra(NEWS_IMAGE_URL),
                intent.getStringExtra(NEWS_DESCRIPTION),
                newsUrl,
                intent.getStringExtra(NEWS_SOURCE),
                intent.getStringExtra(NEWS_PUBLICATION_TIME),
                newsContent
            )
        )

        newsWebView.apply {
            settings.apply {
                domStorageEnabled = true
                loadsImagesAutomatically = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptEnabled = true
            }
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
        }

        if (newsUrl != null) {
            newsWebView.loadUrl(newsUrl)
        }

        tts = TextToSpeech(this, this)

    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "TTS Not Supported for this news", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.share_news -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this news : " + newsData[0].url)
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share with :"))
                return true
            }

            R.id.save_news -> {
                this.let { viewModel.insertNews(this@DetailNewsActivity, newsData[0]) }
                Toast.makeText(this, "News saved!", Toast.LENGTH_SHORT)
                    .show()
            }

            R.id.browse_news -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsData[0].url))
                startActivity(intent)
            }

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}