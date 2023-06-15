package com.example.usahayuk.ui.community

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usahayuk.Utils
import com.example.usahayuk.data.model.ArticleDataResponse
import com.example.usahayuk.databinding.FragmentKomunitasBinding

class KomunitasFragment : Fragment() {

    private var _binding: FragmentKomunitasBinding? = null
    private val binding get() = _binding!!
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articleAdapter: ArticleAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKomunitasBinding.inflate(inflater, container, false)
        val root : View = binding.root

        articleAdapter = ArticleAdapter()
        articleAdapter.SetOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun ItemClicked(user: ArticleDataResponse) {
                Utils.EXTRA_ARTICLE_AUTHOR = user.penulis
                Utils.EXTRA_TAGS = user.tags
                Utils.EXTRA_UID = user.uid
                Utils.EXTRA_ARTICLE_TITLE = user.title
                Utils.EXTRA_ARTICLE_CONTENT = user.content
                val intent = Intent(activity, DetailArticleActivity::class.java)
                startActivity(intent)
            }
        })

        binding.rvArticle.layoutManager = LinearLayoutManager(activity)
        binding.rvArticle.adapter = articleAdapter

        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
        articleViewModel.getListArticle(Utils.EXTRA_UID)

        articleViewModel.setArticle().observe(viewLifecycleOwner) {
            if (it != null) {
                articleAdapter.setList(it)
            }
        }

        binding.floatingButton.setOnClickListener {
            val intent = Intent(activity, AddArtikelActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}