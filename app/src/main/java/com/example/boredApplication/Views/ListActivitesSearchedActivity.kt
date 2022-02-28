package com.example.boredApplication.Views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boredApplication.Data.Adapters.ListActivitiesSearchedAdapter
import com.example.boredApplication.Data.Utils
import com.example.boredApplication.Data.Models.ActivityEntity
import com.example.boredApplication.R
import com.example.boredApplication.ViewModels.DatabaseViewModel
import com.example.boredApplication.databinding.ActivityListActivitiesSearchedBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivitesSearchedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setEvent()
    }

    /**
     * Initialize the components of the view
     */
    private fun initComponents() {
        listActivitiesSearchedBinding = ActivityListActivitiesSearchedBinding.inflate(layoutInflater)
        setContentView(listActivitiesSearchedBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.searched_activities_text)
        searchActivityTextInputLayout = listActivitiesSearchedBinding.searchActivityTextInputLayout
        searchActivityTextInputLayout.requestFocus()
        listActivities = intent.extras?.get("listActivities") as List<ActivityEntity>
        listActivitiesRecycleView = listActivitiesSearchedBinding.listActivitiesListview
        listActivitiesRecycleView.layoutManager = LinearLayoutManager(this)
        listActivitiesSearchedAdapter = ListActivitiesSearchedAdapter(this)
        databaseViewModel = ViewModelProvider(this).get(DatabaseViewModel::class.java)
        listActivitiesRecycleView.adapter = listActivitiesSearchedAdapter
        listActivitiesSearchedAdapter.setActivities(listActivities)
        listActivitiesSearchedAdapter.notifyDataSetChanged()
    }

    /**
     * Manage the listeners of the class ListActivitesSearchedActivity
     */
    private fun setEvent() {

        // Handle the click on the button search
        searchActivityTextInputLayout.setEndIconOnClickListener {
            listActivitiesSearchedAdapter.filter.filter(listActivitiesSearchedBinding.searchActivityTextInputEditText.text)
            listActivitiesSearchedAdapter.notifyDataSetChanged()
        }

        // Handle the listener change text
        listActivitiesSearchedBinding.searchActivityTextInputEditText.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {
                listActivitiesSearchedAdapter.filter.filter(s)
                listActivitiesSearchedAdapter.notifyDataSetChanged()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Add listener befor text changed
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Add action onTextChanged
            }
        })
        configureBottomView()
    }

    /**
     * Configure the  BottomNavigationView Listener
     */
    private fun configureBottomView() {
        listActivitiesSearchedBinding.deleteCancelBottomNavigationView.setOnItemSelectedListener { item -> addActionBottomViewClick(item.itemId) }
    }

    /**
     * Add the action of click on the differents menus of BottomNavigationView
     */
    private fun addActionBottomViewClick(item: Int): Boolean {
        when (item) {
            R.id.delete_button -> {
                Utils.displayAlert(
                    this,
                    {confirmDeletionActivitiesSearched()},
                    getString(R.string.confirm_delete_text),
                    getString(R.string.yes_text),
                    getString(R.string.no_text)
                )
            }

            R.id.cancel_button -> {
                finish()
            }
        }
        return true
    }

    /**
     * Confirm the deletion of the activities
     */
    private fun confirmDeletionActivitiesSearched() {
        databaseViewModel.deleteActivities()
        finish()
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

    private lateinit var listActivitiesRecycleView: RecyclerView
    private lateinit var listActivitiesSearchedBinding: ActivityListActivitiesSearchedBinding
    private lateinit var listActivitiesSearchedAdapter: ListActivitiesSearchedAdapter
    private lateinit var databaseViewModel: DatabaseViewModel
    private lateinit var searchActivityTextInputLayout: TextInputLayout
    private var listActivities: List<ActivityEntity> = listOf()
}