package com.example.chatbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtName: EditText
    private lateinit var signBtn: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mAuth = FirebaseAuth.getInstance()

        edtEmail= findViewById(R.id.Email)
        edtPassword = findViewById(R.id.Password)
         edtName= findViewById(R.id.Name)
        signBtn= findViewById(R.id.button3)

        signBtn.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()
            if (TextUtils.isEmpty(email)) {
                edtEmail.error = " Email is Required To Create Account"
            } else if (TextUtils.isEmpty(password)) {
                edtPassword.error = "Password is required to Create Account"
            } else
                if (password.length < 8) {
                    edtPassword.error = "Password must be greater than 8 characters in size"
                }
            signUP(name,email,password)
        }


    }
    private fun signUP(name:String,email: String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUsertoDatabase(name, email, password)
                    val  intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignupActivity,"SOME ERROR", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUsertoDatabase(name: String, email: String, uid: String?){
        mDbRef = FirebaseDatabase.getInstance().reference

        mDbRef.child("user").child(mAuth.currentUser?.uid!!).setValue(User(name,email,uid))


    }

}
