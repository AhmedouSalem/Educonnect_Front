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
fun CustomDropdown(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedItem.ifBlank { "" },
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
            items.distinct().forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.ifBlank { "[inconnu]" }) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
