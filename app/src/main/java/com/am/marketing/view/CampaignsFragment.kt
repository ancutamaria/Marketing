package com.am.marketing.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.marketing.R
import com.am.marketing.model.data.Campaign
import com.am.marketing.viewmodel.MarketingViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignsFragment : Fragment() {

    private lateinit var campaignsRecyclerView: RecyclerView
    private var adapter: CampaignsAdapter? = null
    private lateinit var prevButton: FloatingActionButton

    private val viewModel: MarketingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.campaigns_fragment, container, false)
        campaignsRecyclerView = view.findViewById(R.id.campaigns_recycler_view)
        campaignsRecyclerView.layoutManager = LinearLayoutManager(context)
        prevButton = view.findViewById(R.id.campaigns_prev_button)
        prevButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, ChannelsFragment())
                ?.commit()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.selectedCampaigns.observe(viewLifecycleOwner){
            updateUICall()
        }
        updateUICall()
    }

    private fun updateUICall() {
        updateUI(
            viewModel.eligibleChannels
                .last { channel ->
                    channel.id == viewModel.selectedChannelID
                }.campaigns
        )
    }

    private fun updateUI(campaigns: List<Campaign>) {
        adapter = CampaignsAdapter(campaigns)
        campaignsRecyclerView.adapter = adapter
    }

    private inner class CampaignHolder(view: View): RecyclerView.ViewHolder(view){
        val channelLabel: TextView = itemView.findViewById(R.id.campaign_item_label)
        val campaignSelected: ImageView = itemView.findViewById(R.id.campaign_selected)
    }

    private inner class CampaignsAdapter(
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
//                    println("!!!!!!!!!!!!!!!!is selected ${campaign.selected}")
                    setSelectedItem(campaign)
                }
            }

        }

        override fun getItemCount() = campaigns.size

    }

    private fun CampaignHolder.setSelectedItem(campaign: Campaign) {
        if (campaign.selected) {
            campaignSelected.visibility = View.VISIBLE
        } else {
            campaignSelected.visibility = View.INVISIBLE
        }
    }

}