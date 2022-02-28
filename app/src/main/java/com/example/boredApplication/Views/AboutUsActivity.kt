package com.example.boredApplication.Views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.boredApplication.R
import com.example.boredApplication.databinding.ActivityAboutUsBinding

class AboutUsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
    }

    /**
     * Initialize the components of the view MainMenuActivity
     */
    private fun initComponents() {
        bindingActivityAboutUs = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(bindingActivityAboutUs.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.about_us_text)
    }

    /**
     * Handle the click on back button
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var bindingActivityAboutUs: ActivityAboutUsBinding

}