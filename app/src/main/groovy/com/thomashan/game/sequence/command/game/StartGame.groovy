package com.thomashan.game.sequence.command.game

import com.thomashan.game.sequence.command.Command
import com.thomashan.game.sequence.domain.board.Board
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.game.GameType
import com.thomashan.game.sequence.domain.player.Players
import com.thomashan.game.sequence.events.game.GameNotStarted
import com.thomashan.game.sequence.events.game.GameStarted
import com.thomashan.game.sequence.events.game.StartGameEvent
import groovy.transform.ImmutableOptions

import static com.thomashan.game.sequence.domain.game.GameState.WAITING_FOR_CARDS_TO_BE_DEALT

@ImmutableOptions(knownImmutables = ["players"])
record StartGame(Players players, GameType gameType) implements Command<StartGameEvent> {
    @Override
    StartGameEvent execute() {
        return switch (players) {
            case { it instanceof List && it.size() < 2 } -> new GameNotStarted("Not enough players: ${players.size()}")
            case { it instanceof List && it.size() > 3 } -> new GameNotStarted("Too many players: ${players.size()}")
            case { it instanceof List && it.size() != players.uniqueColours.size() } ->
                new GameNotStarted("Each player must have a unique colour")
            default -> {
                Players newPlayers = players.initialise()
                Board board = switch (gameType) {
                    case GameType.STANDARD -> Board.standard()
                    default -> throw new RuntimeException("Only standard board is supported")
                }
                new GameStarted(new Game(gameType, WAITING_FOR_CARDS_TO_BE_DEALT, 0, newPlayers, CardDeck.standardDeck(), board))
            }
        }
    }
}
