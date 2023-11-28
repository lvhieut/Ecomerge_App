package com.example.my_ecomerge_app.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.my_ecomerge_app.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Objects

class SignUpActivity : AppCompatActivity() {

    private val signUpBinding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private lateinit var firebaseAuth1: FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(signUpBinding.root)

        firebaseAuth1 = FirebaseAuth.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()

        signUpBinding.btnSignUp.setOnClickListener {
            val email = signUpBinding.edtEmail.text.toString()
            val pass = signUpBinding.edtPassword.text.toString()
            val fullname = signUpBinding.edtFullname.text.toString()
            val confirmPass = signUpBinding.edtConfirmPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth1.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        val user: FirebaseUser? = firebaseAuth1.currentUser
                        val df : DocumentReference = firebaseStore.collection("Users").document(user?.uid.toString())
                        val userInfo = mutableMapOf<String, Any>()
                        userInfo.put("UserEmail" , email)
                        userInfo.put("UserPassword" , pass)
                        userInfo.put("FullName" , fullname)
                        userInfo.put("isUser" , "2")
                        df.set(userInfo)


                        if (it.isSuccessful) {
                            val intent = Intent(this, LoginActivity::class.java)
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            Log.d("---", it.exception.toString())
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        signUpBinding.tvAlreadyAcc.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}