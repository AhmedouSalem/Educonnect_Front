package com.educonnect.ui.auth

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.educonnect.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educonnect.di.Injection
import com.educonnect.ui.theme.OnPrimaryOpacity
import com.educonnect.ui.theme.Primary
import com.educonnect.ui.theme.Secondary
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState

@Composable
fun LoginScreen(
    context: Context,
    authViewModel: AuthViewModel = Injection.provideAuthViewModel(context),
    onLoginSuccess: (String) -> Unit,
    modifier: Modifier = Modifier
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
        modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.app_background),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logoapp),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(OnPrimaryOpacity.copy(alpha = 0.2F))
                    .fillMaxWidth(0.9f)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LOGIN",
                    fontSize = 24.sp,
                    color = Secondary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                UnderlinedTextField(
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    label = "Email",
                    leadingIcon = Icons.Default.Email
                )

                Spacer(modifier = Modifier.height(12.dp))

                UnderlinedTextField(
                    value = passwordInput,
                    onValueChange = { passwordInput = it },
                    label = "Mot de passe",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onVisibilityToggle = { passwordVisible = !passwordVisible }
                )

                Spacer(modifier = Modifier.height(20.dp))

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

    // Observer `userRole` pour redirection
    LaunchedEffect(authViewModel.userRole) {
        Log.d("LoginScreen", "LaunchedEffect triggered")
        authViewModel.userRole.collect { role ->
            Log.d("LoginScreen", "User Role observed in collect: $role")
            role?.let {
                Log.d("LoginScreen", "Redirecting to: $it")
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
