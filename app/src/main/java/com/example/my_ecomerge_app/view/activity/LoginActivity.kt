package com.example.my_ecomerge_app.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var progressDialog: ProgressDialog


    private val loginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)

        progressDialog = ProgressDialog(this)

        firebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this , gso)

        loginBinding.loginWithGg.setOnClickListener {
            signInGoogle()
        }

        loginBinding.tvCreateAcc.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBinding.btnLogin.setOnClickListener {
            val email = loginBinding.edtEmail.text.toString()
            val pass = loginBinding.edtPassword.text.toString()

            progressDialog.show()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful) {
                        checkUserAccessLevel(firebaseAuth.currentUser?.uid)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun checkUserAccessLevel(uid: String?) {
        val firebaseStore = FirebaseFirestore.getInstance()
        val df : DocumentReference? = uid?.let { firebaseStore.collection("Users").document(it) }
        if (df != null) {
            df.get().addOnSuccessListener {  documentSnapshot ->
                if (documentSnapshot != null) {
                    Log.d("---", "DocumentSnapshot data: ${documentSnapshot.data}")

                    if (documentSnapshot.get("isUser") != null){

                    val intent = Intent(this, AdminActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Log.d("---", "No such document")
                    val intent = Intent(this, HomeAcitivity::class.java)
                    startActivity(intent)
                }
            }
                .addOnFailureListener { exception ->
                    Log.d("---", "get failed with ", exception)
                }
        }
    }

    override fun onStart() {
        super.onStart()
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, HomeAcitivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }
    private fun handleResults(task: Task<GoogleSignInAccount>) {

        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        progressDialog.show()
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            progressDialog.dismiss()
            if (it.isSuccessful){
                val intent : Intent = Intent(this , HomeAcitivity::class.java)
                intent.putExtra("email" , account.email)
                intent.putExtra("name" , account.displayName)
                startActivity(intent)
            }else{
                Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()

            }
        }
    }
}