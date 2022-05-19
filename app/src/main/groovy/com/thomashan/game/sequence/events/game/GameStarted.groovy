package com.thomashan.game.sequence.events.game

import com.thomashan.game.sequence.domain.game.Game
import groovy.transform.ImmutableOptions

@ImmutableOptions(knownImmutables = ["game"])
record GameStarted(Game game) implements StartGameEvent {
    @Override
    Optional<Game> getGame() {
        return Optional.of(game)
    }
}
