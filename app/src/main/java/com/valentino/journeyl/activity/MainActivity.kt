package com.valentino.journeyl.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.valentino.journeyl.R
import com.valentino.journeyl.adapter.GoalAdapter
import com.valentino.journeyl.model.Goal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: GoalAdapter
    var goalData = arrayListOf<Goal>(
            Goal("1", "Test 1", arrayListOf("Tag 1", "Tag 2", "Tag 3")),
            Goal("2", "Test 2", arrayListOf("Tag 1", "Tag 2", "Tag 3", "Tag 4")),
            Goal("3", "Test 3", arrayListOf("Tag 1", "Tag 2")),
            Goal("4", "Test 4", arrayListOf("Tag 1", "Tag 2", "Tag 3", "Tag 1", "Tag 2", "Tag 3"))
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        goalsRecyclerView.layoutManager = linearLayoutManager
        adapter = GoalAdapter(goalData)
        goalsRecyclerView.adapter = adapter
        goalsRecyclerView.smoothScrollToPosition(goalData.size - 1)
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
