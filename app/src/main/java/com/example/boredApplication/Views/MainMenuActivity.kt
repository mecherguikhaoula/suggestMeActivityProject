package com.example.boredApplication.Views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.boredApplication.Data.Utils.Companion.displayAlert
import com.example.boredApplication.Data.Models.ActivityEntity
import com.example.boredApplication.R
import com.example.boredApplication.ViewModels.DatabaseViewModel
import com.example.boredApplication.databinding.ActivityMainmenuBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_list_activities_searched.*
import org.jetbrains.anko.startActivity

@AndroidEntryPoint
class MainMenuActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setEvent()
    }

    /**
     * Initialize the components of the view MainMenuActivity
     */
    private fun initComponents() {
        mainmenuBinding = ActivityMainmenuBinding.inflate(layoutInflater)
        setContentView(mainmenuBinding.root)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainmenuViewModel = ViewModelProvider(this).get(DatabaseViewModel::class.java)
    }

    /**
     * Manage the click on the listeners of the class MainMenuActivity
     */
    private fun setEvent() {
        mainmenuBinding.searchActivityButton.setOnClickListener {
            startActivity<SearchForTaskActivity>()
        }

        mainmenuBinding.searchedActivitiesButton.setOnClickListener {
            mainmenuViewModel.getListActivitiesFromDatabase()

            mainmenuViewModel.getListActivitiesSearched().observe(this, {
                val listActivitiesSearched = mainmenuViewModel.listActivities.value!!

                if (!listActivitiesSearched.isNullOrEmpty()) {
                    val intent = Intent(this@MainMenuActivity, ListActivitesSearchedActivity::class.java)

                    intent.putExtra(
                        "listActivities",
                        mainmenuViewModel.listActivities.value as ArrayList<ActivityEntity>
                    )
                    startActivity(intent)
                } else {
                    displayAlert(
                        this@MainMenuActivity,
                        {},
                        getString(R.string.no_activity_searched),
                        getString(R.string.ok_text),
                        null
                    )
                }
            })
        }

        mainmenuBinding.aboutusButton.setOnClickListener {
            startActivity<AboutUsActivity>()
        }
    }

    /**
     * Manage the click on the back button
     */
    override fun onSupportNavigateUp(): Boolean {
        displayAlert(
            this,
            { finishAffinity() },
            getString(R.string.quit_app_text),
            getString(R.string.yes_text),
            getString(R.string.no_text)
        )
        return true
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    /**
     * Remove the observer when the activity in pause
     */
    override fun onPause() {
        mainmenuViewModel.getListActivitiesSearched().removeObservers(this)
        super.onPause()
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var mainmenuBinding: ActivityMainmenuBinding
    private lateinit var mainmenuViewModel: DatabaseViewModel
}