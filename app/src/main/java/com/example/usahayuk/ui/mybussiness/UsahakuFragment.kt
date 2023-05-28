package com.example.usahayuk.ui.mybussiness

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.usahayuk.R
import com.example.usahayuk.databinding.FragmentUsahakuBinding
import com.example.usahayuk.ui.FutureFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UsahakuFragment : Fragment() {
    private var _binding: FragmentUsahakuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUsahakuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val destinationFragment = FutureFragment()
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding.btnInput.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, destinationFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.btnTingkatkan.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, destinationFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.btnDetailUsaha.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, destinationFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}