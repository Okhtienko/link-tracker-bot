package com.java.bot.state;

public enum MessageState {
    TRACK("URL added for tracking."),
    ERROR_UNTRACK("URL is not tracked."),
    UNTRACK("URL removed from tracking."),
    ERROR("Incorrect URL. Enter the correct URL, the bot supports github and stackoverflow URLs."),
    UNKNOWN("Unknown command. Type /help displays a command window.");

    private final String message;

    MessageState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
