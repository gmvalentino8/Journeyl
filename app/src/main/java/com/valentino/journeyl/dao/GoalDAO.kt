package com.valentino.journeyl.dao

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

    fun getTags(goal: Goal, completion: (String) -> Unit) {
        mDatabase.child("goal-tags").child(goal.gid).addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                completion(p0?.key!!)
            }
        })
    }

    fun getSimilarGoals(goal: Goal) {
        getTags(goal) {

        }
    }

}