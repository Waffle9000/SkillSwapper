package org.setu.skillswapper

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged  //runs code everytime the text in the search box changes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
            onEdit = { swap -> editSwap(swap) },
            onDelete = { swap -> deleteSwap(swap) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Re-filter the list every time the search text changes
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

    // Returns only the swaps that match the search text
    private fun searchSwaps(): List<SkillSwapModel> {
        val query = searchInput.text.toString().trim()

        return AppData.swaps.findAll().filter { swap ->
            swap.skillWanted.contains(query, ignoreCase = true) ||
                    swap.skillOffered.contains(query, ignoreCase = true) ||
                    swap.location.contains(query, ignoreCase = true)
        }
    }

    private fun editSwap(swap: SkillSwapModel) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("id", swap.id)
        startActivity(intent)
    }

    private fun deleteSwap(swap: SkillSwapModel) {
        AppData.swaps.delete(swap.id)
        adapter.updateSwaps(searchSwaps())
    }
}