package com.valentino.journeyl.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.valentino.journeyl.R
import com.valentino.journeyl.dao.MilestoneDAO
import com.valentino.journeyl.model.Goal
import com.valentino.journeyl.model.Milestone
import kotlinx.android.synthetic.main.activity_reflection.*

class ReflectionActivity : AppCompatActivity() {
    var levels = arrayOf("Beginner", "Intermediate", "Advanced")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reflection)
        var milestone = intent.getParcelableExtra<Milestone>("milestone")
        var goal = intent.getParcelableExtra<Goal>("goal")
        goalName.text = goal.description
        milestoneName.text = milestone.description

        ratingFourSpinner.adapter = ArrayAdapter(this,
                                                    R.layout.item_spinner, levels)

        submitButton.setOnClickListener({
            milestone.rating1 = ratingOneSeekbar.progress
            milestone.rating2 = ratingTwoSeekbar.progress
            milestone.rating3 = ratingThreeSeekbar.progress
            milestone.rating4 = ratingFourSpinner.selectedItem?.toString()
            milestone.reflection = reflectionEditText.text.toString()
            MilestoneDAO.postReflection(milestone)
            finish()
        })
    }
}
