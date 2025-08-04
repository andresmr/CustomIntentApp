package org.dhis2.customintent.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.dhis2.customintent.ui.state.IntentUiEvent
import org.dhis2.customintent.ui.state.IntentUiState

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<IntentUiState>(IntentUiState.Loading)
    val uiState: StateFlow<IntentUiState> = _uiState.asStateFlow()

    private var resultCallback: ((String) -> Unit)? = null

    fun processIntent(intent: Intent?) {
        try {
            val receivedText = when (intent?.action) {
                Intent.ACTION_SEND -> {
                    if (intent.type == "text/plain") {
                        intent.getStringExtra(Intent.EXTRA_TEXT)
                    } else null
                }
                "org.dhis2.customintent.ACTION_PROCESS_TEXT" -> {
                    intent.getStringExtra(Intent.EXTRA_TEXT)
                        ?: intent.getStringExtra("text_data")
                }
                else -> null
            }

            _uiState.value = IntentUiState.Success(
                receivedText = receivedText,
                responseText = generateDefaultResponse(receivedText)
            )
        } catch (e: Exception) {
            _uiState.value = IntentUiState.Error("Error processing intent: ${e.message}")
        }
    }

    fun onEvent(event: IntentUiEvent) {
        when (event) {
            is IntentUiEvent.UpdateResponseText -> {
                val currentState = _uiState.value
                if (currentState is IntentUiState.Success) {
                    _uiState.value = currentState.copy(responseText = event.text)
                }
            }
            is IntentUiEvent.SendResponse -> {
                val currentState = _uiState.value
                if (currentState is IntentUiState.Success) {
                    resultCallback?.invoke(currentState.responseText)
                }
            }
        }
    }

    fun setResultCallback(callback: (String) -> Unit) {
        resultCallback = callback
    }

    private fun generateDefaultResponse(receivedText: String?): String {
        return if (receivedText.isNullOrBlank()) {
            "Hello from CustomIntent app!"
        } else {
            "Processed: $receivedText"
        }
    }
}
