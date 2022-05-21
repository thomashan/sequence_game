package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DealCardsEvent
import com.thomashan.game.sequence.events.card.DealCardsFailed
import com.thomashan.game.sequence.events.card.DealtCards
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["game"])
record DealSetCards(Game game, Map<Player, List<Card>> dealCards) implements DealCards {
    @Override
    DealCardsEvent execute() {
        Optional<DealCardsFailed> failureEvent = failureEvent
        if (!failureEvent.empty) {
            return failureEvent.get()
        }
        List<Card> cardsToDeal = dealCards.entrySet().stream()
            .map({ it.value })
            .flatMap({ it.stream() })
            .toList()
        if ((game.cardDeck() - cardsToDeal).cards().size() != game.cardDeck().cards().size() - cardsToDeal.size()) {
            return new DealCardsFailed("The card deck doesn't have all the cards")
        }
        return new DealtCards(dealCards)
    }

    @Override
    Game getGame() {
        return game
    }
}
