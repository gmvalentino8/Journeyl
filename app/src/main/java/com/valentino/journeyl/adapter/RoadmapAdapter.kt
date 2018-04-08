package com.valentino.journeyl.adapter

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.valentino.journeyl.R
import com.valentino.journeyl.activity.CreateMilestoneActivity
import com.valentino.journeyl.activity.MilestoneActivity
import com.valentino.journeyl.activity.ReflectionActivity
import com.valentino.journeyl.model.Milestone
import com.valentino.journeyl.model.Goal
import kotlinx.android.synthetic.main.item_roadmap_vertical.view.*

/**
 * Created by Valentino on 4/4/18.
 */

class RoadmapAdapter(private val milestoneData: List<Milestone>, val goal: Goal) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return  milestoneData.size
    }

    override fun getItemViewType(position: Int): Int {
        val milestone = milestoneData[position]
        if (milestone.completed) {
            return 0
        }
        else {
            return 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_roadmap_vertical, parent, false)
        if (viewType == 1) {
            root.milestoneButton.setImageResource(R.drawable.milestone_circle_empty)
            root.milestoneButton.tag = "Incomplete"
        }
        return RoadmapViewHolder(root, goal)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = milestoneData[position]
        val placeHolder = holder as RoadmapViewHolder
        placeHolder.bindData(data)
    }

    class RoadmapViewHolder(itemView: View, val goal: Goal) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindData(milestone: Milestone) {
            view.milestoneContent.text = milestone.description

            if (view.milestoneButton.tag == "Incomplete") {
                view.milestoneButton.setOnLongClickListener {
                    goToReflectionActivity(milestone)
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

        private fun goToMilestoneActivity(milestone: Milestone) {
            val intent = Intent(view.context, MilestoneActivity::class.java)
            intent.putExtra("milestone", milestone)
            intent.putExtra("goal", goal)
            startActivity(view.context, intent, null)
        }

        private fun goToReflectionActivity(milestone: Milestone) {
            val intent = Intent(view.context, ReflectionActivity::class.java)
            intent.putExtra("milestone", milestone)
            intent.putExtra("goal", goal)
            startActivity(view.context, intent, null)
        }
    }
}