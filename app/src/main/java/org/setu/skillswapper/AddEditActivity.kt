package org.setu.skillswapper

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddEditActivity : AppCompatActivity() {

    private lateinit var wantedInput: EditText
    private lateinit var offeredInput: EditText
    private lateinit var locationInput: EditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        wantedInput = findViewById(R.id.wantedInput)
        offeredInput = findViewById(R.id.offeredInput)
        locationInput = findViewById(R.id.locationInput)

        editingId = intent.getLongExtra("id", -1L).takeIf { it != -1L }

        if (editingId != null) {
            findViewById<TextView>(R.id.formTitle).text = "Edit Swap"
            loadExistingSwap(editingId!!)
        }

        findViewById<Button>(R.id.saveButton).setOnClickListener { saveSwap() }
        findViewById<Button>(R.id.cancelButton).setOnClickListener { finish() }
    }

    private fun loadExistingSwap(id: Long) {
        val swap = AppData.swaps.findOne(id)

        if (swap == null) {
            Toast.makeText(this, "Swap not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        wantedInput.setText(swap.skillWanted)
        offeredInput.setText(swap.skillOffered)
        locationInput.setText(swap.location)
    }

    private fun saveSwap() {
        val wanted = wantedInput.text.toString().trim()
        val offered = offeredInput.text.toString().trim()
        val location = locationInput.text.toString().trim()

        if (wanted.isEmpty()) {
            wantedInput.error = "Required"
            return
        }
        if (offered.isEmpty()) {
            offeredInput.error = "Required"
            return
        }
        if (location.isEmpty()) {
            locationInput.error = "Required"
            return
        }

        if (editingId == null) {
            AppData.swaps.create(
                SkillSwapModel(skillWanted = wanted, skillOffered = offered, location = location)
            )
            Toast.makeText(this, "Swap created", Toast.LENGTH_SHORT).show()
        } else {
            AppData.swaps.update(
                SkillSwapModel(id = editingId!!, skillWanted = wanted, skillOffered = offered, location = location)
            )
            Toast.makeText(this, "Swap updated", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}