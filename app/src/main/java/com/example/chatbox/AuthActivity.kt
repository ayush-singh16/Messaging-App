package com.example.chatbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUpBtn: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var Fp : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mAuth = FirebaseAuth.getInstance()

        Fp = findViewById(R.id.tvForgetPassword)
        edtEmail= findViewById(R.id.Email)
        edtPassword = findViewById(R.id.Password)
        loginBtn = findViewById(R.id.button1)
        signUpBtn= findViewById(R.id.button2)


        loginBtn.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email,password)
        }


        signUpBtn.setOnClickListener{
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
        Fp.setOnClickListener {
            startActivity(Intent( this,PasswordActivity::class.java))
        }

    }


    private fun login(email: String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val  intent = Intent(this@AuthActivity, HomeActivity::class.java)
                    startActivity(intent)


                } else {
                    Toast.makeText(this@AuthActivity,"USER DOES NOT EXIT", Toast.LENGTH_SHORT).show()

                }
            }

    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(this)
        if (FirebaseAuth.getInstance().currentUser?.uid != null) {
            startMainActivity()
        }
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        if (p0.currentUser?.uid != null) {
            startMainActivity()
        }
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(this)
    }

    private fun startMainActivity() {
        val intent = Intent(this@AuthActivity, HomeActivity::class.java)
        startActivity(intent)
    }
}





