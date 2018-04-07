package com.valentino.journeyl.dao

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.json.JSONException
import java.io.ByteArrayOutputStream

/**
 * Created by Valentino on 4/6/18.
 */

object UserDAO {
    private val mDatabase : DatabaseReference
    private val mStorage : StorageReference
    val currentUser : FirebaseUser?
    var uid: String = ""

    init {
        val mInstance = FirebaseDatabase.getInstance()
        mInstance.setPersistenceEnabled(true)
        mDatabase = mInstance.reference
        mStorage = FirebaseStorage.getInstance().reference
        currentUser = FirebaseAuth.getInstance().currentUser
    }


    fun getUser(uid: String) {
        mDatabase.child("users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {}
            override fun onCancelled(p0: DatabaseError?) {}
        })
    }

    fun getUserFromEmail(email: String, finish: ()-> Unit) {
        mDatabase.child("users").orderByChild("email").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                finish()
            }
            override fun onCancelled(p0: DatabaseError?) {}
        })
        mDatabase.child("users").orderByChild("email").equalTo(email).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {}
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}
        })
    }

    fun postUser(email: String) {
        mDatabase.child("users").child(currentUser?.uid).setValue(email)
    }

}