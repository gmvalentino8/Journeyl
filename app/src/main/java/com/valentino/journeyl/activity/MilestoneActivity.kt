package com.valentino.journeyl.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.valentino.journeyl.R
import android.view.Gravity
import android.view.ViewGroup
import android.util.DisplayMetrics
import com.valentino.journeyl.dao.MilestoneDAO
import com.valentino.journeyl.model.Goal
import com.valentino.journeyl.model.Milestone
import kotlinx.android.synthetic.main.activity_milestone.*


class MilestoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_milestone)
        val milestone = intent.getParcelableExtra<Milestone>("milestone")
        val goal = intent.getParcelableExtra<Goal>("goal")
        goalName.text = goal.description
        milestoneName.text = milestone.description
        milestone?.rating1?.let { ratingOneSeekbar.setProgress(it, true) }
        milestone.rating2?.let { ratingTwoSeekbar.setProgress(it, true) }
        milestone.rating3?.let { ratingThreeSeekbar.setProgress(it, true) }
        //ratingFourSeekbar.setProgress(milestone.rating4!!, true)
        reflectionEditText.setText(milestone.reflection)
    }
}
