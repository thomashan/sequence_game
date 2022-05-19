package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.command.Command
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DrawCardEvent
import com.thomashan.game.sequence.events.card.DrawCardFailed
import com.thomashan.game.sequence.events.card.DrewCard
import groovy.transform.ImmutableOptions

import java.time.Clock
import java.time.Instant

@ImmutableOptions(knownImmutables = ["clock", "player", "cardDeck"])
record DrawCard(Instant timestamp, final Clock clock, Player player, CardDeck cardDeck) implements Command<DrawCardEvent> {
    Instant getTimestamp() {
        return timestamp
    }

    @Override
    DrawCardEvent execute() {
        if (player.cards().size() >= player.maxCards()) {
            return new DrawCardFailed(clock.instant(), "Player has too many cards")
        }
        if (cardDeck.cards().isEmpty()) {
            return new DrawCardFailed(clock.instant(), "No more cards left in deck")
        }
        return new DrewCard(clock().instant(), cardDeck)
    }
}
