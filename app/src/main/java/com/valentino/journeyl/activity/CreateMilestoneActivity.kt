package com.valentino.journeyl.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.valentino.journeyl.R
import com.valentino.journeyl.adapter.MilestoneAdapter
import com.valentino.journeyl.adapter.RoadmapAdapter
import com.valentino.journeyl.dao.MilestoneDAO
import com.valentino.journeyl.model.Goal
import com.valentino.journeyl.model.Milestone
import kotlinx.android.synthetic.main.activity_create_milestone.*
import java.util.*
import android.support.v7.widget.DividerItemDecoration



class CreateMilestoneActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: MilestoneAdapter
    var relatedMilestoneData = arrayListOf<Pair<Milestone, Goal>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_milestone)
        val goal = intent.getParcelableExtra<Goal>("goal")
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        suggestionsRecyclerView.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(suggestionsRecyclerView.context,
                linearLayoutManager.orientation)
        suggestionsRecyclerView.addItemDecoration(dividerItemDecoration)
        adapter = MilestoneAdapter(relatedMilestoneData)
        suggestionsRecyclerView.adapter = adapter

        MilestoneDAO.getRelatedMilestones(goal) {
            relatedMilestoneData.add(it)
            adapter.notifyDataSetChanged()
        }

        createButton.setOnClickListener({
            val milestone = Milestone(null, descriptionEditText.text.toString(), Date().time)
            MilestoneDAO.postMilestone(goal.gid!!, milestone)
            finish()
        })
    }
}
