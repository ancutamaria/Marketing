package com.am.marketing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.marketing.R
import com.am.marketing.model.Channel
import com.am.marketing.viewmodel.MarketingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelsFragment : Fragment() {

    private lateinit var channelsRecyclerView: RecyclerView
    private var adapter: ChannelsAdapter? = null
    private lateinit var nextButton: Button

    private val viewModel: MarketingViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.channels_fragment, container, false)
        channelsRecyclerView = view.findViewById(R.id.channels_recycler_view)
        nextButton = view.findViewById(R.id.channels_next_button)
        nextButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, ReviewFragment())
                ?.commit()
        }
        nextButton.isEnabled = viewModel.selectedCampaigns.isNotEmpty()
        channelsRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.generateEligibleChannels()
        updateUI(viewModel.eligibleChannels)
    }

    private fun updateUI(mutableSet: MutableSet<Channel>) {
        adapter = activity?.let { ChannelsAdapter(it, mutableSet.toList()) }
        channelsRecyclerView.adapter = adapter
    }

    private inner class ChannelHolder(view: View): RecyclerView.ViewHolder(view){
        val channelLabel: TextView = itemView.findViewById(R.id.channel_item_label)
        val channelCampaignSelected: ImageView = itemView.findViewById(R.id.campaign_selected)
    }

    private inner class ChannelsAdapter(val activity: FragmentActivity, var channels: List<Channel>)
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
                    if (viewModel.selectedCampaigns.containsKey(channel.id)){
                        channelCampaignSelected.visibility = View.VISIBLE
                    } else {
                        channelCampaignSelected.visibility = View.INVISIBLE
                    }

                    setOnClickListener {
                        viewModel.selectedChannelID = channel.id
                        activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CampaignsFragment())
                                .commit()
                    }
                    }
                }

            }

        override fun getItemCount() = channels.size
    }

}