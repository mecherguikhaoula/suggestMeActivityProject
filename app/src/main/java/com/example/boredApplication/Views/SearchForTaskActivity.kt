package com.example.boredApplication.Views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.boredApplication.Data.Constantes.Companion.CONST_SEPARATOR
import com.example.boredApplication.Data.Utils
import com.example.boredApplication.Data.Utils.Companion.getRandomColor
import com.example.boredApplication.Data.Models.ActivityEntity
import com.example.boredApplication.R
import com.example.boredApplication.ViewModels.DatabaseViewModel
import com.example.boredApplication.ViewModels.SearchActivityViewModel
import com.example.boredApplication.databinding.ActivitySearchForActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.backgroundColor

@AndroidEntryPoint
class SearchForTaskActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setEvent()
    }

    /**
     * Initialize the components of the view
     */
    private fun initComponents() {
        searchForActivityBinding = ActivitySearchForActivityBinding.inflate(layoutInflater)
        setContentView(searchForActivityBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.search_task_title_text)
        searchForActivityViewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        databaseViewModel = ViewModelProvider(this).get(DatabaseViewModel::class.java)
    }

    /**
     * Manage the listeners of the class HomeActivity
     */
    @SuppressLint("SetTextI18n")
    private fun setEvent() {

        searchForActivityBinding.searchActivityButton.setOnClickListener {
            searchForActivityViewModel.getActivityFromApi()
            // Observe the activity  from Api
            searchForActivityViewModel.getActivity().observe(this, {
                if (searchForActivityViewModel.activityFromApi.value != null) {
                    activitySuggested = searchForActivityViewModel.activityFromApi.value!!
                    searchForActivityBinding.layoutFragementInformationActivity.informationActivityCardView.backgroundColor =
                        getRandomColor()
                    searchForActivityBinding.layoutFragementInformationActivity.informationActivityCardView.visibility = View.VISIBLE
                    if (activitySuggested.activity.isNotBlank()) {
                        searchForActivityBinding.layoutFragementInformationActivity.activityMaterialTextView.text =
                            "${getString(R.string.activity_text)} ${CONST_SEPARATOR} ${activitySuggested.activity}"
                        searchForActivityBinding.layoutFragementInformationActivity.activityMaterialTextView.visibility = View.VISIBLE
                    } else {
                        searchForActivityBinding.layoutFragementInformationActivity.activityMaterialTextView.visibility = View.GONE
                    }
                    if (!activitySuggested.type.isNullOrEmpty()) {
                        searchForActivityBinding.layoutFragementInformationActivity.typeMaterialTextView.text =
                            "${getString(R.string.type_text)} ${CONST_SEPARATOR} ${activitySuggested.type}"
                        searchForActivityBinding.layoutFragementInformationActivity.typeMaterialTextView.visibility = View.VISIBLE
                    } else {
                        searchForActivityBinding.layoutFragementInformationActivity.typeMaterialTextView.visibility = View.GONE
                    }
                    searchForActivityBinding.layoutFragementInformationActivity.participantsMaterialTextView.text =
                        "${getString(R.string.participants_text)} ${CONST_SEPARATOR} ${activitySuggested.participants.toString()}"

                    searchForActivityBinding.layoutFragementInformationActivity.priceMaterialTextView.text =
                        "${getString(R.string.price_text)} ${CONST_SEPARATOR} ${activitySuggested.price.toString()}"
                    if (!activitySuggested.link.isNullOrEmpty()) {
                        searchForActivityBinding.layoutFragementInformationActivity.linkMaterialTextView.text =
                            "${getString(R.string.link_text)} ${CONST_SEPARATOR} ${activitySuggested.link}"
                        searchForActivityBinding.layoutFragementInformationActivity.linkMaterialTextView.visibility = View.VISIBLE
                    } else {
                        searchForActivityBinding.layoutFragementInformationActivity.linkMaterialTextView.visibility = View.GONE
                    }
                    if(!activitySuggested.key.isNullOrEmpty()) {
                        searchForActivityBinding.layoutFragementInformationActivity.keyMaterialTextView.text =
                            "${getString(R.string.key_text)} ${CONST_SEPARATOR} ${activitySuggested.key}"
                        searchForActivityBinding.layoutFragementInformationActivity.keyMaterialTextView.visibility = View.VISIBLE
                    } else {
                        searchForActivityBinding.layoutFragementInformationActivity.keyMaterialTextView.visibility = View.GONE
                    }
                    searchForActivityBinding.layoutFragementInformationActivity.accessibilityMaterialTextView.text =
                        "${getString(R.string.accessibility_text)} ${CONST_SEPARATOR} ${activitySuggested.accessibility.toString()}"
                    databaseViewModel.insertActitivity(activitySuggested)
                } else {
                    Utils.displayAlert(
                        this,
                        {finish()},
                        getString(R.string.call_api_error_message),
                        getString(R.string.ok_text),
                        null
                    )
                }
            })

            // Observe the progress Bar
            searchForActivityViewModel.getIsProgressing().observe(this, {
                if (searchForActivityViewModel.isProgressingBar.value!!) {
                    searchForActivityBinding.progressBar.isVisible = true
                    searchForActivityBinding.loadingDataMaterialTextView.isVisible = true
                } else {
                    searchForActivityBinding.progressBar.visibility = View.GONE
                    searchForActivityBinding.loadingDataMaterialTextView.visibility = View.GONE
                }
            })
        }
    }

    /**
     * Manage the click on back button
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var searchForActivityBinding: ActivitySearchForActivityBinding
    private lateinit var searchForActivityViewModel: SearchActivityViewModel
    private lateinit var databaseViewModel: DatabaseViewModel
    private lateinit var activitySuggested: ActivityEntity
}