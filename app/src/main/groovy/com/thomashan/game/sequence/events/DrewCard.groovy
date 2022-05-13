package com.thomashan.game.sequence.events

import com.thomashan.game.sequence.card.CardDeck
import groovy.transform.ImmutableOptions

import java.time.Instant

@ImmutableOptions(knownImmutables = ["cardDeck"])
record DrewCard(Instant timestamp, CardDeck cardDeck) implements DrawCardEvent {
    Instant getTimestamp() {
        return timestamp
    }
}
