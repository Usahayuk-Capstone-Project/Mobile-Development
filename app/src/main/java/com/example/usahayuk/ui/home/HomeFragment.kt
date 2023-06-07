package com.example.usahayuk.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.usahayuk.R
import com.example.usahayuk.ViewModelFactory
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.databinding.FragmentHomeBinding
import com.example.usahayuk.ui.MainViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreferences.getInstance(requireContext().dataStore))
        )[MainViewModel::class.java]
        mainViewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                binding.txtWelcome.text = getString(R.string.greeting, user.name)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        _binding?.findRecomendbtn?.setOnClickListener {
            val intent = Intent(this@HomeFragment.context, FormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}