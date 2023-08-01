package com.getfly.technologies.adapter

import android.app.Activity
import android.app.LauncherActivity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.getfly.technologies.R
import com.getfly.technologies.model.response.SemDetailsResponse

class SemDetailsListAdapter (private val context: Activity,private val arrayList: ArrayList<SemDetailsResponse.Entrance>) : ArrayAdapter<SemDetailsResponse.Entrance>(context,
        R.layout.sem_details_list_item, arrayList) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val inflater : LayoutInflater = LayoutInflater.from(context)
                val view : View = inflater.inflate(R.layout.sem_details_list_item, null)

                var college : TextView = view.findViewById(R.id.tv_clg_name)
                val marks : TextView = view.findViewById(R.id.tv_marks_obtained)
                val percentage : TextView = view.findViewById(R.id.tv_percentage)

                college.text = arrayList[position].aggregatedScore
                marks.text = arrayList[position].gradeObtained
                percentage.text = arrayList[position].totalKt.toString()

                return view
        }




}