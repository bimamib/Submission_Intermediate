package com.bima.mystoryapp.ui.view.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bima.mystoryapp.R
import com.bima.mystoryapp.data.Result
import com.bima.mystoryapp.data.ViewModelFactory
import com.bima.mystoryapp.databinding.ActivityMainBinding
import com.bima.mystoryapp.ui.view.story.AddStoryActivity
import com.bima.mystoryapp.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()

        viewModel.getSession().observe(this) {

        }

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_actionbar)

        itemDecoration()

        viewModel.getSession().observe(this) { user ->
            Log.d("token", "onCreate: ${user.token}")
            Log.d("user", "onCreate: ${user.isLogin}")
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
            viewModel.dataStory.observe(this) { story ->
                if (story != null) {
                    when (story) {
                        is Result.Loading -> {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                            showLoading(true)
                        }

                        is Result.Success -> {
                            val storyData = story.data.listStory
                            val storyAdapter = UserAdapter()
                            storyAdapter.submitList(storyData)
                            binding.rvUser.adapter = storyAdapter
                            showLoading(false)
                        }

                        is Result.Error -> {
                            showLoading(false)
                        }
                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStories()
    }

    private fun setUpAction() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    fun itemDecoration() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarUser.visibility = View.VISIBLE
            } else {
                progressBarUser.visibility = View.GONE
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed() // Panggil metode default
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            R.id.menu2 -> {
                // Tambahkan logika logout di sini
                viewModel.logout()

                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}