package com.educonnect.ui.auth

import android.content.Context
import android.util.Log
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun TabletLoginLayout(
    context: Context,
    onLoginSuccess: (String) -> Unit,
    authViewModel: AuthViewModel = Injection.provideAuthViewModel(context)
) {
    val email by authViewModel.email.collectAsState()
    val password by authViewModel.password.collectAsState()

    val loginStatus by authViewModel.loginStatus.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    label = "Email",
                    leadingIcon = Icons.Default.Email,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                UnderlinedTextField(
                    value = passwordInput,
                    onValueChange = { passwordInput = it },
                    label = "Mot de passe",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onVisibilityToggle = { passwordVisible = !passwordVisible },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        Log.d("LoginScreen", "Authentication started")
                        authViewModel.onEmailChange(emailInput)
                        authViewModel.onPasswordChange(passwordInput)
                        authViewModel.authenticate()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("CONNEXION", fontWeight = FontWeight.Bold, color = Secondary)
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(authViewModel.userRole) {
        authViewModel.userRole.collect { role ->
            Log.d("TabletLoginLayout", "User Role observed: $role")
            role?.let {
                Log.d("TabletLoginLayout", "Redirecting to: $it")
                onLoginSuccess(it)
            }
        }
    }

    loginStatus?.let { status ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = status,
                actionLabel = "Fermer",
                duration = SnackbarDuration.Short
            )
        }
    }

}
