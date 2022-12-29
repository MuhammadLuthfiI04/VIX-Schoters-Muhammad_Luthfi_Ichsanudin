package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.activity.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.R
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.customize.CircleTransform
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.databinding.ActivityProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        viewModel.setUserDetail("muhammadluthfii04")
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvLink.text = it.html_url
                    tvLink.setOnClickListener {
                        val intentToGit = Intent(Intent.ACTION_VIEW)
                        startActivity(intentToGit)
                    }
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvBlog.text = it.blog
                    tvRepository.text = "${it.public_repos}\nRepository"
                    tvGists.text = "${it.public_gists}\nGists"
                    tvFollowers.text = "${it.followers}\nFollowers"
                    tvFollowing.text = "${it.following}\nFollowing"
                    Picasso.get()
                        .load(it.avatar_url)
                        .fit()
                        .centerCrop()
                        .transform(CircleTransform())
                        .error(R.drawable.placeholder)
                        .into(imgProfile)
                }
            }
        }
    }
}