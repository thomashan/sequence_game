package com.thomashan.game.sequence.events.game

import com.thomashan.game.sequence.domain.game.Game

record GameNotStarted(String reason) implements StartGameEvent {
    @Override
    Optional<Game> getGame() {
        return Optional.empty()
    }
}
