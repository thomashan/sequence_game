package com.thomashan.game.sequence.domain.board

import com.thomashan.game.sequence.domain.card.Card
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["card"])
record BoardCell(int column, int row, Optional<Card> card) {
    boolean wild() {
        return card.isEmpty()
    }

    static BoardCell of(int column, int row, Optional<Card> card) {
        return new BoardCell(column, row, card)
    }
}
