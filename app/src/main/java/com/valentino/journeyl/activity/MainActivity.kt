package com.valentino.journeyl.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.valentino.journeyl.R
import com.valentino.journeyl.adapter.GoalAdapter
import com.valentino.journeyl.dao.GoalDAO
import com.valentino.journeyl.model.Goal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: GoalAdapter
    var goalData = arrayListOf<Pair<Goal, List<String>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        goalsRecyclerView.layoutManager = linearLayoutManager
        adapter = GoalAdapter(goalData)
        goalsRecyclerView.adapter = adapter
        GoalDAO.getGoals {goal ->
            GoalDAO.getTags(goal!!) {
                goalData.add(Pair(goal, it))
                Log.d("TAG", it.toString())
                adapter.notifyDataSetChanged()
            }
        }
        fab.setOnClickListener {
            val intent = Intent(this, CreateGoalActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_signoout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.signout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
