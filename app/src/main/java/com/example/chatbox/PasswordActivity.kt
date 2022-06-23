package com.example.chatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class PasswordActivity : AppCompatActivity() {

    private var fAuth: FirebaseAuth?=null
    private lateinit var btnReset : AppCompatButton
    private lateinit var resert : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        fAuth = FirebaseAuth.getInstance()
        btnReset  = findViewById(R.id.R_password)
        resert = findViewById(R.id.Re_email)

        btnReset.setOnClickListener{
            resetPass()
        }

    }

    private fun resetPass(){
        val emailR = resert.text.toString().trim { it <= ' ' }
        if(emailR.isEmpty()){
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show()
        }
        fAuth?.sendPasswordResetEmail(emailR)?.addOnCompleteListener { task->
           if (task.isSuccessful){
               Toast.makeText(this, "Reset Email Send", Toast.LENGTH_SHORT).show()
           }
            else{
               Toast.makeText(this@PasswordActivity, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
           }
        }
    }

}