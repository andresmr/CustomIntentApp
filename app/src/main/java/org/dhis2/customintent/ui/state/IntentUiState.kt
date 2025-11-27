package org.dhis2.customintent.ui.state

// UI state used for the Intent handling screen
sealed class IntentUiState {
    object Loading : IntentUiState()
    data class Success(
        val receivedText: String?,
        val extraReturnType: ExtraReturnType,
        val responseText: String = ""
    ) : IntentUiState()
    data class Error(val message: String) : IntentUiState()
}

// Class representing UI events for the Intent handling screen
sealed class IntentUiEvent {
    data class UpdateResponseText(val text: String) : IntentUiEvent()
    object SendResponse : IntentUiEvent()
}

// Enum representing the type of extra data to return
enum class ExtraReturnType {
    STRING,
    INTEGER,
    FLOAT,
    BOOLEAN,
    OBJECT,
    LIST_OF_OBJECTS,
}