package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DealCardsEvent
import com.thomashan.game.sequence.events.card.DealCardsFailed
import com.thomashan.game.sequence.events.card.DealtCards
import groovy.transform.ImmutableOptions

import java.util.stream.Collectors

@ImmutableOptions(knownImmutables = ["game"])
record DealRandomCards(Game game) implements DealCards {
    @Override
    DealCardsEvent execute() {
        Optional<DealCardsFailed> failureEvent = failureEvent
        if (!failureEvent.empty) {
            return failureEvent.get()
        }
        if (!game.cardDeck().shuffled()) {
            return new DealCardsFailed("Please shuffle the deck")
        }
        int numberOfCards = game.players().value().stream()
            .map({ it.maxCards() - it.cards().size() })
            .mapToInt(Integer::valueOf)
            .sum()
        List<Card> cardsToDeal = game.cardDeck().take(numberOfCards)
        Map<Player, List<Card>> dealtCards = game().players().value().stream()
            .map({
                List<Card> dealtCards = []
                while (dealtCards.size() < it.maxCards()) {
                    dealtCards.add(cardsToDeal.pop())
                }
                return new AbstractMap.SimpleEntry(it, dealtCards)
            })
            .collect(Collectors.toMap(Map.Entry.&getKey, Map.Entry.&getValue))

        return new DealtCards(dealtCards)
    }

    @Override
    Game getGame() {
        return game
    }
}
