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
        ratingOneSeekbar.setProgress(milestone?.rating1!!, true)
        ratingTwoSeekbar.setProgress(milestone.rating2!!, true)
        ratingThreeSeekbar.setProgress(milestone.rating3!!, true)
        ratingFourSeekbar.setProgress(milestone.rating4!!, true)
        reflectionEditText.setText(milestone.reflection)
    }
}
