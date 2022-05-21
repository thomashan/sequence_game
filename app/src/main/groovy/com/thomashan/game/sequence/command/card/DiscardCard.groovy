package com.thomashan.game.sequence.command.card

import com.thomashan.game.sequence.command.Command
import com.thomashan.game.sequence.domain.board.Position
import com.thomashan.game.sequence.domain.card.Card
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.card.DiscardCardEvent
import com.thomashan.game.sequence.events.card.DiscardCardFailed
import com.thomashan.game.sequence.events.card.DiscardedCard
import groovy.transform.ImmutableOptions

import static com.thomashan.game.sequence.domain.game.GameState.WAITING_FOR_PLAYER_TO_DISCARD
import static com.thomashan.game.sequence.domain.game.GameState.WAITING_FOR_PLAYER_TO_DRAW_OR_NEXT_PLAYER_TO_DISCARD

@ImmutableOptions(knownImmutables = ["game", "player", "card", "position"])
record DiscardCard(Game game, Player player, Card card, Position position) implements Command<DiscardCardEvent> {
    @Override
    DiscardCardEvent execute() {
        if (!validGameState()) {
            return new DiscardCardFailed("Game must be in WAITING_FOR_PLAYER_TO_DISCARD state")
        }
        if (!isPlayersTurn()) {
            return new DiscardCardFailed("Not player[${player}]'s turn")
        }
        if (!isChipFree()) {
            return new DiscardCardFailed("Chip is already placed at position[${position}]")
        }
        if (card().remove()) {
            if (!isRemovable()) {
                return new DiscardCardFailed("You can only remove if a chip is already placed at position[${position}]")
            }
            if (isOwnChip()) {
                return new DiscardCardFailed("You cannot remove your own chip")
            }
        }
        if (!isPositionInRange()) {
            return new DiscardCardFailed("Position must be 0-9: column[${position.column()}], row[${position.row()}]")
        }
        if (!card().remove() && !isRightPosition()) {
            return new DiscardCardFailed("You cannot place chip on column[${position.column()}] row[${position.row()}] boardCell[${game.board().boardCells[position].card()}]")
        }
        if (!player.cards().contains(card) && !player.allowAnyDiscard()) {
            return new DiscardCardFailed("The player does not hold ${card}")
        }
        return new DiscardedCard(player, card, position)
    }

    private boolean isOwnChip() {
        return game().board().boardCells[position].chip().map({ it == player.colour() }).orElse(false)
    }

    private boolean isRemovable() {
        return game.board().boardCells[position].placed()
    }

    private boolean isChipFree() {
        return !game.board().boardCells[position].placed() || card.remove()
    }

    private boolean validGameState() {
        return game.gameState() == WAITING_FOR_PLAYER_TO_DISCARD ||
            game.gameState() == WAITING_FOR_PLAYER_TO_DRAW_OR_NEXT_PLAYER_TO_DISCARD
    }

    private boolean isPlayersTurn() {
        return game.players()[game.turnNumber() % game.players().size()] == player
    }

    private boolean isRightPosition() {
        return game.board().boardCells[position].card()
            .map({ it == card || card.wild() })
            .orElse(false)
    }

    private boolean isPositionInRange() {
        return position.column() >= 0 && position.column() < 10 && position.row() >= 0 && position.row() < 10
    }
}
