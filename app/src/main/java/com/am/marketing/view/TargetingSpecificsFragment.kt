package com.am.marketing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.marketing.R
import com.am.marketing.model.TargetingSpecific
import com.am.marketing.viewmodel.MarketingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TargetingSpecificsFragment : Fragment() {

    private lateinit var tsRecyclerView: RecyclerView
    private var adapter: TSAdapter? = null
    private lateinit var targetingSpecifics: List<TargetingSpecific>

    companion object {
        fun newInstance() = TargetingSpecificsFragment()
    }

    private val viewModel: MarketingViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.targeting_specifics_fragment, container, false)
        tsRecyclerView = view.findViewById(R.id.targeting_specifics_recycler_view)
        tsRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.marketing.observe(viewLifecycleOwner){
            targetingSpecifics = it.targetingSpecifics
            updateUI(targetingSpecifics, viewModel.selectedTargetings)
        }
    }

    private fun updateUI(targetingSpecifics: List<TargetingSpecific>, mutableSet: MutableSet<Int>) {
        for (targeting in targetingSpecifics){
            targeting.selected = mutableSet.contains(targeting.id)
        }
        adapter = TSAdapter(targetingSpecifics)
        tsRecyclerView.adapter = adapter
    }

    private inner class TSHolder(view: View): RecyclerView.ViewHolder(view){
        val tsLabel: TextView = itemView.findViewById(R.id.targeting_specifics_item_label)
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
                        setSelectedItem(ts)
                    }
                    setSelectedItem(ts)
                }

            }
        }

        override fun getItemCount() = tss.size
    }

    private fun TextView.setSelectedItem(ts: TargetingSpecific) {
        if (ts.selected)
            setTextColor(resources.getColor(R.color.purple_700))
        else
            setTextColor(resources.getColor(R.color.orange))
    }

}