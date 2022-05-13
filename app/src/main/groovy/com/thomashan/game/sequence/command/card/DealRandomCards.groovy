package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DealCardsEvent
import com.thomashan.game.sequence.events.card.DealCardsFailed
import com.thomashan.game.sequence.events.card.DealtRandomCards
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["cardDeck", "players"])
record DealRandomCards(CardDeck cardDeck, List<Player> players) implements DealCards {
    @Override
    DealCardsEvent execute() {
        Optional<DealCardsEvent> failureEvent = getFailureEvent()
        if (!failureEvent.empty) {
            return failureEvent.get()
        }
        if (!cardDeck.shuffled()) {
            return new DealCardsFailed("Please shuffle the deck")
        }
        return new DealtRandomCards()
    }

    @Override
    CardDeck getCardDeck() {
        return cardDeck
    }

    @Override
    List<Player> getPlayers() {
        return players
    }
}
