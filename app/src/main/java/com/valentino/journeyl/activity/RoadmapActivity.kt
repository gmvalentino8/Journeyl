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

class RoadmapActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RoadmapAdapter
    var milestoneData = arrayListOf<Milestone>(
            Milestone("1", "Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 Test 1 ", 1.0),
            Milestone("2", "Test 2", 1.0),
            Milestone("3", "Test 3", 1.0),
            Milestone("4", "Test 4", 1.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roadmap)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        roadmapRecyclerView.layoutManager = linearLayoutManager
        adapter = RoadmapAdapter(milestoneData)
        roadmapRecyclerView.adapter = adapter
        roadmapRecyclerView.smoothScrollToPosition(milestoneData.size - 1)
    }

}
