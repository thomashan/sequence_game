package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DealCardsEvent
import com.thomashan.game.sequence.events.card.DealtSetCards
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["cardDeck"])
record DealSetCards(CardDeck cardDeck, Map<Player, List<Card>> dealCards) implements DealCards {
    @Override
    DealCardsEvent execute() {
        Optional<DealCardsEvent> failureEvent = getFailureEvent()
        if (!failureEvent.empty) {
            return failureEvent.get()
        }
        return new DealtSetCards(cardDeck, dealCards)
    }

    @Override
    CardDeck getCardDeck() {
        return cardDeck
    }

    @Override
    List<Player> getPlayers() {
        return dealCards.entrySet().stream()
            .map({ it.getKey() })
            .toList()
    }
}
