package com.example.boredApplication.Views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.boredApplication.Data.Constantes
import com.example.boredApplication.R
import com.example.boredApplication.ViewModels.SplachScreenViewModel
import com.example.boredApplication.databinding.ActivitySplashscreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity

class SplashScreenActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        setEvent()
    }

    /**
     * Initialize the components of the view
     */
    @SuppressLint("SetTextI18n")
    private fun initComponents() {
        splashscreenBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(splashscreenBinding.root)
        progressBar = splashscreenBinding.progressBarSplashScreen
        splashScreenViewModel = ViewModelProvider(this).get(SplachScreenViewModel::class.java)
        splashscreenBinding.libVersion.text =  "${getString(R.string.version_text)} ${Constantes.CONST_APP_VERSION}"
        // to pass on fullscreen
        supportActionBar?.hide()
    }

    /**
     * Manage the listeners of the class SplashScreenActivity
     */
    private fun setEvent() {
        CoroutineScope(Dispatchers.Main).launch {
            splashScreenViewModel.updateProgressBar()
        }
        splashScreenViewModel.getIsProgressing().observe(this, {
            if (splashScreenViewModel.progressBarStatus.value!! <= 100) {
                progressBar.progress = splashScreenViewModel.progressBarStatus.value!!
            } else {
                startActivity<MainMenuActivity>()
                finish()
            }
        })
    }

    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private lateinit var splashScreenViewModel: SplachScreenViewModel
    private lateinit var splashscreenBinding: ActivitySplashscreenBinding
    private lateinit var progressBar: ProgressBar

}