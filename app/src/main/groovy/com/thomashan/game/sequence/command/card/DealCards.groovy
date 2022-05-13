package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.command.Command
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DealCardsEvent
import com.thomashan.game.sequence.events.card.DealCardsFailed

trait DealCards implements Command<DealCardsEvent> {
    final CardDeck cardDeck
    final List<Player> players

    Optional<DealCardsEvent> getFailureEvent() {
        List<Player> alreadyDealtPlayers = getPlayers().stream()
            .filter({ it.cards.size() > 0 })
            .toList()
        if (!alreadyDealtPlayers.isEmpty()) {
            return Optional.of(new DealCardsFailed("Players already dealt cards: ${alreadyDealtPlayers}"))
        }
        int cardsRequired = getPlayers().stream()
            .map({ it.maxCards })
            .mapToInt(Integer::valueOf)
            .sum()
        if (getCardDeck().cards.size() < cardsRequired) {
            return Optional.of(new DealCardsFailed("Required ${cardsRequired} cards but only had ${getCardDeck().cards.size()} in the deck"))
        }
        return Optional.empty()
    }
}
