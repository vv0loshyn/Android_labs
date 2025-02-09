package com.example.lab_1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.DropdownMenuItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContent {
            PhoneSelectorApp()
        }
    }
}

@Composable
fun PhoneSelectorApp() {
    val context = LocalContext.current
    var selectedPhoneType by remember { mutableStateOf<String?>(null) }
    var selectedBrand by remember { mutableStateOf<String?>(null) }
    var resultText by remember { mutableStateOf("") }

    val phoneTypes = listOf("Смартфон", "Кнопковий", "Розкладний")
    val brands = listOf("Samsung", "Apple", "Xiaomi", "OnePlus")

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Оберіть тип телефону:")
        DropdownMenuPhoneTypes(phoneTypes) { selectedPhoneType = it }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Оберіть фірму:")
        brands.forEach { brand ->
            Row {
                RadioButton(
                    selected = selectedBrand == brand,
                    onClick = { selectedBrand = brand }
                )
                Text(text = brand, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (selectedPhoneType == null || selectedBrand == null) {
                Toast.makeText(context, "Будь ласка, оберіть всі параметри", Toast.LENGTH_SHORT).show()
            } else {
                resultText = "Ви обрали: $selectedPhoneType, $selectedBrand"
            }
        }) {
            Text(text = "OK")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = resultText)
    }
}

@Composable
fun DropdownMenuPhoneTypes(phoneTypes: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(phoneTypes.first()) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(text = selectedOption)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            phoneTypes.forEach { phone ->
                DropdownMenuItem(onClick = {
                    selectedOption = phone
                    onSelect(phone)
                    expanded = false
                }) {
                    Text(text = phone)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PhoneSelectorApp()
}
