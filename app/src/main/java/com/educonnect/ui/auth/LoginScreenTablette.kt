package com.educonnect.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel

import com.educonnect.R
import com.educonnect.di.Injection
import com.educonnect.ui.theme.OnPrimaryOpacity
import com.educonnect.ui.theme.Primary
import com.educonnect.ui.theme.Secondary

@Composable
fun TabletLoginLayout( authViewModel: AuthViewModel = Injection.provideAuthViewModel(),) {
    val email by authViewModel.email.collectAsState()
    val password by authViewModel.password.collectAsState()

    // Nouveau état local pour la visibilité du mot de passe
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(500.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(OnPrimaryOpacity.copy(alpha = 0.2F))
                .padding(start = 32.dp, end = 32.dp, top = 48.dp, bottom = 32.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logoapp),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = (-70).dp)
                    .zIndex(1f)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    leadingIcon = Icons.Default.Email
                )

                Spacer(modifier = Modifier.height(12.dp))

                UnderlinedTextField(
                    value = password,
                    onValueChange = { authViewModel.onPasswordChange(it) },
                    label = "Mot de passe",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onVisibilityToggle = { passwordVisible = !passwordVisible }
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
