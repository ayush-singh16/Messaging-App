package com.example.chatbox

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ProfileUpdateActivity : AppCompatActivity() {

    private var edName: EditText? = null
    private var edEmail: EditText? = null
    private var fAuth: FirebaseAuth? = null
    private var PICK_IMAGE_REQUEST = 10
    private var imagePath: Uri? = null
    private var fStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var db : DatabaseReference?= null
    private lateinit var btnS: Button
    private lateinit var imgB: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updateprofile)
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        db = FirebaseDatabase.getInstance().reference
        btnS = findViewById(R.id.btnSave)
        edName = findViewById(R.id.profileName)
        edEmail = findViewById(R.id.profileEmail)
        imgB = findViewById(R.id.imgProfileImage)
        imgB.setOnClickListener {
            fileChooser()
        }
        btnS.setOnClickListener {
           update()
           Toast.makeText(this,"Profile Updated Successful",Toast.LENGTH_SHORT).show()

        }
    }
    private fun fileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data!= null && data.data!= null){
            imagePath = data.data
            Picasso.get().load(imagePath).into(imgB)
        }
    }
    private fun update(){
        val name = edName?.text.toString()
        val email = edEmail?.text.toString()


        if (name.isEmpty() || email.isEmpty() ) {
            Toast.makeText(this, "Fill all field ", Toast.LENGTH_SHORT).show()
        } else {

            val profilePicture = User(name, email,uid = fAuth!!.currentUser?.uid)
            db?.child("user")?.child(fAuth!!.currentUser?.uid!!)?.setValue(profilePicture)

            val imageReference = fStore!!.reference.child(fAuth!!.uid!!)
                .child("image").child("Profile")

            val uploadImage = imageReference.putFile(imagePath!!)
            uploadImage.addOnFailureListener{
                Toast.makeText(this,"Error occured",Toast.LENGTH_SHORT).show()
            }

        }

    }
}

