package com.example.usahayuk.ui.profle
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.usahayuk.Utils
import com.example.usahayuk.databinding.FragmentProfileBinding
import com.example.usahayuk.ui.login.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private lateinit var profileViewModel: ProfileViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        profileViewModel.getUser(Utils.token,Utils.EXTRA_UID)
        profileViewModel.setUser().observe(viewLifecycleOwner){
            if (it != null){
                binding.apply {
                    val name = binding.nameProfile
                    val email = binding.emailProfile

                    name.text = it.user.displayName
                    email.text = it.user.email
                }
            }
        }
        return binding.root
    }

    private fun saveState() {
        val sharedPreference = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor?.apply {
            putString("STRING_KEY", "logout")
        }?.apply()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        _binding?.pengaturanAccbtn?.setOnClickListener {
            val intent = Intent(this@ProfileFragment.context, EditProfileActivity::class.java)
            startActivity(intent)
        }
        _binding?.logoutBtn?.setOnClickListener {
            saveState()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}