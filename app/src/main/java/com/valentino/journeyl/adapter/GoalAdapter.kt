package com.valentino.journeyl.adapter

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.valentino.journeyl.R
import com.valentino.journeyl.activity.MilestoneActivity
import com.valentino.journeyl.activity.RoadmapActivity
import com.valentino.journeyl.model.Goal
import com.valentino.journeyl.model.Milestone
import kotlinx.android.synthetic.main.item_goal.view.*
import kotlinx.android.synthetic.main.item_roadmap_vertical.view.*

/**
 * Created by Valentino on 4/7/18.
 */

class GoalAdapter(private val goalData: List<Goal>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return  goalData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = goalData[position]
        val placeHolder = holder as GoalViewHolder
        placeHolder.bindData(data)
    }

    class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindData(goal: Goal) {
            view.goalName.text = goal.description
            view.goalTags.text = goal.tag.joinToString()
            view.setOnClickListener({
                goToRoadmapActivity(goal)
            })
        }

        fun goToRoadmapActivity(goal: Goal) {
            val intent = Intent(view.context, RoadmapActivity::class.java)
            intent.putExtra("Roadmap", goal.gid)
            ContextCompat.startActivity(view.context, intent, null)
        }
    }
}