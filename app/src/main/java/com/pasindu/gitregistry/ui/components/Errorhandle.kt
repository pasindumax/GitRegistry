package com.pasindu.gitregistry.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pasindu.gitregistry.ui.theme.Primary
import retrofit2.HttpException
import java.io.IOException

@Composable
fun ErrorState(
    error: Throwable,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "!",
                style = MaterialTheme.typography.titleLarge.copy(

                    fontSize = MaterialTheme.typography.titleLarge.fontSize * 2
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = getErrorMessage(error),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = getErrorDescription(error),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(48.dp)
            ) {
                Text(
                    text = "Retry",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }
    }
}
fun getErrorMessage(error: Throwable): String {
    return when (error) {
        is IOException -> "No Internet Connection"
        is HttpException -> {
            if (error.code() == 403) {
                "API Rate Limit Exceeded"
            } else {
                "Something went wrong (${error.code()})"
            }
        }
        else -> "Something went wrong"
    }
}

private fun getErrorDescription(error: Throwable): String {
    return when (error) {
        is IOException -> "check your internet connection and try again."
        is HttpException -> {
            if (error.code() == 403) {
                "Please try again later. "
            } else {
                " Please try again later.  "
            }
        }
        else -> "Please try again."
    }
}