package org.setu.skillswapper

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SwapListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SwapAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_list)

        recyclerView = findViewById(R.id.swapsRecyclerView)

        adapter = SwapAdapter(
            swaps = AppData.swaps.findAll(),
            onEdit = { swap -> editSwap(swap) },
            onDelete = { swap -> deleteSwap(swap) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        setupBottomBar(this, R.id.nav_lists)
    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            adapter.updateSwaps(AppData.swaps.findAll())
        }
    }

    private fun editSwap(swap: SkillSwapModel) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("id", swap.id)
        startActivity(intent)
    }

    private fun deleteSwap(swap: SkillSwapModel) {
        AppData.swaps.delete(swap.id)
        adapter.updateSwaps(AppData.swaps.findAll())
    }
}