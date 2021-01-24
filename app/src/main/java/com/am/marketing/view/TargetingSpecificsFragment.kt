package com.am.marketing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.marketing.R
import com.am.marketing.model.MarketingResponse
import com.am.marketing.model.TargetingSpecific
import com.am.marketing.viewmodel.MarketingViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TargetingSpecificsFragment : Fragment() {

    private lateinit var tsRecyclerView: RecyclerView
    private var tsAdapter: TSAdapter? = null
    private lateinit var nextButton: FloatingActionButton

    private val viewModel: MarketingViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.targeting_specifics_fragment, container, false)
        tsRecyclerView = view.findViewById(R.id.targeting_specifics_recycler_view)
        tsRecyclerView.layoutManager = LinearLayoutManager(context)

        nextButton = view.findViewById(R.id.targeting_specifics_next_button)
        nextButton.setOnClickListener{
            if  (viewModel.selectedTargetings.size == 0){
                AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                    .setMessage("Please select at least one option to proceed")
                    .setPositiveButton("OK") { _,_ -> }
                    .create()
                    .show()
            } else {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, ChannelsFragment())
                    ?.commit()
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.marketing.observe(viewLifecycleOwner){ response ->
            when (response) {
                is MarketingResponse.OK -> {
                    updateUI(response.data?.targetingSpecifics, viewModel.selectedTargetings)
                }
                is MarketingResponse.Error -> {
                    AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                        .setMessage(response.message)
                        .setPositiveButton("OK") { _,_ -> }
                        .create()
                        .show()
                }
            }

        }
    }

    private fun updateUI(targetingSpecifics: List<TargetingSpecific>?, mutableSet: MutableSet<Int>) {
        if (targetingSpecifics != null) {
            for (targeting in targetingSpecifics){
                targeting.selected = mutableSet.contains(targeting.id)
            }
            tsAdapter = TSAdapter(targetingSpecifics)
            tsRecyclerView.adapter = tsAdapter
        }
    }

    private inner class TSHolder(view: View): RecyclerView.ViewHolder(view){
        val tsLabel: TextView = itemView.findViewById(R.id.targeting_specifics_item_label)
        val tsSelected: ImageView = itemView.findViewById(R.id.targeting_specifics_selected)

    }

    private inner class TSAdapter(var tss: List<TargetingSpecific>)
        : RecyclerView.Adapter<TSHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TSHolder {
            val view = layoutInflater.inflate(R.layout.targeting_specifics_item, parent, false)
            return TSHolder(view)
        }

        override fun onBindViewHolder(holder: TSHolder, position: Int) {
            val ts = tss[position]
            holder.apply {
                tsLabel.apply {
                    text = ts.label
                    setOnClickListener {
                        ts.selected = !ts.selected
                        viewModel.addSelectedTargetedSpecifics(ts.id, ts.selected)
                        setSelected(ts)
                    }
                    setSelected(ts)
                }

            }
        }

        override fun getItemCount() = tss.size

    }

    private fun TSHolder.setSelected(ts: TargetingSpecific) {
        if (ts.selected) {
            tsSelected.visibility = View.VISIBLE
        } else {
            tsSelected.visibility = View.INVISIBLE
        }
    }

}