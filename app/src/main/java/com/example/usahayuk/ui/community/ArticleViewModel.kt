package com.example.usahayuk.ui.community

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.usahayuk.data.Repository
import com.example.usahayuk.data.model.ArticleDataResponse

class ArticleViewModel(application: Application) : AndroidViewModel(application){

    private val mRepository: Repository = Repository(application)

    fun getListArticle(id: String) = mRepository.getArticle(id)
    fun setArticle(): MutableLiveData<ArticleDataResponse> { return mRepository.getNewArticle() }

}