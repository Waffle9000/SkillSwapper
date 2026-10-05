package org.setu.skillswapper

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddEditActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var xInput: EditText
    private lateinit var yInput: EditText

    private var editingId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createUserInterface()

        editingId = intent.getLongExtra("id", -1L)
        if (editingId != -1L) loadExistingSwap(editingId)
    }

    private fun createUserInterface() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 320, 32, 32)
        }

        titleInput = EditText(this).apply { hint = "Title" }
        descriptionInput = EditText(this).apply { hint = "Description" }

        xInput = EditText(this).apply {
            hint = "X coordinate"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        yInput = EditText(this).apply {
            hint = "Y coordinate"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        val saveButton = Button(this).apply {
            text = "Save"
            setOnClickListener { saveSwap() }
        }

        val cancelButton = Button(this).apply {
            text = "Cancel"
            setOnClickListener { finish() }
        }

        root.addView(titleInput)
        root.addView(descriptionInput)
        root.addView(xInput)
        root.addView(yInput)
        root.addView(saveButton)
        root.addView(cancelButton)

        setContentView(root)
    }

    private fun loadExistingSwap(id: Long) {
        val swap = AppData.swaps.findOne(id)

        if (swap == null) {
            Toast.makeText(this, "Swap not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        titleInput.setText(swap.title)
        descriptionInput.setText(swap.description)
        xInput.setText(swap.x.toString())
        yInput.setText(swap.y.toString())
    }

    private fun saveSwap() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Title is required"
            return
        }

        val x = xInput.text.toString().toDoubleOrNull()
        if (x == null) {
            xInput.error = "Enter a valid number"
            return
        }

        val y = yInput.text.toString().toDoubleOrNull()
        if (y == null) {
            yInput.error = "Enter a valid number"
            return
        }

        if (editingId == -1L) {
            AppData.swaps.create(
                SkillSwapModel(title = title, description = description, x = x, y = y)
            )
            Toast.makeText(this, "Swap created", Toast.LENGTH_SHORT).show()
        } else {
            AppData.swaps.update(
                SkillSwapModel(id = editingId, title = title, description = description, x = x, y = y)
            )
            Toast.makeText(this, "Swap updated", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}