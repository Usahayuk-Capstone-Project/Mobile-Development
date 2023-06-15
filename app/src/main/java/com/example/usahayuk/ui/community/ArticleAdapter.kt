package com.example.usahayuk.ui.community

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.usahayuk.R
import com.example.usahayuk.data.model.ArticleDataResponse
import com.example.usahayuk.databinding.ItemKomunitasBinding

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val listArticle = ArrayList<ArticleDataResponse>()
    private var ItemClickCallback: OnItemClickCallback? = null

    fun SetOnItemClickCallback(ItemClickCallback: OnItemClickCallback) {
        this.ItemClickCallback = ItemClickCallback
    }

    inner class ArticleViewHolder(private var binding: ItemKomunitasBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.bacaBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, DetailArticleActivity::class.java)
                    intent.putExtra("article", listArticle[position])
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bindingView(user: ArticleDataResponse) {
            binding.root.setOnClickListener {
                ItemClickCallback?.ItemClicked(user)
            }

            binding.apply {
                artikTitle.text = StringBuilder(user.title)
                artikAuthor.text = StringBuilder(user.penulis)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listArticleNew: ArticleDataResponse) {
        listArticle.clear()
        listArticle.addAll(listOf(listArticleNew))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemKomunitasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindingView(listArticle[position])
    }

    override fun getItemCount(): Int = listArticle.size

    interface OnItemClickCallback {
        fun ItemClicked(user: ArticleDataResponse)
    }
}