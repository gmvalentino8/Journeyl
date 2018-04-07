package com.valentino.journeyl.adapter

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.valentino.journeyl.R
import com.valentino.journeyl.activity.MilestoneActivity
import com.valentino.journeyl.model.Milestone
import kotlinx.android.synthetic.main.item_roadmap_vertical.view.*

/**
 * Created by Valentino on 4/4/18.
 */

class RoadmapAdapter(private val milestoneData: List<Milestone>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return  milestoneData.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == milestoneData.size - 1) {
            return 1
        }
        else {
            return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_roadmap_vertical, parent, false)
        if (viewType == 1) {
            root.milestoneButton.setImageResource(R.drawable.milestone_circle_empty)
            root.milestoneButton.tag = "Incomplete"
        }
        return RoadmapsViewHolder(root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = milestoneData[position]
        val placeHolder = holder as RoadmapsViewHolder
        placeHolder.bindData(data)
    }

    class RoadmapsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindData(milestone: Milestone) {
            view.milestoneContent.text = milestone.description


            if (view.milestoneButton.tag == "Incomplete") {
                view.milestoneButton.setOnLongClickListener {
                    Toast.makeText(view.context, "Goal Completed", Toast.LENGTH_SHORT).show()
                    true
                }
            }
            else {
                view.milestoneContent.setOnClickListener {
                    goToMilestoneActivity(milestone)
                }

                view.milestoneButton.setOnClickListener {
                    goToMilestoneActivity(milestone)
                }
            }
        }

        fun goToMilestoneActivity(milestone: Milestone) {
            val intent = Intent(view.context, MilestoneActivity::class.java)
            intent.putExtra("Milestone", milestone.description)
            startActivity(view.context, intent, null)
        }
    }
}