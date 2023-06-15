package com.example.usahayuk.ui.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.usahayuk.R
import com.example.usahayuk.data.model.ArticleDataResponse
import com.example.usahayuk.databinding.ActivityDetailArticleBinding

@Suppress("DEPRECATION")
class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        val article = intent.getParcelableExtra<ArticleDataResponse>("article")

        // Use the retrieved data to populate the views
        binding.txtJudulArtcl.text = article?.title
        binding.label.text = article?.tags
        binding.contentTxt.text = article?.content
        binding.penulis.text = article?.penulis

        setContentView(binding.root)
    }
}