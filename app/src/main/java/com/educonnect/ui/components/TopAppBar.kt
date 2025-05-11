import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educonnect.ui.theme.OnPrimaryOpacity
import com.educonnect.ui.theme.Primary
import com.educonnect.ui.theme.Secondary

@Composable
fun TopAppBar(
    onHomeClick: () -> Unit,
    onSearch: (String) -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .clip(RoundedCornerShape(16.dp))
            .background(OnPrimaryOpacity.copy(alpha = 0.2F))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Home Icon
        IconButton(onClick = onHomeClick) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                tint = Primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Search TextField
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Rechercher", color = Secondary) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )

            IconButton(
                onClick = { onSearch(searchQuery) }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Vertical Divider
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(24.dp)
                .background(Primary)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Profile Icon with Dropdown
        Box {
            IconButton(onClick = { expanded = true }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                DropdownMenuItem(
                    text = { Text("Profil") },
                    onClick = {
                        expanded = false
                        onProfileClick()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Calendrier") },
                    onClick = {
                        expanded = false
                        // TODO: Implement Calendar action
                    }
                )

                DropdownMenuItem(
                    text = { Text("Langues") },
                    onClick = {
                        expanded = false
                        // TODO: Implement Languages action
                    }
                )

                Divider(color = Color.LightGray)

                DropdownMenuItem(
                    text = { Text("Déconnexion", color = Color.Red) },
                    onClick = {
                        expanded = false
                        onLogoutClick()
                    }
                )
            }
        }
    }
}
