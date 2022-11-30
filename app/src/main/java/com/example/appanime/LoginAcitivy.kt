package com.example.appanime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appanime.databinding.ActivityLoginAcitivyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginAcitivy : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var binding: ActivityLoginAcitivyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAcitivyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init Firebase
        firebaseAuth= FirebaseAuth.getInstance()

        binding.txtRegistrarme.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            var email = binding.edtEmail.text.toString().trim()
            var pwd = binding.edtPassword.text.toString().trim()
            firebaseAuth.signInWithEmailAndPassword(email,pwd)
                .addOnSuccessListener {
                    val ref = FirebaseDatabase.getInstance().getReference("Users")
                    val firebaseUser = firebaseAuth.currentUser!!
                    ref.child(firebaseUser.uid)
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onCancelled(error: DatabaseError) {

                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                startActivity(Intent(this@LoginAcitivy, MainActivity::class.java))
                                finish()
                            }
                        })
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Fallo al iniciar Sesion",Toast.LENGTH_SHORT).show()
                }
        }
    }
}