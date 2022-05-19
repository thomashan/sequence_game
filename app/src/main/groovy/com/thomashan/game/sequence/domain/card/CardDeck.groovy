package com.thomashan.game.sequence.domain.card


import com.thomashan.game.sequence.events.card.DealtCards
import com.thomashan.game.sequence.events.card.DrewCard

import static Card.of

record CardDeck(List<Card> cards, boolean shuffled) {
    CardDeck plus(DrewCard drewCard) {
        return this - drewCard.card()
    }

    CardDeck plus(DealtCards dealtCards) {
        return this - dealtCards.allCards
    }

    List<Card> take(int numberOfCards) {
        return cards.take(numberOfCards)
    }

    CardDeck minus(Card card) {
        List<Card> newCards = new ArrayList<>(cards)
        newCards.removeElement(card)
        new CardDeck(newCards, shuffled)
    }

    CardDeck minus(List<Card> cardsToRemove) {
        List<Card> newCards = new ArrayList<>(cards)
        cardsToRemove.forEach({ cardToRemove -> newCards.removeElement(cardToRemove) })
        new CardDeck(newCards, shuffled)
    }

    CardDeck shuffle() {
        List<Card> newCards = new ArrayList<>(cards)
        newCards.shuffle()
        return new CardDeck(newCards, true)
    }

    static CardDeck standardDeck() {
        final List<Card> cards = Arrays.stream(Suite.values())
            .flatMap { Suite suite ->
                Arrays.stream(CardNumber.values()).map { CardNumber number -> of(number, suite) }
            }
            .toList() * 2
        return new CardDeck(cards, false).shuffle()
    }
}
