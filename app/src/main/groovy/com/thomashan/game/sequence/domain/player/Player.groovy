package com.thomashan.game.sequence.domain.player

import com.thomashan.game.sequence.domain.card.Card

record Player(String name, Colour colour, int maxCards, boolean allowAnyDiscard, List<Card> cards) {
    Player plus(Card card) {
        return new Player(name, colour, maxCards, allowAnyDiscard, cards + card)
    }

    Player minus(Card card) {
        List<Card> newCards = new ArrayList<>(cards)
        newCards.removeElement(card)
        return new Player(name, colour, maxCards, allowAnyDiscard, newCards)
    }
}
