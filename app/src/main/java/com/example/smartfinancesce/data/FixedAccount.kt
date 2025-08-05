package com.example.smartfinancesce.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fixed_accounts")
data class FixedAccount(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val amount: Float,
    val paymentDate: String
)
