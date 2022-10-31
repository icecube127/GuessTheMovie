package edu.utap.guessthemovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import edu.utap.guessthemovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        var globalDebug = false
    }

    private lateinit var userName: String
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        binding = activityMainBinding

        binding.btnSignin.setOnClickListener {
            Log.d("XXXX", "This should go to sign page")
            // XXXXX WRITE ME
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