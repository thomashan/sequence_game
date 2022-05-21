package com.thomashan.game.sequence.events.card

import com.thomashan.game.sequence.domain.board.Position
import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.player.Player
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["player", "card", "position"])
record DiscardedCard(Player player, Card card, Position position) implements DiscardCardEvent {
}
