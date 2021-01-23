package com.am.marketing.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.marketing.R
import com.am.marketing.model.Campaign
import com.am.marketing.viewmodel.MarketingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignsFragment : Fragment() {

    private lateinit var champaignsRecyclerView: RecyclerView
    private var adapter: CampaignsAdapter? = null
    private var checkedPosition = -1

    companion object {
        fun newInstance() = ChannelsFragment()
    }

    private val viewModel: MarketingViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.campaigns_fragment, container, false)
        champaignsRecyclerView = view.findViewById(R.id.campaigns_recycler_view)
        champaignsRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateUI(viewModel.eligibleChannels
            .last() { channel ->
                channel.id == viewModel.selectedChannelID
            }.campaigns
        )
    }

    private fun updateUI(campaigns: List<Campaign>) {
        adapter = activity?.let { CampaignsAdapter(it, campaigns) }
        champaignsRecyclerView.adapter = adapter
    }

    private inner class CampaignHolder(view: View): RecyclerView.ViewHolder(view){
        val channelLabel: TextView = itemView.findViewById(R.id.campaign_item_label)
    }

    private inner class CampaignsAdapter(
        val activity: FragmentActivity,
        var campaigns: List<Campaign>
    )
        : RecyclerView.Adapter<CampaignHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignHolder {
            val view = layoutInflater.inflate(R.layout.campaigns_item, parent, false)
            return CampaignHolder(view)
        }

        override fun onBindViewHolder(holder: CampaignHolder, position: Int) {
            val campaign = campaigns[position]
            holder.apply {
                channelLabel.apply {
                    text = campaign.content
                    setOnClickListener {
                        campaign.selected = !campaign.selected
                        viewModel.addSelectedCampaigns(campaign.id, campaign.selected)
                        setSelectedItem(campaign)
                    }
                    setSelectedItem(campaign)
                }
            }

        }

        override fun getItemCount() = campaigns.size


    }

    private fun TextView.setSelectedItem(campaign: Campaign) {
        if (campaign.selected)
            setTextColor(resources.getColor(R.color.purple_700))
        else
            setTextColor(resources.getColor(R.color.orange))
    }


}