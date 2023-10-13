package com.bima.mystoryapp.ui.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.bima.mystoryapp.R
import com.bima.mystoryapp.ui.view.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.custom_actionbar)
    }

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
                // Tambahkan logika logout di sini
                // Contoh: Navigasi ke halaman login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()  // Optional: Menutup aktivitas saat kembali ke halaman login
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}