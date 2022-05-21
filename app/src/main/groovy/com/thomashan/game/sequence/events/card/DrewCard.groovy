package com.thomashan.game.sequence.events.card

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.player.Player
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["player", "card"])
record DrewCard(Player player, Card card) implements DrawCardEvent {
}
