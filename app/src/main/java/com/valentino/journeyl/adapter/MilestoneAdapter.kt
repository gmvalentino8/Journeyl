package com.valentino.journeyl.adapter

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valentino.journeyl.R
import com.valentino.journeyl.activity.MilestoneActivity
import com.valentino.journeyl.activity.RoadmapActivity
import com.valentino.journeyl.model.Goal
import com.valentino.journeyl.model.Milestone
import kotlinx.android.synthetic.main.item_milestone.view.*

/**
 * Created by Valentino on 4/7/18.
 */

class MilestoneAdapter(private val data: List<Pair<Milestone, Goal>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_milestone, parent, false)
        return MilestoneViewHolder(root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val milestone = data[position].first
        val goal = data[position].second
        val placeHolder = holder as MilestoneViewHolder
        placeHolder.bindData(milestone, goal)
    }

    class MilestoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindData(milestone: Milestone, goal: Goal) {
            view.descriptionTextView.text = milestone.description
            view.setOnClickListener({
                goToMilestoneActivity(milestone, goal)
            })
        }

        fun goToMilestoneActivity(milestone: Milestone, goal: Goal) {
            val intent = Intent(view.context, MilestoneActivity::class.java)
            intent.putExtra("milestone", milestone)
            intent.putExtra("goal", goal)
            startActivity(view.context, intent, null)
        }
    }
}