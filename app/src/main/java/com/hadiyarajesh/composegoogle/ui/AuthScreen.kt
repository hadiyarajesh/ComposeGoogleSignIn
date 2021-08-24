package com.hadiyarajesh.composegoogle.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.api.ApiException
import com.hadiyarajesh.composegoogle.viewmodel.AuthViewModel
import com.hadiyarajesh.composegoogle.R
import com.hadiyarajesh.composegoogle.utils.AuthResultContract
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    val user by remember(authViewModel) { authViewModel.user }.collectAsState()
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    text = "Google sign in failed"
                } else {
                    coroutineScope.launch {
                        authViewModel.signIn(
                            email = account.email,
                            displayName = account.displayName,
                        )
                    }
                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
            }
        }

    AuthView(
        errorText = text,
        onClick = {
            text = null
            authResultLauncher.launch(signInRequestCode)
        }
    )

    user?.let {
        HomeScreen(user = it)
    }
}

@ExperimentalMaterialApi
@Composable
fun AuthView(
    errorText: String?,
    onClick: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInButton(
                text = "Sign in with Google",
                loadingText = "Signing in...",
                isLoading = isLoading,
                icon = painterResource(id = R.drawable.ic_google_logo),
                onClick = {
                    isLoading = true
                    onClick()
                }
            )

            errorText?.let {
                isLoading = false
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = it)
            }
        }
    }
}

