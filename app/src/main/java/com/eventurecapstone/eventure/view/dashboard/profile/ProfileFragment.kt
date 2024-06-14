package com.eventurecapstone.eventure.view.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.eventurecapstone.eventure.databinding.FragmentProfileBinding
import com.eventurecapstone.eventure.view.my_post.MyPostActivity

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

        model = ViewModelProvider(this)[ProfileViewModel::class.java]

        attachAccountInfoToView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun attachAccountInfoToView(){
        model.fetchUserInfo()

        model.userInfo.observe(viewLifecycleOwner){ user ->
            with(binding){
                username.text = user.name
                email.text = user.email
                Glide.with(requireActivity()).load(user.pictureUrl).into(userImage)

                setupMoreButton(user.verified!!)
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
}