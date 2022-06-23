package com.example.chatbox


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {
    private var fAuth: FirebaseAuth? = null
    lateinit var profileEmail: TextView
    lateinit var profileName: TextView
    private lateinit var pic: CircleImageView
    private lateinit var button: Button
    private var db : DatabaseReference? = null
    private var fStore: FirebaseStorage? = null
    private var stgRef: StorageReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        profileEmail = findViewById(R.id.userEmail)
        profileName = findViewById(R.id.userName)
        pic = findViewById(R.id.imgProfile)
        button = findViewById(R.id.updateP)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseStorage.getInstance()


        button.setOnClickListener {
            val intent = Intent(this, ProfileUpdateActivity::class.java)
            startActivity(intent)
        }
        getUserData()

    }


    private fun getUserData() {
        db = FirebaseDatabase.getInstance().getReference("user").child(fAuth!!.uid!!)
        db?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    val user = p0.getValue(User::class.java)
                    profileName.text = user!!.getUserName()
                    profileEmail.text = user.getUserEmail()
                    profile()

                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@ProfileActivity, p0.code, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun profile() {
        stgRef = fStore!!.getReference(fAuth!!.uid!!).child("image/").child("Profile")
        stgRef!!.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).into(pic)
        }
    }

}
