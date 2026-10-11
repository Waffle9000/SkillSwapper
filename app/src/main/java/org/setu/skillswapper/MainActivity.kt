package org.setu.skillswapper

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.addSwapButton).setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }

        setupBottomBar(this, R.id.nav_home)
    }
}