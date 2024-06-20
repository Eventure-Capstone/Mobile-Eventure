package com.eventurecapstone.eventure.view.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.databinding.FragmentProfileBinding
import com.eventurecapstone.eventure.view.edit_profile.EditProfileActivity
import com.eventurecapstone.eventure.view.my_post.MyPostActivity
import com.eventurecapstone.eventure.view.welcome_screen.WelcomeScreenActivity

class ProfileFragment : Fragment() {
    private lateinit var model: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity()))[ProfileViewModel::class.java]

        attachAccountInfoToView()
        setupToggleButton()
        setupLogoutButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun attachAccountInfoToView(){
        model.userInfo.observe(viewLifecycleOwner){ user ->
            if (user?.token != null){
                // TODO: Perbaiki info user
                with(binding){
                    username.text = "someone"
                    email.text = "someone"
//                    if (!user.pictureUrl.isNullOrBlank()){
//                        Glide.with(requireActivity()).load(user.pictureUrl).into(userImage)
//                    }

                    editButton.setOnClickListener {
                        val intent = Intent(requireActivity(), EditProfileActivity::class.java)
                        startActivity(intent)
                    }

                    setupMoreButton(true)
                }
            } else {
                val intent = Intent(requireActivity(), WelcomeScreenActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun setupMoreButton(isVerified: Boolean){
        binding.moreButton.setOnClickListener {
            val intent = if (isVerified){
                Intent(requireActivity(), MyPostActivity::class.java)
            } else {
                Intent(requireActivity(), MyPostActivity::class.java)
            }
            startActivity(intent)
        }
    }

    private fun setupToggleButton(){
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                model.setTheme(UserPreference.Theme.NIGHT)
            } else {
                model.setTheme(UserPreference.Theme.LIGHT)
            }
        }

        model.systemTheme.observe(viewLifecycleOwner){
            binding.darkModeSwitch.isChecked = it == UserPreference.Theme.NIGHT

            if (it == UserPreference.Theme.NIGHT) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            context?.let { context ->
                AlertDialog.Builder(context).apply {
                    setTitle(getString(R.string.logout))
                    setMessage(getString(R.string.logout_message))
                    setPositiveButton(getString(R.string.yes)) { _, _ ->
                        model.logout()
                    }
                    setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
                    create()
                    show()
                }
            }
        }
    }

}