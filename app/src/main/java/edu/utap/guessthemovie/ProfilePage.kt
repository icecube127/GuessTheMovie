package edu.utap.guessthemovie

import android.R
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import edu.utap.guessthemovie.databinding.ProfileMainBinding

class ProfilePage : AppCompatActivity() {

    class LeaderBoard(var name: String, var score: Int){
        override fun toString(): String {
                return this.name + ": " + this.score
        }
    }
    private var highScores = mutableListOf<LeaderBoard>()

    private lateinit var binding : ProfileMainBinding
    private var userName = ""
    private val guestNames = listOf<String>("Captain America", "Ironman", "Thor", "Hulk", "Blackwindow", "Hawkeye", "Nick Fury")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityThatCalled = intent
        // display the player's name
        userName = activityThatCalled.extras?.getString("username").toString()
        if (userName == "guest") {
            userName = randomName()
        }

        binding.tvName.hint = userName
        binding.tvName.isEnabled = false

        binding.btnEditName.setOnClickListener {
            if(binding.btnEditName.text == "Edit") {
                binding.btnEditName.text = "Submit"
                binding.tvName.isEnabled = true
            } else {
                if (binding.tvName.text.toString() != "")
                    userName = binding.tvName.text.toString()
                binding.tvName.hint = userName
                binding.btnEditName.text = "Edit"
                binding.tvName.isEnabled = false

                // XXXXX WRITE ME
                // need to grab user score and name from DB
            }
        }

        // show leaderboard
        binding.starLeft.setImageResource(android.R.drawable.btn_star_big_on)
        binding.starRight.setImageResource(android.R.drawable.btn_star_big_on)
        setupLeaderBoard()
        this.renderHighScores()

        // PLAY GAME button
        binding.btnPlay.setOnClickListener {
            val gameIntent = Intent(this, Game::class.java)
            // XXXXX REMOVE ME LATER
            val userScore : Int = (0..100).random()

            gameIntent.putExtra("userScore", userScore)
            gameIntent.putExtra("userName", userName)
            startActivity(gameIntent)
        }
    }

    private fun setupLeaderBoard(){
        // XXXXX WRITE ME
        // GET LEADER BOARD INFO FROM DB

        highScores.addAll(
            listOf(
                LeaderBoard("Black Adam", 997),
                LeaderBoard("Hawkman", 997),
                LeaderBoard("Dr. Fate", 13)
            )
        )
    }

    private fun renderHighScores() {
        // NEED to replace with get leaderboard info from DB
        highScores.sortWith(compareByDescending<LeaderBoard>{it.score}.thenBy{it.name})
        // Convert Score objects into a list of strings
        val stringList = highScores.map { it.toString() }
        // A simple way to display lists
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, stringList)
        binding.highScoreList.adapter = adapter
    }

    private fun randomName() : String {
        val randomNamePosition = (0..6).random()
        val randomNumber = (100..999).random()
        return guestNames[randomNamePosition] + " " + randomNumber.toShort()
    }
}