package com.valentino.journeyl.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.valentino.journeyl.R
import com.valentino.journeyl.dao.GoalDAO
import com.valentino.journeyl.model.Goal

import kotlinx.android.synthetic.main.activity_create_goal.*
import java.util.*

class CreateGoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)
        createButton.setOnClickListener({
            val goal = Goal(null, nameEditText.text.toString(), Date().time)
            GoalDAO.postGoal(goal, tagsToArray(tagsEditText.text.toString()))
            finish()
        })
    }

    private fun tagsToArray(tags: String): ArrayList<String> {
        tags.replace("\\s".toRegex(),"")
        val tagList = tags.split(",")
        return ArrayList(tagList)
    }
}
