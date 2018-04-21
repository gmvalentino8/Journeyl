package com.valentino.journeyl.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.valentino.journeyl.model.Goal
import com.valentino.journeyl.model.Milestone

/**
 * Created by Valentino on 4/6/18.
 */

object MilestoneDAO {
    private val mDatabase: DatabaseReference

    init {
        val mInstance = FirebaseDatabase.getInstance()
        mDatabase = mInstance.reference
    }

    fun getMilestone(mid: String, completion: (Milestone?)->Unit) {
        mDatabase.child("milestones").child(mid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                var milestone = p0?.getValue(Milestone::class.java)
                milestone?.mid = p0?.key
                completion(milestone)
            }
            override fun onCancelled(p0: DatabaseError?) {}
        })
    }

    fun getMilestones(gid: String, completionAdded: (Milestone?)->Unit, completionChanged: (Milestone?) -> Unit) {
        mDatabase.child("goal-milestones").child(gid).orderByChild("time").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                val key = p0?.key!!
                getMilestone(key, completionAdded)

                mDatabase.child("milestones").child(p0.key).addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError?) {}
                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
                    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                        Log.d("Child Changed", p0?.key)
                        getMilestone(key, completionChanged)
                    }
                    override fun onChildRemoved(p0: DataSnapshot?) {}
                    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {}
                })
            }
        })
    }

    fun postMilestone(gid: String, milestone: Milestone) {
        val midRef = mDatabase.child("milestones").push()
        val mid = midRef.key
        midRef.setValue(milestone)
        mDatabase.child("goal-milestones").child(gid).child(mid).setValue(0)
    }

    fun postReflection(milestone: Milestone) {
        val reflectionUpdate = hashMapOf(
                "completed" to true,
                "rating1" to milestone.rating1!!,
                "rating2" to milestone.rating2!!,
                "rating3" to milestone.rating3!!,
                "rating4" to milestone.rating4!!,
                "reflection" to milestone.reflection
        )
        mDatabase.child("milestones").child(milestone.mid).updateChildren(reflectionUpdate)
    }


    fun getRelatedMilestones(goal: Goal, completion: (Pair<Milestone, Goal>) -> Unit) {
        GoalDAO.getSimilarGoals(goal) {goalList ->
            for (goalItem in goalList) {
                mDatabase.child("goal-milestones").child(goalItem.gid).addListenerForSingleValueEvent( object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {}
                    override fun onDataChange(p0: DataSnapshot?) {
                        for (milestone in p0?.children?.iterator()!!) {
                            getMilestone(milestone.key) {
                                Log.d("Similar Milestones", "Return item: $it, $goalItem")
                                completion(Pair(it!!, goalItem))
                            }
                        }
                    }
                })
            }
        }
    }

}