package edu.utap.guessthemovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import androidx.activity.viewModels
import edu.utap.guessthemovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        var globalDebug = false
    }
    private val viewModel: MainViewModel by viewModels()

    private lateinit var userName: String
    private lateinit var binding: ActivityMainBinding

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            viewModel.updateUser()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        binding = activityMainBinding

        binding.btnSignin.setOnClickListener {
            Log.d("XXXX", "This should go to sign page")
            // XXXXX WRITE ME
            val user = FirebaseAuth.getInstance().currentUser
            if(user == null){
                println( "...... In log in  ${user?.displayName} email ${user?.email}")
                AuthInit(viewModel, signInLauncher)
            }
        }

        binding.btnGuest.setOnClickListener {
            playAsGuest()
        }
    }

    private fun playAsGuest() {
        userName = "guest"
        val selectionScreenIntent = Intent(this, ProfilePage::class.java)
        selectionScreenIntent.putExtra("username", userName)
        startActivity(selectionScreenIntent)
    }
}