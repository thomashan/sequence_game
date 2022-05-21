package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.command.Command
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DealCardsEvent
import com.thomashan.game.sequence.events.card.DealCardsFailed

import static com.thomashan.game.sequence.domain.game.GameState.WAITING_FOR_CARDS_TO_BE_DEALT

trait DealCards implements Command<DealCardsEvent> {
    final Game game

    Optional<DealCardsFailed> getFailureEvent() {
        if (getGame().gameState() != WAITING_FOR_CARDS_TO_BE_DEALT) {
            return Optional.of(new DealCardsFailed("Game must be in WAITING_FOR_CARDS_TO_BE_DEALT state"))
        }
        List<Player> alreadyDealtPlayers = getGame().players().value().stream()
            .filter({ it.cards.size() > 0 })
            .toList()
        if (!alreadyDealtPlayers.isEmpty()) {
            return Optional.of(new DealCardsFailed("Players already dealt cards: ${alreadyDealtPlayers}"))
        }
        int cardsRequired = getGame().players().value().stream()
            .map({ it.maxCards })
            .mapToInt(Integer::valueOf)
            .sum()
        if (getGame().cardDeck().cards.size() < cardsRequired) {
            return Optional.of(new DealCardsFailed("Required ${cardsRequired} cards but only had ${getCardDeck().cards.size()} in the deck"))
        }
        return Optional.empty()
    }
}
