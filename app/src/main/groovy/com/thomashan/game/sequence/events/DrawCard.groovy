package com.thomashan.game.sequence.events

import com.thomashan.game.sequence.card.CardDeck
import com.thomashan.game.sequence.player.Player
import groovy.transform.ImmutableOptions

import java.time.Clock
import java.time.Instant

@ImmutableOptions(knownImmutables = ["clock", "player", "cardDeck"])
record DrawCard(Instant timestamp, final Clock clock, Player player, CardDeck cardDeck) implements Command {
    Instant getTimestamp() {
        return timestamp
    }

    DrawCardEvent toEvent() {
        if (player.cards.size() >= player.maxCards) {
            return new DrawCardFailed(clock.instant(), "Player has too many cards")
        }
        if (cardDeck.cards.isEmpty()) {
            return new DrawCardFailed(clock.instant(), "No more cards left in deck")
        }
        return new DrewCard(clock().instant(), cardDeck)
    }
}
