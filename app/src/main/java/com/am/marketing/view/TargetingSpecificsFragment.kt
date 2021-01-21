package com.am.marketing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.am.marketing.R
import com.am.marketing.viewmodel.TargetingSpecificsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TargetingSpecificsFragment : Fragment() {

    companion object {
        fun newInstance() = TargetingSpecificsFragment()
    }

    private val viewModel: TargetingSpecificsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.targeting_specifics_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.targetingSpecific.observe(viewLifecycleOwner){
            Toast.makeText(activity, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

}