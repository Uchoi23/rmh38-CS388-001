package com.example.tapit

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView)
        val upgrade1 = findViewById<Button>(R.id.doubleTapUpgrade)
        val upgrade2 = findViewById<Button>(R.id.changeCustomButton)
        val upgradeShopTitle = findViewById<EditText>(R.id.shopTitle)
        val tapButton = findViewById<Button>(R.id.tappedButton)
        val tapButtonParams = tapButton.layoutParams
        val goalText = findViewById<TextView>(R.id.goalText)
        var goal = 100
        var doubleTapUpgrade = false
        var changeButtonUpgrade = false
        var taps = 0

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            goalText.text = "Goal: $goal"
            tapButton.setOnClickListener { v->
                if(doubleTapUpgrade){
                    taps += 2
                } else {
                    taps++
                }
                if(taps >= 100){
                    if(doubleTapUpgrade == false){
                        upgrade1.visibility = View.VISIBLE
                    }
                    if(changeButtonUpgrade == false){
                        upgrade2.visibility = View.VISIBLE
                    }
                    upgradeShopTitle.visibility = View.VISIBLE
                } else {
                    upgrade1.visibility = View.GONE
                    upgrade2.visibility = View.GONE
                    upgradeShopTitle.visibility = View.GONE
                }
                if(taps == goal){
                    goal += 100
                    goalText.text = "Goal: $goal"
                    Toast.makeText(this, "Goal reached!", Toast.LENGTH_SHORT).show()
                }
                textView.text = "Tapped: $taps"
            }
            upgrade1.setOnClickListener { v->
                upgrade1.visibility = View.GONE
                doubleTapUpgrade = true
                taps -= 100
                textView.text = "Tapped: $taps"
                Toast.makeText(this, "Double Tap Bought!", Toast.LENGTH_SHORT).show()
            }
            upgrade2.setOnClickListener { v->
                tapButton.setBackgroundResource(R.drawable.burger_vector)
                tapButtonParams.width = 200
                tapButtonParams.height = 200
                upgrade2.visibility = View.GONE
                changeButtonUpgrade = true
                taps -= 100
                textView.text = "Tapped: $taps"
                Toast.makeText(this, "Change Icon Bought!", Toast.LENGTH_SHORT).show()
            }

            insets
        }
    }
}