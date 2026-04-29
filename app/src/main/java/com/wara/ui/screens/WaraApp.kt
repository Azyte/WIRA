package com.wara.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wara.ui.theme.SuccessGreen

@Composable
fun WaraApp() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Beranda", "Stok", "QRIS", "Laporan")

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            // Simple box placeholder for icons to avoid loading external vectors initially
                            Box(modifier = Modifier
                                .size(24.dp)
                                .background(
                                    if (selectedTab == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                    shape = androidx.compose.foundation.shape.CircleShape
                                )
                            )
                        },
                        label = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (selectedTab) {
                0 -> HomeScreen()
                1 -> PlaceholderScreen("Stok Barang", "Status Stok: 12 Aman, 3 Menipis")
                2 -> PlaceholderScreen("QRIS", "Tampilkan QR Code untuk Pembayaran")
                3 -> PlaceholderScreen("Laporan", "Kirim Ringkasan Harian ke WhatsApp")
            }
            
            // Custom QRIS Floating Button override if needed
            if (selectedTab == 2) {
                Button(
                    onClick = { /* Generate QRIS */ },
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                    modifier = Modifier.align(Alignment.Center).padding(top = 100.dp)
                ) {
                    Text("Buat QRIS Baru")
                }
            }
        }
    }
}

@Composable
fun PlaceholderScreen(title: String, subtitle: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(subtitle, style = MaterialTheme.typography.bodyLarge)
    }
}
