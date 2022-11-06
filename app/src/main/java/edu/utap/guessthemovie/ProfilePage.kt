package edu.utap.guessthemovie

import android.R
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import edu.utap.guessthemovie.databinding.ProfileMainBinding



class ProfilePage : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    class LeaderBoard(var name: String, var score: Int){
        override fun toString(): String {
                return this.name + ": " + this.score
        }
    }
    private var highScores = mutableListOf<LeaderBoard>()

    private lateinit var binding : ProfileMainBinding
    private var userName = ""
    private val guestNames = listOf<String>("Captain America", "Ironman", "Thor", "Hulk", "Blackwindow", "Hawkeye", "Nick Fury")

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
                result ->
            if (result.resultCode == RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null){
                    viewModel.updateUser()
                }
                // ...
            } else {
                Log.d("XXXXXX", "Sign in error")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var musicStatus = false
        musicStatus = intent.getBooleanExtra("music", musicStatus)
        binding.btnMusic.isChecked = musicStatus

        // display the player's name
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            userName = user.displayName.toString()
        }else {
            userName = randomName()
        }
        binding.tvName.hint = userName
        binding.tvName.isEnabled = false

        // this set up editing user name
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
                AuthInit.setDisplayName(userName, viewModel)
            }
        }

        // set up the sign in and out button
        if(user == null) {
            binding.btnSignIn.text = "Sign In"
        } else {
            binding.btnSignIn.text = "Sign Out"
        }
        binding.btnSignIn.setOnClickListener {
            if (user == null) {
                val mainActivityIntent = Intent(this, MainActivity::class.java)
                startActivity(mainActivityIntent)
            } else {
                viewModel.signOut()
                val mainActivityIntent = Intent(this, MainActivity::class.java)
                startActivity(mainActivityIntent)
            }
        }

        // show leaderboard
        binding.starLeft.setImageResource(android.R.drawable.btn_star_big_on)
        binding.starRight.setImageResource(android.R.drawable.btn_star_big_on)
        setupLeaderBoard()

        // PLAY GAME button
        binding.btnPlay.setOnClickListener {
            val gameIntent = Intent(this, Game::class.java)
            val music = binding.btnMusic.isChecked
            gameIntent.putExtra("music", music)
            startActivity(gameIntent)
        }
    }

    private fun setupLeaderBoard(){
        // GET LEADER BOARD INFO FROM DB
        viewModel.fetchScoreMeta()
        viewModel.observeScoreMeta().observe(this) {
            for (item in it) {
                highScores.add(LeaderBoard(item.name, item.score))
            }
            renderHighScores()
        }
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