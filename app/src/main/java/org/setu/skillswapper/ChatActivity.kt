package org.setu.skillswapper

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_soon)
        findViewById<TextView>(R.id.comingSoonText).text = "Chat\nComing soon"
        setupBottomBar(this, R.id.nav_chat)
    }
}