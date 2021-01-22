package com.am.marketing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.marketing.R
import com.am.marketing.model.TargetingSpecific
import com.am.marketing.viewmodel.TargetingSpecificsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TargetingSpecificsFragment : Fragment() {

    private lateinit var tsRecyclerView: RecyclerView
    private var adapter: BookAdapter? = null

    companion object {
        fun newInstance() = TargetingSpecificsFragment()
    }

    private val viewModel: TargetingSpecificsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.targeting_specifics_fragment, container, false)
        tsRecyclerView = view.findViewById(R.id.targeting_specifics_recycler_view)
        tsRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.targetingSpecific.observe(viewLifecycleOwner){
//            Toast.makeText(activity, it.toString(), Toast.LENGTH_LONG).show()
            updateUI(it.targetingSpecifics)
        } }


    private fun updateUI(targetingSpecifics: List<TargetingSpecific>) {
        adapter = BookAdapter(targetingSpecifics)
        tsRecyclerView.adapter = adapter
    }

    private inner class TSHolder(view: View): RecyclerView.ViewHolder(view){
        val tsLabel: TextView = itemView.findViewById(R.id.targeting_specifics_item_label)
    }

    private inner class BookAdapter(var tss: List<TargetingSpecific>)
        : RecyclerView.Adapter<TSHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TSHolder {
            val view = layoutInflater.inflate(R.layout.targeting_specifics_item, parent, false)
            return TSHolder(view)
        }

        override fun onBindViewHolder(holder: TSHolder, position: Int) {
            val ts = tss[position]
            holder.apply {
                tsLabel.text = ts.label
            }
        }

        override fun getItemCount() = tss.size

    }

}