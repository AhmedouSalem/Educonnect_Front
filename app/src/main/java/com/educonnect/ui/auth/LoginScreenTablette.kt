package com.educonnect.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.educonnect.R
import com.educonnect.di.Injection
import com.educonnect.ui.theme.OnPrimaryOpacity
import com.educonnect.ui.theme.Primary
import com.educonnect.ui.theme.Secondary


@Composable
fun TabletLoginLayout(authViewModel: AuthViewModel = Injection.provideAuthViewModel(),) {
    val email by authViewModel.email.collectAsState()
    val password by authViewModel.password.collectAsState()


    // État local pour la gestion des saisies
    var emailInput by remember { mutableStateOf(email) }
    var passwordInput by remember { mutableStateOf(password) }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .width(600.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(OnPrimaryOpacity.copy(alpha = 0.2f))
                .padding(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image à gauche
            Image(
                painter = painterResource(R.drawable.logoapp),
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
                    .padding(end = 24.dp)
            )

            // Formulaire à droite
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LOGIN",
                    fontSize = 24.sp,
                    color = Secondary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))

                UnderlinedTextField(
                    value = email,
                    onValueChange = { authViewModel.onEmailChange(it) },
                    label = "Email",
                    leadingIcon = Icons.Default.Email,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                UnderlinedTextField(
                    value = password,
                    onValueChange = { authViewModel.onPasswordChange(it) },
                    label = "Mot de passe",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onVisibilityToggle = { passwordVisible = !passwordVisible },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { authViewModel.authenticate() },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("CONNEXION", fontWeight = FontWeight.Bold, color = Secondary)
                }
            }
        }
    }
}


