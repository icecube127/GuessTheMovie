package edu.utap.guessthemovie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import edu.utap.guessthemovie.databinding.SelectionMainBinding

class SelectionPage : AppCompatActivity() {

    companion object{
        val TAG = this::class.java.simpleName
    }

    private lateinit var binding : SelectionMainBinding
    private var userName = ""
    private lateinit var optionYear : Spinner
    private lateinit var optionGenre : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectionMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityThatCalled = intent
        // display the player's name
        userName = activityThatCalled.extras?.getString("username").toString()
        binding.tvName.text = userName

        // set up the spinner aka the year and genre selection
        val yearSP = binding.yearSP
        val yearOptions = listOf("1980s", "1990s", "2000s", "2010s", "2020s")
        val yearSelection = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearOptions)
        yearSelection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                Log.d(TAG, "pos $position")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected")
            }
        }
        yearSP.adapter = yearSelection
        val initialSpinner = 1
        yearSP.setSelection(initialSpinner)

        val genreSP = binding.genreSP
        val genreOptions = listOf("Action", "Comedy", "Sci-fi", "Drama")
        val genreSelection = ArrayAdapter(this, android.R.layout.simple_spinner_item, genreOptions)
        genreSelection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genreSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                Log.d(TAG, "pos $position")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected")
            }
        }
        genreSP.adapter = genreSelection
        genreSP.setSelection(initialSpinner)

        // once selection is made, go to GAME
        binding.btnPlay.setOnClickListener {
            val gameIntent = Intent(this, Game::class.java)
            startActivity(gameIntent)
        }
    }
}