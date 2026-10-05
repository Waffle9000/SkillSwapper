package org.setu.skillswapper

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createUserInterface()
    }

    override fun onResume() {
        super.onResume()
        if (::listLayout.isInitialized) displaySwaps()
    }

    private fun createUserInterface() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 320, 32, 32)
        }

        val title = TextView(this).apply {
            text = "Skill Swaps"
            textSize = 28f
            gravity = Gravity.CENTER
        }

        val addButton = Button(this).apply {
            text = "Add Swap"
            setOnClickListener {
                startActivity(Intent(this@MainActivity, AddEditActivity::class.java))
            }
        }

        listLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        root.addView(title)
        root.addView(addButton)
        root.addView(listLayout)

        setContentView(root)
        displaySwaps()
    }

    private fun displaySwaps() {
        listLayout.removeAllViews()

        val swaps = AppData.swaps.findAll()

        if (swaps.isEmpty()) {
            listLayout.addView(TextView(this).apply {
                text = "No swaps yet."
                textSize = 18f
                setPadding(0, 40, 0, 40)
            })
            return
        }

        for (swap in swaps) {
            val swapLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(0, 20, 0, 20)
            }

            val swapTitle = TextView(this).apply {
                text = "${swap.id}: ${swap.title}"
                textSize = 20f
            }

            val swapDescription = TextView(this).apply {
                text = swap.description
                textSize = 16f
            }

            val coordinates = TextView(this).apply {
                text = "X: ${swap.x}, Y: ${swap.y}"
                textSize = 14f
            }

            val editButton = Button(this).apply {
                text = "Edit"
                setOnClickListener {
                    val intent = Intent(this@MainActivity, AddEditActivity::class.java)
                    intent.putExtra("id", swap.id)
                    startActivity(intent)
                }
            }

            val deleteButton = Button(this).apply {
                text = "Delete"
                setOnClickListener {
                    AppData.swaps.delete(swap.id)
                    displaySwaps()
                }
            }

            swapLayout.addView(swapTitle)
            swapLayout.addView(swapDescription)
            swapLayout.addView(coordinates)
            swapLayout.addView(editButton)
            swapLayout.addView(deleteButton)

            listLayout.addView(swapLayout)
        }
    }
}