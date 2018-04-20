package com.valentino.journeyl.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.valentino.journeyl.model.Goal

/**
 * Created by Valentino on 4/6/18.
 */

object GoalDAO {
    private val mDatabase: DatabaseReference
    val currentUser: FirebaseUser?

    init {
        val mInstance = FirebaseDatabase.getInstance()
        mDatabase = mInstance.reference
        currentUser = FirebaseAuth.getInstance().currentUser
    }

    fun getGoal(gid: String, completion: (Goal?)->Unit) {
        mDatabase.child("goals").child(gid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                var goal = p0?.getValue(Goal::class.java)
                goal?.gid = p0?.key
                completion(goal)
            }
            override fun onCancelled(p0: DatabaseError?) {}
        })
    }

    fun getGoals(completion: (Goal?)->Unit) {
        mDatabase.child("user-goals").child(currentUser?.uid).orderByChild("time").addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                getGoal(p0?.key!!, completion)
            }
        })
    }

    fun postGoal(goal: Goal, tags: ArrayList<String>) {
        val gidRef = mDatabase.child("goals").push()
        val gid = gidRef.key
        gidRef.setValue(goal)
        mDatabase.child("user-goals").child(currentUser?.uid).child(gid).setValue(0)
        for (tag in tags) {
            mDatabase.child("goal-tags").child(gid).child(tag).setValue(0)
            mDatabase.child("tag-goals").child(tag).child(gid).setValue(0)
        }
    }

    fun getTags(goal: Goal, completion: (List<String>) -> Unit) {
        var tags = arrayListOf<String>()
        mDatabase.child("goal-tags").child(goal.gid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                Log.d("Returning tags", tags.toString())
                completion(tags)
            }
            override fun onCancelled(p0: DatabaseError?) {}
        })
        mDatabase.child("goal-tags").child(goal.gid).addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                tags.add(p0?.key!!)
                Log.d("Added Tag", tags.toString())
            }
        })
    }

    fun getSimilarGoals(goal: Goal, completion: (List<Goal>) -> Unit) {
        getSimilarGoalList(goal) { goalsList->
            mDatabase.child("goals").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot?) {
                    val goals = ArrayList<Goal>()
                    val goalsMap = p0?.children
                    for (child in goalsMap!!.iterator()) {
                        val item = child.getValue(Goal::class.java)
                        item?.gid = child.key
                        goals.add(item!!)
                    }
                    Log.d("Similar Goals", "Snapshot: $goals")
                    completion(goals)
                }
                override fun onCancelled(p0: DatabaseError?) {}
            })
        }
    }

    fun getSimilarGoalList(goal: Goal, completion: (List<String>) -> Unit) {
        var goalHashMap = HashMap<String, Int>()
        getTags(goal) {tags ->
            mDatabase.child("tag-goals").addChildEventListener(object: ChildEventListener{
                override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                    if (p0?.key in tags) {

                        val goals = p0?.value as HashMap<*, *>
                        for (goal in goals.keys) {
                            if (goal in goalHashMap.keys) {
                                goalHashMap[goal as String] = goalHashMap[goal]?.plus(1)!!
                            } else {
                                goalHashMap[goal as String] = 1
                            }
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError?) {}
                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
                override fun onChildRemoved(p0: DataSnapshot?) {}
            })
            mDatabase.child("tag-goals").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot?) {
                    val goalList = ArrayList<String>()
                    for (pair in goalHashMap.toList().sortedByDescending { (_, value) -> value }) {
                        goalList.add(pair.first)
                    }
                    Log.d("Similar Goals Map", goalHashMap.toString())
                    completion(goalList)
                }
                override fun onCancelled(p0: DatabaseError?) {}
            })
        }
    }

}