package com.thomashan.game.sequence.domain.board

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.player.Colour
import com.thomashan.game.sequence.domain.player.Player
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["card", "chip"])
record BoardCell(Optional<Card> card, Optional<Colour> chip, boolean placed) {
    boolean wild() {
        return card.isEmpty()
    }

    BoardCell place(Player player) {
        return new BoardCell(card, Optional.of(player.colour()), true)
    }

    BoardCell remove() {
        if (placed) {
            return new BoardCell(card, Optional.empty(), false)
        }
        return this
    }

    static BoardCell of(Optional<Card> card) {
        boolean placed = card.empty ? true : false
        return new BoardCell(card, Optional.empty(), placed)
    }
}
