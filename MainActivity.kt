package com.saber.sabernews.presentation.features.news

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.saber.sabernews.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

const val VS_PLAYER = 0
const val VS_COMPUTER = 1

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var counter = 0
    var reset = false
    var playerOption = VS_PLAYER
    private var winPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    var xPositions = arrayListOf<Int>()
    var oPositions = arrayListOf<Int>()
    var buttons = arrayListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).apply { binding = this }.root)
        setupPositions()
        binding.buStart.setOnClickListener {
            playerOption = binding.spGameOption.selectedItemPosition
            reset()
        }
        binding.bu0.setOnClickListener {
            buttonClicked(0, it)
        }
        binding.bu1.setOnClickListener {
            buttonClicked(1, it)

        }
        binding.bu2.setOnClickListener {
            buttonClicked(2, it)

        }
        binding.bu3.setOnClickListener {
            buttonClicked(3, it)

        }
        binding.bu4.setOnClickListener {
            buttonClicked(4, it)

        }
        binding.bu5.setOnClickListener {
            buttonClicked(5, it)

        }
        binding.bu6.setOnClickListener {
            buttonClicked(6, it)

        }
        binding.bu7.setOnClickListener {
            buttonClicked(7, it)

        }
        binding.bu8.setOnClickListener {
            buttonClicked(8, it)

        }
    }

    private fun buttonClicked(num: Int, view: View) {
        if (reset)
            reset()
        (view as Button).text =
            if (counter % 2 == 0) {
                xPositions.add(num)
                if (playerOption == VS_COMPUTER) {
                    computerClick()
                }
                "X"
            } else {
                oPositions.add(num)
                "O"
            }
        counter++
        if (counter == 9) {
            binding.grOptions.visibility = View.VISIBLE
        } else if (counter >= 5) {
            checkWin()
        }

    }

    private fun reset() {
        xPositions.clear()
        oPositions.clear()
        reset = false
        counter = 0
        buttons.map { it.text = "" }
        binding.grButtons.visibility = View.VISIBLE
        binding.grOptions.visibility = View.GONE
    }

    private fun checkWin() {
        for (element in winPositions) {
            if (xPositions.intersect(element.toList()).size == 3) {
                binding.grOptions.visibility = View.VISIBLE
                reset = true
                Toast.makeText(this, "X Wins", Toast.LENGTH_LONG).show()
            } else if (oPositions.intersect(element.toList()).size == 3) {
                binding.grOptions.visibility = View.VISIBLE
                reset = true
                Toast.makeText(
                    this,
                    if (playerOption == VS_COMPUTER) "computer Wins" else "O Wins",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupPositions() {
        buttons.add(binding.bu0)
        buttons.add(binding.bu1)
        buttons.add(binding.bu2)
        buttons.add(binding.bu3)
        buttons.add(binding.bu4)
        buttons.add(binding.bu5)
        buttons.add(binding.bu6)
        buttons.add(binding.bu7)
        buttons.add(binding.bu8)
    }

    private fun computerClick() {
        lifecycleScope.launch {
            delay(1000)
            randomClick()
        }
    }

    private fun randomClick() {
        var again = true
        var selected: Int
        while (again) {
            selected = Random().nextInt(9)
            if (buttons[selected].text.isEmpty()) {
                again = false
                buttonClicked(selected, buttons[selected])
            }
        }
    }
}