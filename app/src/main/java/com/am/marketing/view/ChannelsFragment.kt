package com.am.marketing.view

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.marketing.R
import com.am.marketing.model.Channel
import com.am.marketing.viewmodel.MarketingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelsFragment : Fragment() {

    private lateinit var tsRecyclerView: RecyclerView
    private var adapter: ChannelsAdapter? = null

    companion object {
        fun newInstance() = ChannelsFragment()
    }

    private val viewModel: MarketingViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.channels_fragment, container, false)
        tsRecyclerView = view.findViewById(R.id.targeting_specifics_recycler_view)
        tsRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.generateEligibleChannels()
        updateUI(viewModel.eligibleChannels)
    }

    private fun updateUI(mutableSet: MutableSet<Channel>) {
        adapter = ChannelsAdapter(mutableSet.toList())
        tsRecyclerView.adapter = adapter
    }

    private inner class ChannelHolder(view: View): RecyclerView.ViewHolder(view){
        val channelLabel: TextView = itemView.findViewById(R.id.channel_item_label)
    }

    private inner class ChannelsAdapter(var channels: List<Channel>)
        : RecyclerView.Adapter<ChannelHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
            val view = layoutInflater.inflate(R.layout.channels_item, parent, false)
            return ChannelHolder(view)
        }

        override fun onBindViewHolder(holder: ChannelHolder, position: Int) {
            val channel = channels[position]
            holder.apply {
                channelLabel.apply {
                    text = channel.name
                    setOnClickListener {
                        val intent = Intent(activity, CampaignsFragment::class.java).apply {
                            putExtra("channelid", channel.id)
                        }
                        startActivity(intent)
                    }
                }

            }
        }

        override fun getItemCount() = channels.size
    }

}