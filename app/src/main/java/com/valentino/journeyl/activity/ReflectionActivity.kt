package com.valentino.journeyl.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.valentino.journeyl.R
import com.valentino.journeyl.dao.MilestoneDAO
import com.valentino.journeyl.model.Goal
import com.valentino.journeyl.model.Milestone
import kotlinx.android.synthetic.main.activity_reflection.*

class ReflectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reflection)
        var milestone = intent.getParcelableExtra<Milestone>("milestone")
        var goal = intent.getParcelableExtra<Goal>("goal")
        goalName.text = goal.description
        milestoneName.text = milestone.description

        submitButton.setOnClickListener({
            milestone.rating1 = ratingOneSeekbar.progress
            milestone.rating2 = ratingTwoSeekbar.progress
            milestone.rating3 = ratingThreeSeekbar.progress
            milestone.rating4 = "TODDO"
            milestone.reflection = reflectionEditText.text.toString()
            MilestoneDAO.postReflection(milestone)
            finish()
        })
    }
}
