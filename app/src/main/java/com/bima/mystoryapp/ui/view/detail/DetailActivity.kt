package com.bima.mystoryapp.ui.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bima.mystoryapp.R
import com.bima.mystoryapp.data.ViewModelFactory
import com.bima.mystoryapp.databinding.ActivityDetailBinding
import com.bima.mystoryapp.databinding.ActivityMainBinding
import com.bima.mystoryapp.ui.view.main.MainViewModel
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(ID)?: ""
        name = intent.getStringExtra(NAME)?: ""
        description = intent.getStringExtra(DESCRIPTION)?: ""
        picture = intent.getStringExtra(PICTURE)?: ""

        binding.tvDetailName.text = name
        binding.tvDetailDescription.text = description

        Glide.with(this).load(picture).into(binding.ivDetailPhoto)
    }

    companion object {
        const val ID = "ID"
        const val NAME = "NAME"
        const val DESCRIPTION = "DESCRIPTION"
        const val PICTURE = "PICTURE"

        var id: String = ""
        var name: String = ""
        var description: String? = null
        var picture: String? = null
    }
}