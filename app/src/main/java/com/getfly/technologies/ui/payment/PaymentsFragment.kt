package com.getfly.technologies.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.getfly.technologies.databinding.FragmentPaymentBinding

class PaymentsFragment : Fragment() {

    private lateinit var binding : FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(layoutInflater)



        return binding.root
    }
}