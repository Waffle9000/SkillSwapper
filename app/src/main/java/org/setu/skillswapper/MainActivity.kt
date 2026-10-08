package org.setu.skillswapper

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewSwapsButton = findViewById<Button>(R.id.viewSwapsButton)
        val addSwapButton = findViewById<Button>(R.id.addSwapButton)

        viewSwapsButton.setOnClickListener {
            startActivity(Intent(this, SwapListActivity::class.java))
        }

        addSwapButton.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }
    }
}