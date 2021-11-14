package com.unsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.unsplash.utils.SharedPreferenceUtil
import com.unsplash.utils.UserInteractionListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    private var userInteractionListener: UserInteractionListener? = null
    lateinit var pbLoading: ProgressBar

    @Inject
    lateinit var sharedPref: SharedPreferenceUtil

    lateinit var sessionTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment

        pbLoading = findViewById(R.id.pbLoading)

        initSessionTimer()

        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
                || navController.navigateUp(appBarConfiguration)
    }

    fun setUInteractionListener(userInteractionListener: UserInteractionListener?) {
        this.userInteractionListener = userInteractionListener!!
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        userInteractionListener?.onUserInteraction()
    }

    fun initSessionTimer(){
        sessionTimer = initTimer()
    }

    fun startSessionTimer(){
        sessionTimer.start()
    }

    fun cancelSessionTimer(){
        sessionTimer.cancel()
    }

    fun initTimer() : CountDownTimer {
        val timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                sessionExpiredLogout()
            }
        }
        return timer
    }

    fun sessionExpiredLogout(){
        Log.d("GLG","Logout")
        try {
            sharedPref.clearPref()
        } catch (e: Exception) {
            showGeneralErrorDialog()
        }
        findNavController(R.id.nav_host).popBackStack(R.id.loginFragment,false)
        showGeneralErrorDialog("Your session has expired, please login again.")
    }

    fun showGeneralErrorDialog() {
        try {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.apply {
                setMessage("Oops something went wrong, please try again")
                setPositiveButton("Ok") { dialog, whichButton ->
                    dialog.dismiss()
                }
            }.create().show()
        } catch (e: Exception) {

        }
    }

    fun showGeneralErrorDialog(errorMessage: String) {
        try {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.apply {
                setMessage(errorMessage)
                setPositiveButton("Ok") { dialog, whichButton ->
                    dialog.dismiss()
                }
            }.create().show()
        } catch (e: Exception) {

        }
    }

    fun showLoadingBar(){
        pbLoading.visibility = View.VISIBLE
    }

    fun hideLoadingBar(){
        pbLoading.visibility = View.GONE
    }
}