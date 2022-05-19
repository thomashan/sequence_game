package com.thomashan.game.sequence.events.card


import java.time.Instant

record DrawCardFailed(Instant timestamp, String reason) implements DrawCardEvent {
    Instant getTimestamp() {
        return timestamp
    }
}
