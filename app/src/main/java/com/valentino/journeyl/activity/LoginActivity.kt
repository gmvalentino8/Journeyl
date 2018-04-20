package com.valentino.journeyl.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.valentino.journeyl.R
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.text.TextUtils
import android.widget.Toast
import android.support.annotation.NonNull
import com.google.firebase.auth.AuthResult
import android.app.ProgressDialog
import android.content.Intent
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.valentino.journeyl.dao.UserDAO


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    var mProgressDialog: ProgressDialog? = null
    private var mAuth: FirebaseAuth? = null
    

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        loginButton.setOnClickListener(this)
        registerButton.setOnClickListener(this)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.currentUser
        updateUI(currentUser)
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog();
    }

    private fun createAccount(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START create_user_with_email]
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth?.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(applicationContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    hideProgressDialog()
                }
        // [END create_user_with_email]
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        // [START sign_in_with_email]
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth?.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(applicationContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // [START_EXCLUDE]
                    if (!task.isSuccessful) {
                        Toast.makeText(applicationContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()                        }
                    hideProgressDialog()
                    // [END_EXCLUDE]
                }
        // [END sign_in_with_email]
    }

    private fun signOut() {
        mAuth?.signOut()
        updateUI(null)
    }
    

    private fun validateForm(): Boolean {
        var valid = true

        val email = emailEditText.getText().toString()
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Required.")
            valid = false
        } else {
            emailEditText.setError(null)
        }

        val password = passwordEditText.getText().toString()
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required.")
            valid = false
        } else {
            passwordEditText.setError(null)
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressDialog()
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(v: View) {
        when (v) {
            loginButton -> {
                signIn(emailEditText.getText().toString(), passwordEditText.getText().toString())
            }
            registerButton -> {
                createAccount(emailEditText.text.toString(), passwordEditText.text.toString())
            }
        }
    }

    private fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog?.setMessage("Loading")
            mProgressDialog?.isIndeterminate = true
        }

        mProgressDialog?.show()
    }

    private fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog?.isShowing()!!) {
            mProgressDialog?.dismiss()
        }
    }

}
