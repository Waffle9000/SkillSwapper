package org.setu.skillswapper

import org.setu.skillswapper.SkillSwapModel
import org.setu.skillswapper.SkillSwapMemStore

val store = SkillSwapMemStore()

fun main() {
    println("=== Swap Console App (Lab 1) ===")
    var input: Int
    do {
        input = menu()
        when (input) {
            1 -> addSwap()
            2 -> listSwaps()
            3 -> updateSwap()
            4 -> deleteSwap()
            5 -> searchSwap()
            0 -> println("\nExiting SkillSwap application. Goodbye!")
            else -> println("\nInvalid option. Please try again.")
        }
    } while (input != 0)
}

fun menu(): Int {
    println("\n----------------------------------")
    println(" MAIN MENU")
    println("----------------------------------")
    println(" 1. Add Swap")
    println(" 2. List All Swaps")
    println(" 3. Update a Swap")
    println(" 4. Delete a Swap")
    println(" 5. Search Swap by ID")
    println(" 0. Exit")
    print("\nEnter option: ")
    return readlnOrNull()?.toIntOrNull() ?: -1
}




fun addSwap() {
    println("\n--- Add Swap ---")
    print("Enter Title: ")
    val title = readlnOrNull()?.trim().orEmpty()
    print("Enter Description: ")
    val description = readlnOrNull()?.trim().orEmpty()

    if (title.isNotEmpty()) {
        val Swap = SkillSwapModel(title = title, description = description)
        store.create(Swap)
        println("Swap added successfully with ID: ${Swap.id}")
    } else {
        println("Title cannot be empty. Creation cancelled.")
    }
}

fun listSwaps() {
    println("\n--- All Swaps ---")
    val Swaps = store.findAll()
    if (Swaps.isEmpty()) {
        println("No Swaps stored yet.")
    } else {
        Swaps.forEach { println("ID: ${it.id} | Title: ${it.title} | Description: ${it.description}") }
    }
}

fun updateSwap() {
    println("\n--- Update Swap ---")
    listSwaps()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Swap to update: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null && store.findOne(id) != null) {
        print("Enter New Title: ")
        val title = readlnOrNull()?.trim().orEmpty()
        print("Enter New Description: ")
        val description = readlnOrNull()?.trim().orEmpty()

        if (title.isNotEmpty()) {
            val updated = store.update(SkillSwapModel(id = id, title = title, description = description))
            if (updated) println("Swap updated successfully.")
        } else {
            println("Title cannot be empty. Update cancelled.")
        }
    } else {
        println("Swap with ID $id not found.")
    }
}




fun deleteSwap() {
    println("\n--- Delete Swap ---")
    listSwaps()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Swap to delete: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val deleted = store.delete(id)
        if (deleted) {
            println("Swap with ID $id deleted successfully.")
        } else {
            println("Swap with ID $id not found.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun searchSwap() {
    println("\n--- Search Swap ---")
    print("Enter ID: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val Swap = store.findOne(id)
        if (Swap != null) {
            println("Found: ID: ${Swap.id} | Title: ${Swap.title} | Description: ${Swap.description}")
        } else {
            println("No Swap found with ID $id.")
        }
    } else {
        println("Invalid ID entered.")
    }
}
