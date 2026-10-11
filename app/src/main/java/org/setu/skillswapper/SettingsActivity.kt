package org.setu.skillswapper

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE) //Opens a small file on phone for app settings
        val darkModeSwitch = findViewById<SwitchMaterial>(R.id.darkModeSwitch)

        // Show the saved choice - is dark mode on or off
        darkModeSwitch.isChecked = prefs.getBoolean("dark_mode", false)

        // When the switch is flipped: save the choice, then change the theme
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)  //Changes whole app between light and dark
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        setupBottomBar(this, R.id.nav_settings)
    }
}