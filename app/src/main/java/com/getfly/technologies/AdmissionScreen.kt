package com.getfly.technologies

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.getfly.technologies.databinding.ActivityAdmissionSectionBinding
import com.getfly.technologies.model.AcademateRepository

class AdmissionScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAdmissionSectionBinding
    private lateinit var viewModel: MainScreenViewModel

    //Shared preferences to store user uid
    companion object {
        private const val SHARED_PREFS_NAME = "AcademateLogin"
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmissionSectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = applicationContext.getSharedPreferences(
            AdmissionScreen.SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val uidSP = AdmissionScreen.sharedPreferences.getInt("uid", 0)
        if (uidSP != 0) {
            val repository = AcademateRepository()
            val viewModelFactory = MainViewModelFactory(repository)

            viewModel = ViewModelProvider(this, viewModelFactory)[MainScreenViewModel::class.java]

            //Get Faculty dashboard details
            viewModel.getfacultyDashboard(uidSP.toString())
            viewModel.FacultyDashboardResponse.observe(this) { FacultyDashboardResponseStatus ->
                if (FacultyDashboardResponseStatus.isSuccessful) {
                    binding.tvFirstYearCompleted.text =
                        FacultyDashboardResponseStatus.body()?.count1.toString()
                    binding.tvDseCompleted.text =
                        FacultyDashboardResponseStatus.body()?.count2.toString()
                    binding.tvSecondYearCompleted.text =
                        FacultyDashboardResponseStatus.body()?.count3.toString()
                    binding.tvThirdYearCompleted.text =
                        FacultyDashboardResponseStatus.body()?.count4.toString()
                    binding.tvFinalYearCompleted.text =
                        FacultyDashboardResponseStatus.body()?.count5.toString()
                    binding.tvComputerTotal.text =
                        FacultyDashboardResponseStatus.body()?.cs.toString()
                    binding.tvItTotal.text = FacultyDashboardResponseStatus.body()?.it.toString()
                    binding.tvAidsTotal.text =
                        FacultyDashboardResponseStatus.body()?.aids.toString()
                }
            }

            viewModel.getPendingApplication(uidSP.toString())
            viewModel.PendingApplicationResponse.observe(this) { PendingApplicationResponseStatus ->
                if (PendingApplicationResponseStatus.isSuccessful) {
                    binding.tvComputerPending.text = PendingApplicationResponseStatus.body()?.pcs.toString()
                    binding.tvItPending.text = PendingApplicationResponseStatus.body()?.pit.toString()
                    binding.tvAidsPending.text = PendingApplicationResponseStatus.body()?.paids.toString()
                }
            }
        } else {
            Toast.makeText(this, "Unable to fetch details", Toast.LENGTH_SHORT).show()
        }

        //Logout button
        binding.btnLogout.setOnClickListener {
            val editor = AdmissionScreen.sharedPreferences.edit()
            editor.putBoolean("isStudent", false)
            editor.putBoolean("isAdmission", false)
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}