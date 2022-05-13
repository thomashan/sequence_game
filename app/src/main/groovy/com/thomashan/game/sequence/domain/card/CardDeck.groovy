package com.thomashan.game.sequence.domain.card

import com.thomashan.game.sequence.domain.player.Player

import static Card.of

record CardDeck(List<Card> cards, boolean shuffled) {
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

    Tuple2<List<Player>, CardDeck> draw(List<Player> players, Map<Player, List<Card>> dealCards) {
        List<Card> cardsToDeal = dealCards.entrySet().stream()
            .flatMap({ it.value.stream() })
            .toList()
        List<Player> newPlayers = players.stream()
            .map({ player ->
                List<Card> dealtCards = dealCards.getOrDefault(player, [])
                return new Player(player.name(), player.maxCards(), player.cards() + dealtCards)
            })
            .toList()
        return new Tuple2(newPlayers, this - cardsToDeal)
    }

    Tuple2<List<Player>, CardDeck> draw(List<Player> players) {
        List<Card> newCards = cards
        List<Player> newPlayers = players.stream()
            .map({ Player player ->
                int numberOfCardsToDraw = player.maxCards() - player.cards().size()
                List<Card> playerNewCards = player.cards() + newCards.take(numberOfCardsToDraw)
                newCards = newCards.drop(numberOfCardsToDraw)
                return new Player(player.name(), player.maxCards(), playerNewCards)
            })
            .toList()
        return new Tuple2(newPlayers, new CardDeck(newCards, shuffled))
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
