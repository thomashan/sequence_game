package com.thomashan.game.sequence.card


import java.util.stream.Collectors

import static com.thomashan.game.sequence.card.Card.of

record CardDeck(List<Card> cards) {
    CardDeck minus(final Card card) {
        new CardDeck(cards - card)
    }

    def draw() {
        return [cards.head(), new CardDeck(cards.tail())]
    }

    CardDeck shuffle() {
        List<Card> newCards = new ArrayList<>(cards)
        newCards.shuffle()
        return new CardDeck(newCards)
    }

    static CardDeck standardDeck() {
        final List<Card> cards = Arrays.stream(Suite.values())
            .flatMap { Suite suite ->
                Arrays.stream(CardNumber.values()).map { CardNumber number -> of(number, suite) }
            }
            .collect(Collectors.toList()) * 2
        return new CardDeck(cards).shuffle()
    }
}
