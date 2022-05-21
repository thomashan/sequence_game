package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.command.Command
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DrawCardEvent
import com.thomashan.game.sequence.events.card.DrawCardFailed

import static com.thomashan.game.sequence.domain.game.GameState.WAITING_FOR_PLAYER_TO_DRAW_OR_NEXT_PLAYER_TO_DISCARD

trait DrawCard implements Command<DrawCardEvent> {
    final Game game
    final Player player

    Optional<DrawCardFailed> getFailureEvent() {
        if (getGame().gameState() != WAITING_FOR_PLAYER_TO_DRAW_OR_NEXT_PLAYER_TO_DISCARD) {
            return Optional.of(new DrawCardFailed("Game must be in WAITING_FOR_PLAYER_TO_DRAW state"))
        }
        if (!isPlayersTurn()) {
            return Optional.of(new DrawCardFailed("Not player[${getPlayer().name()}]'s turn"))
        }
        if (getGame().cardDeck().cards().isEmpty()) {
            return Optional.of(new DrawCardFailed("No more cards left in deck"))
        }
        if (getPlayer().cards().size() >= getPlayer().maxCards()) {
            return Optional.of(new DrawCardFailed("Player has too many cards"))
        }
        return Optional.empty()
    }

    private boolean isPlayersTurn() {
        return getGame().players()[(getGame().turnNumber() - 1) % getGame().players().size()] == getPlayer()
    }
}
