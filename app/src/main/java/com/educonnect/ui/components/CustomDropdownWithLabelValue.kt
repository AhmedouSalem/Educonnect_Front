package com.educonnect.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.educonnect.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownWithLabelValue(
    label: String,
    items: List<Pair<String, String>>, // value to label
    selectedValue: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedLabel = items.find { it.first == selectedValue }?.second ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedLabel,
            onValueChange = {},
            readOnly = true,
            label = { Text("$label *", color = Secondary) },
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Secondary)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                cursorColor = Secondary,
                focusedIndicatorColor = Secondary,
                unfocusedIndicatorColor = Secondary,
                focusedTextColor = Secondary,
                unfocusedTextColor = Secondary,
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.distinctBy { it.first }.forEach { (value, labelDisplay) ->
                DropdownMenuItem(
                    text = { Text(labelDisplay.ifBlank { "[inconnu]" }) },
                    onClick = {
                        onItemSelected(value)
                        expanded = false
                    }
                )
            }
        }
    }
}
