package com.agroconnect.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agroconnect.data.AgroRepository
import com.agroconnect.data.SupabaseClient
import com.agroconnect.models.Mandi
import com.agroconnect.ui.theme.*
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch
import com.agroconnect.utils.LocationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandisScreen() {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var mandis by remember { mutableStateOf<List<Mandi>>(emptyList()) }
    var search by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            try { 
                // Try GPS first
                val loc = LocationHelper.getCurrentLocation(context)
                if (loc != null) {
                    mandis = AgroRepository.getMandis(loc.latitude, loc.longitude)
                } else {
                    // Fallback to profile
                    val user = com.agroconnect.data.SupabaseClient.client.auth.currentUserOrNull()
                    if (user != null) {
                        val profile = (AgroRepository.getFarmerProfile(user.id) as? com.agroconnect.models.LocationProfile)
                            ?: (AgroRepository.getBuyerProfile(user.id) as? com.agroconnect.models.LocationProfile)
                        mandis = AgroRepository.getMandis(profile?.lat, profile?.lon)
                    } else {
                        mandis = AgroRepository.getMandis()
                    }
                }
            } catch (e: Exception) { 
                Log.e("AgroConnect", "MandisScreen failed: ${e.message}", e) 
            }
            loading = false
        }
    }

    val filtered = mandis.filter {
        search.isBlank() || it.mandiName.contains(search, ignoreCase = true) ||
        (it.districtName?.contains(search, ignoreCase = true) == true)
    }

    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        // Search Bar
        item {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                placeholder = { Text("Search mandis…") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
            )
        }

        item {
            Text("📍 ${filtered.size} markets found", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        items(filtered) { mandi ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(mandi.mandiName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(
                                "${mandi.districtName ?: ""}, ${mandi.stateCode}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .height(24.dp)
                                .background(
                                    color = Green50,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(mandi.stateCode, style = MaterialTheme.typography.labelSmall, color = Green800)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "📍 ${mandi.latitude.toBigDecimal().setScale(4, java.math.RoundingMode.HALF_UP)}°N, ${mandi.longitude.toBigDecimal().setScale(4, java.math.RoundingMode.HALF_UP)}°E",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = {
                            val uri = Uri.parse("google.navigation:q=${mandi.latitude},${mandi.longitude}")
                            val intent = Intent(Intent.ACTION_VIEW, uri).apply { setPackage("com.google.android.apps.maps") }
                            try { context.startActivity(intent) } catch (_: Exception) {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${mandi.latitude},${mandi.longitude}")))
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Icon(Icons.Filled.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Directions")
                    }
                }
            }
        }
    }
}
