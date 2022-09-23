package com.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lugares.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    // definicion de objeto para hacer la autenticacion
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btRegister.setOnClickListener { haceRegistro() }

        binding.btLogin.setOnClickListener { haceLogin() }


    }

    private fun haceLogin() {

        //recuperamos la informacion que ingreso el usuario
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        //se llama a la funcion para crear un usuario en firebase (correo/contrasena)
        auth.signInWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? = null
                if (task.isSuccessful) {//si pudo crear el usuario//\

                    Log.d("Autenticando", "usuario autenticado")
                    user = auth.currentUser //recupero la info del usuario creado


                } else {
                    Log.d("Autenticando", "error autenticando usuario ")

                }
                actualiza(user)
            }


    }

    private fun haceRegistro() {
//recuperamos la informacion que ingreso el usuario
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        //se llama a la funcion para crear un usuario en firebase (correo/contrasena)
        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? = null
                if (task.isSuccessful) {//si pudo crear el usuario//\

                    Log.d("Autenticando", "usuario creado")
                    user = auth.currentUser //recupero la info del usuario creado


                } else {
                    Log.d("Autenticando", "error creando usuario ")

                }

                actualiza(user)
            }

    }


    private fun actualiza(user: FirebaseUser?) {
        //Si hay un usuario definido... se pasa a la pantalla principal (Activity)
        if (user != null) {
            //Se pasa a la otra pantalla
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }


    }

    public override fun onStart() {
        super.onStart()
        val usuario=auth.currentUser
        actualiza(usuario)
    }
}



