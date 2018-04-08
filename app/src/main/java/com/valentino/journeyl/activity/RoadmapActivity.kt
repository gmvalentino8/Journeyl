package com.valentino.journeyl.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.valentino.journeyl.R
import com.valentino.journeyl.adapter.RoadmapAdapter
import com.valentino.journeyl.model.Milestone
import kotlinx.android.synthetic.main.activity_roadmap.*
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.SnapHelper
import android.util.AttributeSet
import android.widget.ImageView
import com.valentino.journeyl.dao.MilestoneDAO
import com.valentino.journeyl.model.Goal
import java.util.*

class RoadmapActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RoadmapAdapter
    var milestoneData = arrayListOf<Milestone>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roadmap)
        val goal = intent.getParcelableExtra<Goal>("goal")
        roadmapToolbar.subtitle = goal.description

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        roadmapRecyclerView.layoutManager = linearLayoutManager
        adapter = RoadmapAdapter(milestoneData, goal)
        roadmapRecyclerView.adapter = adapter

        MilestoneDAO.getMilestones(goal.gid!!, {
            milestoneData.add(it!!)
            adapter.notifyDataSetChanged()
            if (milestoneData.size > 0) {
                roadmapRecyclerView.smoothScrollToPosition(milestoneData.size - 1)
            }
        }, {
            for (i in 0 until milestoneData.size) {
                if (milestoneData[i].mid == it?.mid) {
                    milestoneData[i] = it!!
                    adapter.notifyItemChanged(i)
                }
            }
        })

        fab.setOnClickListener {
            val intent = Intent(this, CreateMilestoneActivity::class.java)
            intent.putExtra("goal", goal)
            startActivity(intent)
        }
    }

}
