package com.wara.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wara.ui.components.SmartTicker
import com.wara.ui.components.TransactionCard
import com.wara.ui.components.WaraPulse

// Data class placeholder for Firestore models
data class Transaction(val id: String, val itemName: String, val timestamp: String, val amount: String)

@Composable
fun HomeScreen() {
    // Placeholder for Firestore state
    val transactions = remember {
        mutableStateListOf(
            Transaction("1", "Minyak Goreng 1L", "10:30 WIB", "Rp 15.000"),
            Transaction("2", "Beras 5kg", "09:15 WIB", "Rp 65.000"),
            Transaction("3", "Telur 1kg", "08:00 WIB", "Rp 28.000")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Immersive Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Text(
                text = "Warung Bu Siti",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Selamat pagi! Semoga laris manis hari ini.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }

        // Smart Ticker
        SmartTicker(message = "Beras di area Anda mulai langka, pesan sekarang dari distributor!")

        // The "WARA Pulse"
        WaraPulse(
            onSpeechStart = {
                // Trigger Google Cloud Speech-to-Text here
            },
            onSpeechEnd = {
                // Process audio, simulate adding a transaction
            }
        )

        // Dashboard
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Penjualan Terakhir",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (transactions.isEmpty()) {
                ZeroStateScreen()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(
                        items = transactions,
                        key = { it.id }
                    ) { transaction ->
                        TransactionCard(
                            itemName = transaction.itemName,
                            timestamp = transaction.timestamp,
                            amount = transaction.amount,
                            onDelete = { transactions.remove(transaction) },
                            onSummary = { /* Navigate or show summary */ }
                        )
                    }
                }
            }
        }
    }
}
