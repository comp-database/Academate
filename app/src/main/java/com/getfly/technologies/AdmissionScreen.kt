package com.getfly.technologies

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.getfly.technologies.databinding.ActivityAdmissionSectionBinding
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.viewmodel.MainScreenViewModel

class AdmissionScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAdmissionSectionBinding
    private lateinit var viewModel: MainScreenViewModel

    //Shared preferences to store user uid
    companion object {
        const val SHARED_PREFS_NAME = "AcademateLogin"
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
                        "Total : " + FacultyDashboardResponseStatus.body()?.count1.toString()
                    binding.tvDseCompleted.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.count2.toString()
                    binding.tvSecondYearCompleted.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.count3.toString()
                    binding.tvThirdYearCompleted.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.count4.toString()
                    binding.tvFinalYearCompleted.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.count5.toString()
                    binding.tvComputerTotal.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.cs.toString()
                    binding.tvItTotal.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.it.toString()
                    binding.tvAidsTotal.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.aids.toString()
                    binding.tvExtcTotal.text =
                        "Total : " + FacultyDashboardResponseStatus.body()?.extc.toString()
                }
            }

            viewModel.getPendingApplication(uidSP.toString())
            viewModel.pendingApplicationsLiveData.observe(this, Observer { pendingApplications ->
                binding.tvComputerPending.text =
                    "Pending : " + pendingApplications.get(0).pcs.toString()
                binding.tvItPending.text =
                    "Pending : " + pendingApplications.get(0).pit.toString()
                binding.tvAidsPending.text =
                    "Pending : " + pendingApplications.get(0).paids.toString()
                binding.tvExtcPending.text =
                    "Pending : " + pendingApplications.get(0).pextc.toString()
            })

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
}