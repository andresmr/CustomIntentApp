package org.dhis2.customintent.ui.state

sealed class IntentUiState {
    object Loading : IntentUiState()
    data class Success(
        val receivedText: String?,
        val responseText: String = ""
    ) : IntentUiState()
    data class Error(val message: String) : IntentUiState()
}

sealed class IntentUiEvent {
    data class UpdateResponseText(val text: String) : IntentUiEvent()
    object SendResponse : IntentUiEvent()
}
