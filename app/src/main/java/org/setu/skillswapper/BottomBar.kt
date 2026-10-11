package org.setu.skillswapper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// This file should be called from any screen that shows the bottom bar.

fun setupBottomBar(activity: AppCompatActivity, currentItemId: Int) {

    val bottomNav = activity.findViewById<BottomNavigationView>(R.id.bottomNav)
    val swapButton = activity.findViewById<FloatingActionButton>(R.id.swapButton)

    // Highlight the button for the screen we're on
    bottomNav.menu.findItem(currentItemId).isChecked = true

    // The four normal buttons
    bottomNav.setOnItemSelectedListener { item ->
        val screen = when (item.itemId) {
            R.id.nav_home -> MainActivity::class.java
            R.id.nav_lists -> SwapListActivity::class.java
            R.id.nav_chat -> ChatActivity::class.java
            R.id.nav_settings -> SettingsActivity::class.java
            else -> null
        }

        if (screen != null && item.itemId != currentItemId) {
            activity.startActivity(Intent(activity, screen))
            activity.finish()
        }
        false
    }

    // The round SkillSwap button
    swapButton.setOnClickListener {
        if (currentItemId != R.id.nav_swap) {
            activity.startActivity(Intent(activity, BrowseActivity::class.java))
            activity.finish()
        }
    }
}