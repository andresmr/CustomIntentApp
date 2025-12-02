package org.dhis2.customintent.ui.state

// UI state used for the Intent handling screen
// * receivedValues: String? - Holds the values received from the calling application
// * responseText: String - Text to be sent back to the calling application
// TODO update or extend the state as needed
sealed class IntentUiState {
    object Loading : IntentUiState()
    data class Success(
        val receivedValues: String?,
        val responseText: String = ""
    ) : IntentUiState()
    data class Error(val message: String) : IntentUiState()
}

// Class representing UI events for the Intent handling screen
// TODO Modify or create new events as needed
sealed class IntentUiEvent {
    data class UpdateResponseText(val text: String) : IntentUiEvent()
    object SendResponse : IntentUiEvent()
}

enum class ExtraReturnType {
    STRING,
    INTEGER,
    FLOAT,
    BOOLEAN,
    OBJECT,
    LIST_OF_OBJECTS,
}