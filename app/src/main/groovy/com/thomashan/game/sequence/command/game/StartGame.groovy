package com.thomashan.game.sequence.command.game

import com.thomashan.game.sequence.command.Command
import com.thomashan.game.sequence.domain.card.CardDeck
import com.thomashan.game.sequence.domain.game.Game
import com.thomashan.game.sequence.domain.game.GameType
import com.thomashan.game.sequence.domain.player.Player
import com.thomashan.game.sequence.events.game.GameNotStarted
import com.thomashan.game.sequence.events.game.GameStarted
import com.thomashan.game.sequence.events.game.StartGameEvent

import java.util.stream.IntStream

record StartGame(int numberOfPlayers, GameType gameType) implements Command<StartGameEvent> {
    @Override
    StartGameEvent execute() {
        return switch (numberOfPlayers) {
            case { it instanceof Integer && it < 2 } -> new GameNotStarted("Not enough players: ${numberOfPlayers}")
            case { it instanceof Integer && it > 3 } -> new GameNotStarted("Too many players: ${numberOfPlayers}")
            default -> {
                int maxCards = getMaxCards(numberOfPlayers)
                List<Player> players = IntStream.range(0, numberOfPlayers)
                    .mapToObj({ i -> new Player("Player ${i}", maxCards, List.of()) })
                    .toList()
                new GameStarted(new Game(gameType, players, CardDeck.standardDeck()))
            }
        }
    }

    private int getMaxCards(int numberOfPlayers) {
        return switch (numberOfPlayers) {
            case 2 -> 7
            case 3 -> 6
            default -> throw new RuntimeException("Only 2 or 3 players allowed")
        }
    }
}
