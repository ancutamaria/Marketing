package com.am.marketing.view

import android.content.Intent
import android.net.Uri
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
import com.am.marketing.viewmodel.MarketingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment : Fragment() {

    private lateinit var reviewRecyclerView: RecyclerView
    private var adapter: ReviewAdapter? = null

    companion object {
        fun newInstance() = ChannelsFragment()
    }

    private val viewModel: MarketingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.review_fragment, container, false)
        reviewRecyclerView = view.findViewById(R.id.review_recycler_view)
        reviewRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateUI(viewModel.generateFinalSelections())
    }

    private fun updateUI(finalSelections: List<String>) {
        adapter = activity?.let { ReviewAdapter(it, finalSelections) }
        reviewRecyclerView.adapter = adapter
    }

    private inner class ReviewHolder(view: View): RecyclerView.ViewHolder(view){
        val finalSelectionLabel: TextView = itemView.findViewById(R.id.review_item_label)
    }

    private inner class ReviewAdapter(
        val activity: FragmentActivity,
        var finalSelections: List<String>
    )
        : RecyclerView.Adapter<ReviewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
            val view = layoutInflater.inflate(R.layout.review_item, parent, false)
            return ReviewHolder(view)
        }

        override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
            val campaign = finalSelections[position]
            holder.apply {
                finalSelectionLabel.apply {
                    text = campaign
                    setOnClickListener {
                        sendEmail(finalSelections)
                    }
                }
            }

        }
        override fun getItemCount() = finalSelections.size
    }

    fun sendEmail(content: List<String>) {

        val selectorIntent = Intent(Intent.ACTION_SENDTO)
        selectorIntent.data = Uri.parse("mailto:")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "ancuta.maria@gmail.com")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Send marketing selections")
        emailIntent.putExtra(Intent.EXTRA_TEXT, formatContent(content))
        emailIntent.selector = selectorIntent
        activity?.startActivity(Intent.createChooser(emailIntent, "Send feedback to XYZ"))
    }

    fun formatContent(content: List<String>): String{
        var result = "Hello,\nThese are the droids you're looking for!\n\n"
        content.forEach {
            result += it + "\n\n"
        }
        result += "\n\nRegards,\nMarketing Team"
        return result
    }
}