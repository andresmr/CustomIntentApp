package org.dhis2.customintent.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dhis2.customintent.ui.state.IntentUiEvent
import org.dhis2.customintent.ui.state.IntentUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntentScreen(
    uiState: IntentUiState,
    onEvent: (IntentUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Custom Intent App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        when (uiState) {
            is IntentUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is IntentUiState.Error -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        text = uiState.message,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            is IntentUiState.Success -> {
                SuccessContent(
                    receivedText = uiState.receivedText,
                    responseText = uiState.responseText,
                    onResponseTextChange = { onEvent(IntentUiEvent.UpdateResponseText(it)) },
                    onSendResponse = { onEvent(IntentUiEvent.SendResponse) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessContent(
    receivedText: String?,
    responseText: String,
    onResponseTextChange: (String) -> Unit,
    onSendResponse: () -> Unit
) {
    // Display received text if available
    if (!receivedText.isNullOrBlank()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Received Text:",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                SelectionContainer {
                    Text(
                        text = receivedText,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    } else {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = "App opened directly (no text received)",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }

    // Response text input
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Response Text:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = responseText,
                onValueChange = onResponseTextChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter your response...") },
                minLines = 3,
                maxLines = 6
            )
        }
    }

    // Send response button
    Button(
        onClick = onSendResponse,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = responseText.isNotBlank()
    ) {
        Text(
            text = "Send Response",
            fontSize = 16.sp
        )
    }

    Spacer(modifier = Modifier.height(1.dp))

    // Info text
    Text(
        text = "This app can be opened by other apps to process text. " +
                "Enter your response and tap 'Send Response' to return data to the calling app.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}
