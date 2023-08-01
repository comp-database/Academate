package com.getfly.technologies.ui.academics

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.getfly.technologies.AdmissionScreen
import com.getfly.technologies.HomeActivity
import com.getfly.technologies.MainViewModelFactory
import com.getfly.technologies.R
import com.getfly.technologies.adapter.SemDetailsListAdapter
import com.getfly.technologies.model.viewmodel.MainScreenViewModel
import com.getfly.technologies.databinding.FragmentAcademicsBinding
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.response.SemDetailsResponse

class AcademicsFragment : Fragment() {

    private lateinit var binding: FragmentAcademicsBinding
    private lateinit var viewModel: MainScreenViewModel
    private lateinit var semDetailsList: String
    var pweActivityResultLauncher: ActivityResultLauncher<Intent>? = null

    //Shared preferences to store user uid
    companion object {
        const val SHARED_PREFS_NAME = "AcademateLogin"
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAcademicsBinding.inflate(layoutInflater)

        val dataList = listOf<SemDetailsResponse.Entrance>()

        sharedPreferences = requireActivity().applicationContext.getSharedPreferences(
            AcademicsFragment.SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val uidSP = sharedPreferences.getInt("uid", 0)
        if (uidSP != 0) {
            val repository = AcademateRepository()
            val viewModelFactory = MainViewModelFactory(repository)

            viewModel = ViewModelProvider(this, viewModelFactory)[MainScreenViewModel::class.java]


            //Get Education details
            viewModel.getEducationDetails(uidSP.toString())
            viewModel.EducationDetailsResponse.observe(this.requireActivity()) { EducationDetailsResponseStatus ->
                if (EducationDetailsResponseStatus.isSuccessful) {
                    binding.tvSscSchoolName.text =
                        "SSC Seat NO : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.sscSeatNumber
                    binding.tvHscSchoolName.text =
                        "HSC Seat NO : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.hscSeatYear
                    binding.tvSscMarksObtained.text =
                        "SSC Marks : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.sscMarks
                    binding.tvHscMarksObtained.text =
                        "HSC Marks : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.hscMarks
                    binding.tvSscPercentage.text =
                        "SSC Percentage : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.sscPercentage + " %"
                    binding.tvHscPercentage.text =
                        "HSC Percentage : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.hscPercentage + " %"
                }
            }

            //Get SEM details
            viewModel.getSemDetails(uidSP.toString())
            viewModel.SemDetailsResponse.observe(this.requireActivity()) { SemDetailsResponseStatus ->
                if (SemDetailsResponseStatus.isSuccessful) {

                    for (i in SemDetailsResponseStatus.body()?.entrance!!.indices) {
                        semDetailsList =
                            SemDetailsResponseStatus.body()?.entrance?.get(i)?.aggregatedScore.toString()

                        Log.d("vishal", semDetailsList)


                    }

//                    binding.semDetailsListView.adapter = SemDetailsListAdapter(this.requireActivity(), semDetailsList)
//                    // use arrayadapter and define an array
//                    val arrayAdapter: ArrayAdapter<*>
//                    val users = arrayListOf<SemDetailsResponse>()
//
//                    // access the listView from xml file
//                    var mListView = binding.semDetailsListView
//                    arrayAdapter = ArrayAdapter(this.requireActivity(),
//                        R.layout.sem_details_list_item, users)
//                    mListView.adapter = arrayAdapter

//                            binding.tvSscPercentage.text =
//                                "SSC Seat NO : " + entrance.aggregatedScore
//                                        binding.tvHscSchoolName.text =
//                                "HSC Seat NO : " + EducationDetailsResponseStatus.body()?.data?.get(
//                                    0
//                                )?.hscSeatYear
//                            binding.tvSscMarksObtained.text =
//                                "SSC Marks : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.sscMarks
//                            binding.tvHscMarksObtained.text =
//                                "HSC Marks : " + EducationDetailsResponseStatus.body()?.data?.get(0)?.hscMarks
//                            binding.tvSscPercentage.text =
//                                "SSC Percentage : " + EducationDetailsResponseStatus.body()?.data?.get(
//                                    0
//                                )?.sscPercentage + " %"
//                            binding.tvHscPercentage.text =
//                                "HSC Percentage : " + EducationDetailsResponseStatus.body()?.data?.get(
//                                    0
//                                )?.hscPercentage + " %"

                }

            }
        }
        return binding.root
    }
}