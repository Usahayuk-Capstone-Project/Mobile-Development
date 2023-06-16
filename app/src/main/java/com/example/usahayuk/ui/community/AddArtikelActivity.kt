package com.example.usahayuk.ui.community

import android.content.ContentValues
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.usahayuk.Utils
import com.example.usahayuk.data.model.ArticleResponse
import com.example.usahayuk.data.model.PostArticleRequest
import com.example.usahayuk.data.remote.ApiConfig
import com.example.usahayuk.databinding.ActivityAddArtikelBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddArtikelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddArtikelBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnSubmit.setOnClickListener {
                val judul = judulEditText.text.toString().trim()
                val penulis = nameEditText.text.toString().trim()
                val label = labelEditText.text.toString().trim()
                val content = artikelEditText.text.toString().trim()

                // Validate the input values
                if (judul.isEmpty() || penulis.isEmpty() || label.isEmpty() || content.isEmpty()) {
                    Toast.makeText(this@AddArtikelActivity, "Isi semua data artikel yang dibutuhkan", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                postArticle(Utils.EXTRA_UID,penulis, judul, content, label)
            }
        }
    }

    private fun postArticle(
        id: String,
        penulis: String,
        title: String,
        content: String,
        tags: String,
    ) {
        isloading(true)
        val dataRequest = PostArticleRequest(
            penulis,
            title,
            content,
            tags
        )
        val client = ApiConfig.getApiService().postArticle(id, dataRequest)
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    isloading(false)
                    val responseBody = response.body()
                    showSuccessDialog()

                    Log.e(ContentValues.TAG, "onSuccess: ${responseBody?.message}")
                } else {
                    isloading(false)
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
                isloading(false)
            }
        })
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Success")
        builder.setMessage("Data Berhasil Dikirim, Menuju ke halama artikel")
        builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        builder.setCancelable(false)

        val dialog = builder.create()
        dialog.show()
    }

    private fun isloading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
    }


}