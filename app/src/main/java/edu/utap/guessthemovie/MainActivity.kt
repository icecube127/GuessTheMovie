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

    private lateinit var binding: ActivityMainBinding

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            viewModel.updateUser()
            playGame()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        binding = activityMainBinding

        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            binding.btnSignin.text = "Sign In"
            binding.btnGuest.text = "Play as Guest"
        } else{
            binding.btnSignin.text = "Sign Out"
            binding.btnGuest.text = "Play"
        }

        binding.btnSignin.setOnClickListener {
            Log.d("XXXX", "This should go to sign page")
            // XXXXX WRITE ME
            val user = FirebaseAuth.getInstance().currentUser
            if(user == null){
                println( "...... In log in  ${user?.displayName} email ${user?.email}")
                AuthInit(viewModel, signInLauncher)
//                viewModel.createScoreMeta()
            } else {
                viewModel.signOut()
            }
        }

        binding.btnGuest.setOnClickListener {
            playGame()
        }
    }

    private fun playGame() {
        val selectionScreenIntent = Intent(this, ProfilePage::class.java)
        startActivity(selectionScreenIntent)
    }
}