package com.example.usahayuk.ui.home

import RecommendationAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usahayuk.Utils
import com.example.usahayuk.databinding.ActivityRecommendationBinding
import com.example.usahayuk.ui.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Suppress("DEPRECATION")
class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationBinding
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var recommendationViewModel: RecommendationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.rvRecommendation.layoutManager = LinearLayoutManager(this)

        recommendationAdapter = RecommendationAdapter(emptyList())
        binding.rvRecommendation.adapter = recommendationAdapter

        recommendationViewModel = ViewModelProvider(this)[RecommendationViewModel::class.java]

        val userId = Utils.EXTRA_UID

        recommendationViewModel.recommendationLiveData.observe(this) { recommendations ->
            recommendationAdapter.setData(recommendations)
        }

        recommendationViewModel.getRecommendations(userId)

        binding.btnBackToForm.setOnClickListener{
            finish()
        }

        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this@RecommendationActivity, MainActivity::class.java)
            intent.putExtra("showHomeFragment", true)
            startActivity(intent)
            finish()
        }
    }
}