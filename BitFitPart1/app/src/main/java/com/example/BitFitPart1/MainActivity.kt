package com.example.BitFitPart1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.codepath.articlesearch.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var addEntry: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // define your fragments here
        val entryFragment: Fragment = Entry()
        val navigationFragment: Fragment = Navigation()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        addEntry = findViewById(R.id.addButton)
        addEntry.setOnClickListener {
            val context = it.context
            val intent = Intent(context, AddEntry::class.java)
            context.startActivity(intent)
        }

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.entries_item -> fragment = entryFragment
                R.id.summary_item -> fragment = navigationFragment
            }
            replaceFragment(fragment)
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.entries_item

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.logs_frame_layout, fragment).commit()
    }

}