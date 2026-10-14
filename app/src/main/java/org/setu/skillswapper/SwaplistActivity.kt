package org.setu.skillswapper

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class SwapListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SwapAdapter
    private lateinit var searchInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_list)

        recyclerView = findViewById(R.id.swapsRecyclerView)
        searchInput = findViewById(R.id.searchInput)

        adapter = SwapAdapter(
            swaps = AppData.swaps.findAll(),
            listener = object : OnSwapListener {

                // Tap a card
                override fun onSwapClick(swap: SkillSwapModel) {
                    editSwap(swap)
                }

                // Long press a card
                override fun onSwapLongClick(swap: SkillSwapModel): Boolean {
                    confirmDelete(swap)
                    return true
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchInput.doAfterTextChanged {
            adapter.updateSwaps(searchSwaps())
        }

        setupBottomBar(this, R.id.nav_lists)
    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            adapter.updateSwaps(searchSwaps())
        }
    }

    private fun searchSwaps(): List<SkillSwapModel> {
        val query = searchInput.text.toString().trim()

        return AppData.swaps.findAll().filter { swap ->
            swap.skillWanted.contains(query, ignoreCase = true) ||
                    swap.skillOffered.contains(query, ignoreCase = true) ||
                    swap.location.contains(query, ignoreCase = true)
        }
    }

    private fun editSwap(swap: SkillSwapModel) {
        Timber.d("Editing swap id=${swap.id}")
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("id", swap.id)
        startActivity(intent)
    }

    // Ask before deleting
    private fun confirmDelete(swap: SkillSwapModel) {
        AlertDialog.Builder(this)
            .setTitle("Delete swap?")
            .setMessage("Delete \"${swap.skillWanted}\"? This can't be undone.")
            .setPositiveButton("Delete") { _, _ -> deleteSwap(swap) }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteSwap(swap: SkillSwapModel) {
        Timber.d("Deleting swap id=${swap.id}")
        AppData.swaps.delete(swap.id)
        adapter.updateSwaps(searchSwaps())
    }
}