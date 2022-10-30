package edu.utap.guessthemovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import edu.utap.guessthemovie.databinding.ActivityMainBinding
import kotlinx.coroutines.selects.select

class MainActivity : AppCompatActivity() {
    companion object {
        var globalDebug = false
    }

    private lateinit var userName: String
    private lateinit var binding: ActivityMainBinding
    private val guestNames = listOf<String>("Captain America", "Ironman", "Thor", "Hulk", "Blackwindow", "Hawkeye", "Nick Fury")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        binding = activityMainBinding

        binding.btnGuest.setOnClickListener {
            play()
        }
    }

    private fun play() {
        val randomNamePosition = (0..6).random()
        val randomNumber = (100..999).random()
        userName = guestNames[randomNamePosition] + " " + randomNumber.toShort()

        val selectionScreenIntent = Intent(this, SelectionPage::class.java)
        selectionScreenIntent.putExtra("username", userName)
        startActivity(selectionScreenIntent)
    }
}