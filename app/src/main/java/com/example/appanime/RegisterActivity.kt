package com.example.appanime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appanime.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var binding:ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init Firebase
        firebaseAuth= FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            //steps input-validate-createaccount- save user in database

            createUserAccount()
        }
    }


    private fun createUserAccount() {
        var email = binding.edtRegEmail.text.toString().trim()
        var password = binding.edtRegPassword.text.toString().trim()
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                updateUserInfo()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Fallo al crear cuenta", Toast.LENGTH_LONG).show()
            }
    }

    private fun updateUserInfo() {
        var email = binding.edtRegEmail.text.toString().trim()
        val uid = firebaseAuth.uid
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"]= uid
        hashMap["email"]= email
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!).setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Cuenta Creada...", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginAcitivy::class.java))
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Fallo al guardar el usuario", Toast.LENGTH_LONG).show()
            }
    }
}